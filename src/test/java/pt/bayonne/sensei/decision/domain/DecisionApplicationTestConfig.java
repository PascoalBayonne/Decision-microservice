package pt.bayonne.sensei.decision.domain;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(TestChannelBinderConfiguration.class)
public class DecisionApplicationTestConfig {
}
