package org.flips.model;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeatureContextUnitTest {

    @InjectMocks
    private FeatureContext              featureContext;

    @Mock
    private Environment                 environment;

    @Mock
    private FeatureExpressionContext    featureExpressionContext;

    @Test
    public void shouldReturnNonEmptyPropertyValueGivenPropertyExistsInEnvironment(){
        String property                 = "feature.enabled";

        when(environment.getProperty(property, String.class, "")).thenReturn("true");
        String propertyValue = featureContext.getPropertyValueOrDefault(property, String.class, "");

        assertEquals("true", propertyValue);
        verify(environment).getProperty(property, String.class, "");
    }

    @Test
    public void shouldReturnEmptyPropertyValueGivenPropertyDoesNotExistInEnvironment(){
        String property                 = "feature.enabled";

        when(environment.getProperty(property, String.class, "")).thenReturn("");
        String propertyValue = featureContext.getPropertyValueOrDefault(property, String.class, "");

        assertEquals("", propertyValue);
        verify(environment).getProperty(property, String.class, "");
    }

    @Test
    public void shouldReturnEmptyProfilesGivenNoActiveProfilesIsSetInEnvironment(){
        when(environment.getActiveProfiles()).thenReturn(null);
        String[] activeProfiles = featureContext.getActiveProfilesOrEmpty();

        assertEquals(ArrayUtils.EMPTY_STRING_ARRAY, activeProfiles);
        verify(environment).getActiveProfiles();
    }

    @Test
    public void shouldReturnActiveProfilesGivenNonEmptyActiveProfilesIsSetInEnvironment(){
        when(environment.getActiveProfiles()).thenReturn(new String[]{"dev"});
        String[] activeProfiles = featureContext.getActiveProfilesOrEmpty();

        assertEquals(new String[]{"dev"}, activeProfiles);
        verify(environment).getActiveProfiles();
    }

    @Test
    public void shouldReturnExpressionParser(){
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        when(featureExpressionContext.getExpressionParser()).thenReturn(expressionParser);

        ExpressionParser parser = featureContext.getExpressionParser();

        assertEquals(expressionParser, parser);
        verify(featureExpressionContext).getExpressionParser();
    }

    @Test
    public void shouldReturnEvaluationContext(){
        EvaluationContext evaluationContext = mock(StandardEvaluationContext.class);
        when(featureExpressionContext.getEvaluationContext()).thenReturn(evaluationContext);

        EvaluationContext context = featureContext.getEvaluationContext();

        assertEquals(evaluationContext, context);
        verify(featureExpressionContext).getEvaluationContext();
    }
}