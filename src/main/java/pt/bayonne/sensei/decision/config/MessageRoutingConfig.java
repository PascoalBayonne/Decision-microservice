package pt.bayonne.sensei.decision.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class MessageRoutingConfig {
    private static final String HEADER_EVENT_TYPE = "X-EVENT-TYPE";

    private final Map<String, String> routingFunctions =
            Map.of("CustomerCreated", "handleCustomerCreated",
                    "EmailChanged", "handleEmailChanged");


    @Bean
    public MessageRoutingCallback routingCallback() {
        return new MessageRoutingCallback() {
            @Override
            public String routingResult(Message<?> message) {
                String headerEventType = (String) message.getHeaders().get(HEADER_EVENT_TYPE);
                log.info("---------> the header type is: {}", headerEventType);

                String routeTo = routingFunctions.getOrDefault(headerEventType, "unknownEvent");
                log.info("---------> routing the event to: {}", routeTo);
                return routeTo;
            }
        };
    }

    @Bean
    public Consumer<Message<?>> unknownEvent() {
        return message -> log.warn("consuming unknown event, so the application will discard it: {}", message.getHeaders());
    }

}
