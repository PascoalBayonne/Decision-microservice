package pt.bayonne.sensei.decision.domain.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.bayonne.sensei.decision.domain.Decision;
import pt.bayonne.sensei.decision.domain.DecisionApplicationTestConfig;
import pt.bayonne.sensei.decision.messaging.CustomerMessageHandler;
import pt.bayonne.sensei.decision.messaging.event.CustomerEvent;

import java.io.IOException;
import java.util.function.Function;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "pt.bayonne.sensei:customer:+:stubs"
)
@Import(DecisionApplicationTestConfig.class)
class DecisionIntegrationTest {

    @Autowired
    private StubFinder stubFinder;

    @Autowired
    private CustomerMessageHandler messageHandler;

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testConsumer() throws IOException {
        boolean trigger = this.stubFinder.trigger("shouldPublishCustomerCreated_label");
        Assertions.assertTrue(trigger);

        Message<byte[]> receive = outputDestination.receive(2000, "decision-topic");
        Assertions.assertNotNull(receive);
        Assertions.assertNotNull(receive.getHeaders());
        Assertions.assertNotNull(receive.getPayload());

        Decision decision = objectMapper.readValue(receive.getPayload(), Decision.class);
        Assertions.assertNotNull(decision);
        Assertions.assertEquals(Integer.valueOf(888888888), decision.getSsn().getSsn());
    }
}
