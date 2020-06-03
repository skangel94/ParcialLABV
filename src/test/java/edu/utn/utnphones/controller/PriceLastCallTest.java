package edu.utn.utnphones.controller;

import edu.utn.utnphones.controller.web.PriceLastCallWeb;
import edu.utn.utnphones.domain.Call;

import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.projections.PriceLastCall;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PriceLastCallTest {
    CallController callController;
    PriceLastCallWeb priceLastCallWeb;
    PriceLastCall priceLastCall;

    @Before
    public void setUp(){
        priceLastCall = mock(PriceLastCall.class);
        callController = mock(CallController.class);
        priceLastCallWeb = new PriceLastCallWeb(callController);
    }

    @Test
    public void getPriceLastCallOk() throws UserNotexistException {

        when(callController.getPriceLastCall(1)).thenReturn(priceLastCall);
        ResponseEntity<PriceLastCall> responseEntity = priceLastCallWeb.getPriceLastCall(1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(priceLastCall,responseEntity.getBody());
    }

    @Test
    public void getPriceLastCallNoContent() throws UserNotexistException {

        when(callController.getPriceLastCall(1)).thenReturn(null);
        ResponseEntity<PriceLastCall> responseEntity = priceLastCallWeb.getPriceLastCall(1);
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test(expected = UserNotexistException.class)
    public void getPriceLastCallException() throws UserNotexistException {
        ResponseEntity<PriceLastCall> responseEntity = priceLastCallWeb.getPriceLastCall(0);
        assertEquals(HttpStatus.BAD_GATEWAY,responseEntity.getStatusCode());
    }


}
