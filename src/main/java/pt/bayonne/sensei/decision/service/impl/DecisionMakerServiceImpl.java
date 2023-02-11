package pt.bayonne.sensei.decision.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pt.bayonne.sensei.decision.domain.Decision;
import pt.bayonne.sensei.decision.domain.SSN;
import pt.bayonne.sensei.decision.repository.DecisionRepository;
import pt.bayonne.sensei.decision.service.DecisionMakerService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class DecisionMakerServiceImpl implements DecisionMakerService {

    private final DecisionRepository decisionRepository;
    @Override
    public void decide(Integer ssn, LocalDate birthDate) {
        Decision decision = Decision.decide(SSN.create(ssn), birthDate);
        Decision decisionCreated = decisionRepository.save(decision);
        log.info("the decision is: {}",decisionCreated);
    }
}
