package pt.bayonne.sensei.decision.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pt.bayonne.sensei.decision.domain.Decision;
import pt.bayonne.sensei.decision.messaging.event.CustomerDTO;
import pt.bayonne.sensei.decision.messaging.event.CustomerEvent;
import pt.bayonne.sensei.decision.proxy.CustomerProxy;
import pt.bayonne.sensei.decision.proxy.CustomerResponse;
import pt.bayonne.sensei.decision.service.DecisionMakerService;

import java.util.List;
import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerMessageHandler {

    private final CustomerProxy customerProxy;
    private final DecisionMakerService decisionMakerService;


    @Bean
    public Consumer<List<CustomerEvent.CustomerCreatedEvent>> processCustomerCreated() {
        return customersCreated -> customersCreated.forEach(this::handle);
    }

    private void handle(CustomerEvent.CustomerCreatedEvent customerCreated) {
        log.info("consuming the event notification: {}", customerCreated.eventType());
        CustomerResponse customerResponse = customerProxy.fetchCustomerById(customerCreated.customerId());
        Decision decision = decisionMakerService.decide(customerResponse.ssn(), customerResponse.birthDate());
        log.info("the decision result is: {}", decision);
    }

}
