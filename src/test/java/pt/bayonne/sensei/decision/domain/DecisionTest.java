package pt.bayonne.sensei.decision.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.bayonne.sensei.decision.enumerated.State;

import java.time.LocalDate;

class DecisionTest {

    @Test
    void shouldRejectIfTheCustomerIsOlderThanSeventyAfterTheCreditLimit(){
        //given
        var ssn = SSN.create(123456789);
        var birthDate = LocalDate.of(1990,12,25);
        //when
        Decision decide = Decision.decide(ssn, birthDate);
        //then
        Assertions.assertEquals(State.REJECTED,decide.getState());
    }

    @Test
    void shouldApprovedIfCustomerSSNIsEven(){
        //given
        var ssn = SSN.create(222222222);
        var birthDate = LocalDate.of(1995,12,25);
        //when
        Decision decide = Decision.decide(ssn, birthDate);
        //then
        Assertions.assertEquals(State.APPROVED,decide.getState());
    }
}