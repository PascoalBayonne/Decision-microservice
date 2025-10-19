package pt.bayonne.sensei.decision.proxy;

import java.time.LocalDate;

public record CustomerResponse(String firstName,
                               String lastName,
                               LocalDate birthDate,
                               String emailAddress,
                               Integer ssn) {
}
