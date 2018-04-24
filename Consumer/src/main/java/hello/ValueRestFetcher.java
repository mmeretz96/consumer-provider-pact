package hello;

import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class ValueRestFetcher {

    public Value fetchValueInfo(URI providerUri){
        RestTemplate restTemplate= new RestTemplate();
        return restTemplate.getForObject(providerUri,Value.class);
    }
    public Value fetchValueInfoPost(URI providerUri){
        RestTemplate restTemplate= new RestTemplate();
        return restTemplate.postForObject(providerUri,null,Value.class);
    }
}
