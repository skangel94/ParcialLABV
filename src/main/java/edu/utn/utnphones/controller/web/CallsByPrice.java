package edu.utn.utnphones.controller.web;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.controller.UserController;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.exception.IllegalPriceException;
import edu.utn.utnphones.exception.UserNotexistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test/calls")
public class CallsByPrice{

    private CallController callController;
    private UserController userController;
    @Autowired
    public CallsByPrice(CallController callController, UserController userController) {
        this.callController = callController;
        this.userController = userController;
    }

    @GetMapping
    public ResponseEntity<List<Call>> getCallsByPrice(@RequestParam(value = "price") float price, @RequestParam(value = "userId") int userId) throws UserNotexistException {
        ResponseEntity<List<Call>> responseEntity;
        List<Call> callList;
        userController.getUserById(userId);
        if (price < 0) {
            throw new IllegalPriceException("the price must be equals to or greater than 0");
        } else {
            callList = callController.getCallsByPrice(price, userId);
            if (!callList.isEmpty()) {
                responseEntity = ResponseEntity.ok().body(callList);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return responseEntity;
    }
}
