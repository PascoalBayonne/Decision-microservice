package pt.bayonne.sensei.decision.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
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
    public Consumer<Message<CustomerEvent.CustomerCreated>> handleCustomerCreated() {
        return customerCreatedMessage -> {
            log.info("[customerCreated] message handler is handling of type: ----------> {}", customerCreatedMessage.getHeaders().
                    get("X-EVENT-TYPE"));
            log.info("the message is: {}", customerCreatedMessage.getPayload());
            CustomerEvent.CustomerCreated customerCreatedMessagePayload = customerCreatedMessage.getPayload();
            CustomerDTO customer = customerCreatedMessagePayload.customer();
            decisionMakerService.decide(customer.ssn(), customer.birthDate());
        };
    }

    @Bean
    public Consumer<Message<CustomerEvent.EmailChanged>> handleEmailChanged(){
        return emailChangedMessage -> {
            log.info("emailChanged] message handler is handling of type: ----------> {}", emailChangedMessage.getHeaders().
                    get("X-EVENT-TYPE"));
            log.info("the message is: {}", emailChangedMessage.getPayload());
        };
    }

}
