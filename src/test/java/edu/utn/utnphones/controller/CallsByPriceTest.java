package edu.utn.utnphones.controller;

import edu.utn.utnphones.controller.web.CallsByPrice;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.projections.PriceLastCall;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CallsByPriceTest {

    CallController callController;
    CallsByPrice callsByPrice;
    Call call;
    UserController userController;
    User user;
    @Before
    public void setUp(){
        callController = mock(CallController.class);
        userController = mock(UserController.class);
        user = mock(User.class);
        call = mock(Call.class);
        callsByPrice = new CallsByPrice(callController,userController);
    }

    @Test
    public void callByPriceOk() throws UserNotexistException {
        List<Call> callList = new ArrayList<>();
        callList.add(call);
        float price = 0.1f;
        when(userController.getUserById(1)).thenReturn(user);
        when(callController.getCallsByPrice(price,1)).thenReturn(callList);

        ResponseEntity<List<Call>> responseEntity = callsByPrice.getCallsByPrice(price,1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(callList,responseEntity.getBody());
    }

    @Test
    public void callByPriceNoContent() throws UserNotexistException {
        List<Call> callList = Collections.emptyList();
        float price = 0.1f;
        when(userController.getUserById(1)).thenReturn(user);
        when(callController.getCallsByPrice(price,1)).thenReturn(callList);
        ResponseEntity<List<Call>> responseEntity = callsByPrice.getCallsByPrice(price,1);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void callByPriceException() throws UserNotexistException {
        float price = -1f;
        when(userController.getUserById(1)).thenReturn(user);
        ResponseEntity<List<Call>> responseEntity = callsByPrice.getCallsByPrice(price,1);
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }

    @Test(expected = UserNotexistException.class)
    public void callByPriceUserException() throws UserNotexistException {
        float price = 1f;
        when(userController.getUserById(1)).thenThrow(new UserNotexistException());
        ResponseEntity<List<Call>> responseEntity = callsByPrice.getCallsByPrice(price,1);
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }
}
