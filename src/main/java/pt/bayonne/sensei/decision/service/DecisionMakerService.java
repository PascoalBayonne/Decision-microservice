package pt.bayonne.sensei.decision.service;

import org.springframework.cglib.core.Local;
import pt.bayonne.sensei.decision.domain.Decision;

import java.time.LocalDate;

public interface DecisionMakerService {
    Decision decide(Integer ssn, LocalDate birthDate);
}
