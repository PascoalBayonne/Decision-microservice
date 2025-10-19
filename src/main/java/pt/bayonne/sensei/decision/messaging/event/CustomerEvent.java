package pt.bayonne.sensei.decision.messaging.event;


import java.io.Serializable;
import java.time.Instant;

public sealed interface CustomerEvent extends Serializable {

    /**
     * Each event is a fact, it describes a state change that occurred to the entity (past tense!)
     *
     * @param customerId the customer id used in order to provide the delivery order semantic
     * @param createdAt  describes when this event occurred
     * @param customer   the customer current state change
     */
    record CustomerCreated(Long customerId, Instant createdAt, CustomerDTO customer) implements CustomerEvent {

    }

    /**
     * DTO to be used on Event-Driven Notification Event.
     * @param customerId
     * @param createdAt
     * @param eventType
     * @param source
     */
    record CustomerCreatedEvent(Long customerId, Instant createdAt, String eventType, String source) implements CustomerEvent {

    }

}
