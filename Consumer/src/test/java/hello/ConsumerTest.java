package hello;



import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class ConsumerTest{
        @Rule
        public PactProviderRuleMk2 pactProviderRule = new PactProviderRuleMk2("value-provider","localhost",8080,this);



        @Pact(consumer = "value-consumer")
        public RequestResponsePact createFragmentPostRequestSquare(PactDslWithProvider builder) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            return builder
                    .given("1")
                    .uponReceiving("square a number")
                    .path("/square")
                    .query("input=5")
                    .method("POST")
                    .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body("{" +
                            "\"value\": 25.0" +
                            "}")
                    .toPact();
        }

        @Pact(consumer = "value-consumer")
        public RequestResponsePact createFragmentPostRequestAdd(PactDslWithProvider builder) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            return builder
                    .given("2")
                    .uponReceiving("add two numbers")
                    .path("/add")
                    .query("input=5&input2=10")
                    .method("POST")
                    .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body("{" +
                            "\"value\": 15.0" +
                            "}")
                    .toPact();
        }

            @Pact(consumer = "value-consumer")
            public RequestResponsePact createFragmentGetRequest(PactDslWithProvider builder) {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                return builder
                        .given("3")
                        .uponReceiving("get last result")
                        .path("/square")
                        .method("GET")
                        .willRespondWith()
                        .status(200)
                        .headers(headers)
                        .body("{\"value\": 25.0 "+"}")
                        .toPact();
            }

        @Test
        @PactVerification(fragment = "createFragmentPostRequestAdd")
        public void runTest1() {
        String url = pactProviderRule.getConfig().url();

        URI valueInfoUri = URI.create(String.format("%s/%s",url,"add?input=5&input2=10"));
        ValueRestFetcher valueRestFetcher = new ValueRestFetcher();
        Value value = valueRestFetcher.fetchValueInfoPost(valueInfoUri);
        assertEquals(15.0, value.getValue());
    }

        @Test
        @PactVerification(fragment = "createFragmentPostRequestSquare")
        public void runTest2() {
            String url = pactProviderRule.getConfig().url();

            URI valueInfoUri = URI.create(String.format("%s/%s",url,"square?input=5"));
            ValueRestFetcher valueRestFetcher = new ValueRestFetcher();
            Value value = valueRestFetcher.fetchValueInfoPost(valueInfoUri);
            assertEquals(25.0, value.getValue());
        }


        @Test
        @PactVerification(fragment = "createFragmentGetRequest")
        public void runTest3() {
            String url = pactProviderRule.getConfig().url();

            URI valueInfoUri = URI.create(String.format("%s/%s", url, "square"));
            ValueRestFetcher valueRestFetcher = new ValueRestFetcher();
            Value value = valueRestFetcher.fetchValueInfo(valueInfoUri);

            Map expectedResponse = new HashMap();
            expectedResponse.put("value", 25.0);
            Assert.assertEquals("{value="+value.getValue()+"}",expectedResponse.toString());
        }
}

