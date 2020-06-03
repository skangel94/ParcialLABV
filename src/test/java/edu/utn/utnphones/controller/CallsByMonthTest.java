package edu.utn.utnphones.controller;

import edu.utn.utnphones.controller.web.CallsByMonthWeb;

import edu.utn.utnphones.exception.IllegalPriceException;
import edu.utn.utnphones.projections.CallsByMonth;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CallsByMonthTest {
    CallController controller;
    CallsByMonthWeb controllerWeb;
    CallsByMonth callsByMonth;

    @Before
    public void setUp(){
        controller = mock(CallController.class);
        callsByMonth = mock(CallsByMonth.class);
        controllerWeb = new CallsByMonthWeb(controller);
    }

    @Test
    public void getCallsByMonthOk(){
        List<CallsByMonth> callList = new ArrayList<>();
        callList.add(callsByMonth);
        when(controller.getCallsByMonth(1)).thenReturn(callList);

        ResponseEntity<List<CallsByMonth>> responseEntity = controllerWeb.getCallsByMonth(1);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(callList,responseEntity.getBody());
    }

    @Test
    public void getCallsByMonthNoContent(){
        List<CallsByMonth> callList = Collections.emptyList();
        when(controller.getCallsByMonth(1)).thenReturn(callList);

        ResponseEntity<List<CallsByMonth>> responseEntity = controllerWeb.getCallsByMonth(1);
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCallsByMonthException(){
        when(controller.getCallsByMonth(13)).thenThrow(new IllegalArgumentException());
        ResponseEntity<List<CallsByMonth>> responseEntity = controllerWeb.getCallsByMonth(13);
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }
}
