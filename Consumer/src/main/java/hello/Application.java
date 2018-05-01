package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            MultiValueMap<String, String> bodyMap;
            Scanner s = new Scanner(System.in);
            Value value;
            HttpEntity<MultiValueMap<String, String>> requestEntity;
            String input;
            String secondInput;
            boolean done=false;
            while (!done){
                printInstructions();
                input=s.next();
                switch(input){
                    case "1":
                        bodyMap = new LinkedMultiValueMap<>();
                        System.out.println("Welche Zahl möchten sie quadrieren?");
                        input=s.next();
                        bodyMap.add("input", input);
                        requestEntity = new HttpEntity<>(bodyMap,null);
                        value = restTemplate.postForObject("http://localhost:8080/square",requestEntity, Value.class);
                        System.out.println("Ergebnis: " + value.toString());
                        break;
                    case "2": value = restTemplate.getForObject("http://localhost:8080/square", Value.class);
                        System.out.println("Ergebnis: " + value.toString());
                        break;
                    case "3":
                        bodyMap = new LinkedMultiValueMap<>();
                        System.out.println("Welche Zahlen möchten sie Addieren?");
                        System.out.println("1. ");
                        input=s.next();
                        System.out.println("2. ");
                        secondInput=s.next();
                        bodyMap.add("input", input);
                        bodyMap.add("input2", secondInput);
                        requestEntity = new HttpEntity<>(bodyMap,null);
                        value = restTemplate.postForObject("http://localhost:8080/add",requestEntity, Value.class);
                        System.out.println("Ergebnis: " + value.toString());
                        break;
                    case "4": done=true;
                        break;
                    default: System.out.println("Keine gültige Eingabe.");
                }


            }


        };
    }


    private void printInstructions(){
        System.out.println("Was möchten sie tun?");
        System.out.println("[1] Zahl Quadrieren");
        System.out.println("[2] Letztes Rechenergebnis ausgeben");
        System.out.println("[3] Zahlen Addieren");
        System.out.println("[4] Beenden");
    }
}