package pt.bayonne.sensei.decision.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerRestClient implements CustomerProxy {
    private final RestClient restClient = RestClient.create("http://localhost:8283/api/v1/customer");

    @Override
    public CustomerResponse fetchCustomerById(Long customerId) {
        log.info("Now invoking the Customer MS. Fetch customer with id: {}", customerId);
        return restClient.get()
                .uri("/{customerId}", customerId)
                .retrieve()
                .body(CustomerResponse.class);
    }
}
