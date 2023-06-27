package pt.bayonne.sensei.decision.integration;

import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import pt.bayonne.sensei.decision.domain.Decision;
import pt.bayonne.sensei.decision.enumerated.State;
import pt.bayonne.sensei.decision.repository.DecisionRepository;

import java.util.Optional;

@SpringBootTest
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "pt.bayonne.sensei:customer:+:stubs"
)
@Slf4j
public class HandleCustomerCreatedEventIT {

    @Autowired
    private StubFinder stubFinder;


    @Autowired
    private DecisionRepository decisionRepository;

    @Test
    void handleEvent() {
        this.stubFinder.trigger("shouldPublishCustomerCreated");

        Awaitility.await().untilAsserted(() -> {
            Optional<Decision> optionalDecision = this.decisionRepository.findAll().stream().findAny();
            Assertions.assertTrue(optionalDecision.isPresent());

            Decision decision = optionalDecision.get();

            Assertions.assertNotNull(decision.getId());
            Assertions.assertNotNull(decision.getState());
            Assertions.assertNotNull(decision.getSsn());

            Assertions.assertEquals(888888888, decision.getSsn().getSsn());
            Assertions.assertEquals(State.REJECTED, decision.getState());

        });

    }
}
