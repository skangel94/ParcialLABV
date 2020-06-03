package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Call;


import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.projections.CallsByMonth;
import edu.utn.utnphones.projections.PriceLastCall;
import edu.utn.utnphones.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.util.Date;
import java.util.List;

@Controller
public class CallController {
    private CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    public List<Call> getAllCalls(){
        return callService.getAll();
    }

    public void addCall(String lineFrom, String lineTo, int seg , Date dateTime){
        callService.add(lineFrom,lineTo,seg,dateTime);
    }

    public Call getById(int id) throws ResourcesNotExistException {
        return callService.getById(id);
    }

    public List<Call> getCallsByDate(Date dateFrom, Date dateTo, int userId){
        return callService.getCallsByDate(dateFrom,dateTo,userId);
    }

    public List<Call> getCallsByPrice(float price, int userId){
        return callService.getCallsByPrice(price, userId);
    }

    public PriceLastCall getPriceLastCall(int userId){
        return callService.getPriceLastCall(userId);
    }

    public List<CallsByMonth> getCallsByMonth(int month){
        return callService.getCallsByMonth(month);
    }
}
