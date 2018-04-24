package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class SquareController {

    double inputStore=1;

    @RequestMapping(value="/square", method=POST)
    public Value square(@RequestParam(value="input", defaultValue="1") double input) {
        inputStore=Double.valueOf(input);
        return new Value(Math.pow(inputStore,2));
    }

    @RequestMapping(value="/square", method=GET)
    public Value squareStore() {
        return new Value(Math.pow(inputStore,2));
    }
}
