package br.com.mrb.restwithspringbootandjava.controller;

import br.com.mrb.restwithspringbootandjava.model.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sum(
            @PathVariable(value = "numberOne")String numberOne
            ,@PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if(!isNumeric(numberOne) || !isNumeric(numberTwo)){
            throw  new Exception();
        }

        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    private Double convertToDouble(String strNumber) {
        if(strNumber==null)
            return 0D;
        var number = strNumber.replace(",",".");
        if(isNumeric(number))
            return Double.parseDouble(number);
        return 0D;
    }

    private boolean isNumeric(String strNumber) {
        if(strNumber==null)  return false;
        var number = strNumber.replace(",",".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
