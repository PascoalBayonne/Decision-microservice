package pt.bayonne.sensei.decision.service;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public interface DecisionMakerService {
    void decide(Integer ssn, LocalDate birthDate);
}
