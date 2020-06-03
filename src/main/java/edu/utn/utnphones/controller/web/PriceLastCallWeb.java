package edu.utn.utnphones.controller.web;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.domain.Call;

import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.projections.PriceLastCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/price")
public class PriceLastCallWeb {

    private CallController callController;

    @Autowired
    public PriceLastCallWeb(CallController callController) {
        this.callController = callController;
    }

    @GetMapping
    public ResponseEntity<PriceLastCall> getPriceLastCall(@RequestParam(value = "userId") int userId) throws UserNotexistException {
        ResponseEntity<PriceLastCall> responseEntity;
        Call call;
        PriceLastCall priceLastCall;
        if (userId <= 0){
            throw new UserNotexistException();
        }else{
            priceLastCall = callController.getPriceLastCall(userId);

            if(priceLastCall != null){
                responseEntity = ResponseEntity.ok().body(priceLastCall);
            }else{
                responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return responseEntity;
    }
}
