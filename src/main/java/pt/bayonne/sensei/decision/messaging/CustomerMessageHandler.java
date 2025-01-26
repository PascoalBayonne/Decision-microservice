package pt.bayonne.sensei.decision.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import pt.bayonne.sensei.decision.domain.Decision;
import pt.bayonne.sensei.decision.exception.RetryableException;
import pt.bayonne.sensei.decision.messaging.event.CustomerDTO;
import pt.bayonne.sensei.decision.messaging.event.CustomerEvent;
import pt.bayonne.sensei.decision.service.DecisionMakerService;

import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerMessageHandler {

    private final DecisionMakerService decisionMakerService;


    @Bean
    public Consumer<Message<CustomerEvent.CustomerCreated>> processCustomerCreated() {
        return customersCreated -> {
            log.info("-----------> Consuming from Original Topic: {}", customersCreated);
            handle(customersCreated.getPayload());
        };
    }

    @Bean
    public Consumer<Message<CustomerEvent.CustomerCreated>> processCustomerCreatedFromRetryTopic() {
        return customerCreatedMessage -> {
            log.info("-----------> Retrying using retry topic: {}", customerCreatedMessage);
            handle(customerCreatedMessage.getPayload());
        };
    }

    private void handle(CustomerEvent.CustomerCreated customerCreated) {
        log.info("processing (transforming) the customerCreated: {}", customerCreated);
        CustomerDTO customer = customerCreated.customer();
        if (customer.ssn() % 2 == 0) {
            //DB connection refused or timeout
            //Invoking a third party API ---> responds with timeout or unavailable service
            throw new RetryableException("We want to retry this message");
        }
        Decision decision = decisionMakerService.decide(customer.ssn(), customer.birthDate());
        log.info("the decision result is: {}", decision);
    }

}
