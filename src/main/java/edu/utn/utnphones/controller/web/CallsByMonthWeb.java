package edu.utn.utnphones.controller.web;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.projections.CallsByMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test/month")
public class CallsByMonthWeb {
    private CallController callController;

    @Autowired
    public CallsByMonthWeb(CallController callController) {
        this.callController = callController;
    }

    @GetMapping
    public ResponseEntity<List<CallsByMonth>> getCallsByMonth(@RequestParam(value = "month") int month){
        ResponseEntity<List<CallsByMonth>> responseEntity;
        List<CallsByMonth> callsByMonthList;
        if (month > 0 && month <13){
            callsByMonthList = callController.getCallsByMonth(month);
            if (!callsByMonthList.isEmpty()){
                responseEntity = ResponseEntity.ok().body(callsByMonthList);
            }else{
                responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }else{
            throw new IllegalArgumentException("Month no valid");
        }
        return responseEntity;
    }
}
