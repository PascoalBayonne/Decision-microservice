package pt.bayonne.sensei.decision.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pt.bayonne.sensei.decision.domain.Decision;
import pt.bayonne.sensei.decision.messaging.event.CustomerDTO;
import pt.bayonne.sensei.decision.messaging.event.CustomerEvent;
import pt.bayonne.sensei.decision.service.DecisionMakerService;

import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerMessageHandler {

    private final DecisionMakerService decisionMakerService;

    @Bean
    public Function<CustomerEvent.CustomerCreated, Decision> processCustomerCreated() {
        return customerCreated -> {
            log.info("processing (transforming) the customerCreated: {}", customerCreated);
            CustomerDTO customer = customerCreated.customer();
            //consuming an API which gives timeout
            if (customer.firstName().startsWith("N")) {
                throw new IllegalStateException("the customer is invalid");
            }
            Decision decision = decisionMakerService.decide(customer.ssn(), customer.birthDate());
            log.info("producing the decision: {}", decision);
            return decision;
        };
    }

}
