package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {
    private final double value;

    public Value(double value) {
        this.value = value;
    }

    public Value(){
        value= 0;
    }
    public double getValue() {
        return value;
    }

    public String toString(){
        return ""+this.value;
    }
}