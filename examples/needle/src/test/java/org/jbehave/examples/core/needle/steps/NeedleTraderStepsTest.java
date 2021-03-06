package org.jbehave.examples.core.needle.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.jbehave.examples.core.service.TradingService;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

/**
 * Test Constructor injection for steps.
 * @author Jan Galinski, Holisticon AG
 */
public class NeedleTraderStepsTest {

    // PONR - Plain old needle rule
    @Rule
    public final NeedleRule needle = new NeedleRule();

    // should be created via constructor injection.
    @ObjectUnderTest
    private NeedleTraderSteps needleTraderSteps;

    @Inject
    private TradingService tradingServiceMock;

    @Test
    public void shouldCreateNewInstanceViaConstructorInjectionWithMockedService() {
        assertThat(needleTraderSteps, Matchers.is(notNullValue()));
        assertThat(needleTraderSteps.getService(), is(tradingServiceMock));
    }

}
