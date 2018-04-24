package hello;


import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import org.junit.Rule;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class ConsumerTest {
        @Rule
        public PactProviderRule pactProviderRule = new PactProviderRule("value-provider", this);



        @Pact(consumer = "value-consumer")
        public PactFragment createFragment(PactDslWithProvider pactDslWithProvider) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            return pactDslWithProvider
                    .given("test first state")
                    .uponReceiving("ConsumerTest interaction")
                    .path("/square")
                    .query("input=5")
                    .method("POST")
                    .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body("{" +
                            "\"value\": 25.0" +
                            "}")
                    .toFragment();
        }

        @Pact(consumer = "value-consumer")
        public PactFragment createFragment2(PactDslWithProvider pactDslWithProvider) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            return pactDslWithProvider
                    .given("test second state")
                    .uponReceiving("ConsumerTest interaction 2")
                    .path("/square")
                    .method("GET")
                    .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body("{" +
                            "\"value\": 25.0" +
                            "}")
                    .toFragment();
        }


        @Test
        @PactVerification(fragment = "createFragment")
        public void runTest() {
            String url = pactProviderRule.getConfig().url();

            URI valueInfoUri = URI.create(String.format("%s/%s", url, "square?input=5"));
            ValueRestFetcher valueRestFetcher = new ValueRestFetcher();
            Value value = valueRestFetcher.fetchValueInfoPost(valueInfoUri);

            assertEquals(25.0, value.getValue());
        }


        @Test
        @PactVerification(fragment = "createFragment2")
        public void runTest2() {
            String url = pactProviderRule.getConfig().url();

            URI valueInfoUri = URI.create(String.format("%s/%s", url, "square"));
            ValueRestFetcher valueRestFetcher = new ValueRestFetcher();
            Value value = valueRestFetcher.fetchValueInfo(valueInfoUri);

            assertEquals(25.0, value.getValue());
        }
}

