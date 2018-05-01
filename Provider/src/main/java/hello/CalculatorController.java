package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class CalculatorController {

    double inputStore=1;

    @RequestMapping(value="/square", method=POST)
    public Value square(@RequestParam(value="input", defaultValue="1") double input) {
        inputStore=Math.pow(Double.valueOf(input),2);
        return new Value(inputStore);
    }

    @RequestMapping(value={"/square","/add"}, method=GET)
    public Value squareStore() {
        return new Value(inputStore);
    }


    @RequestMapping(value="/add", method=POST)
    public Value add(@RequestParam(value="input", defaultValue="1") double input, double input2) {
        inputStore=input+input2;
        return new Value(inputStore);
    }


}