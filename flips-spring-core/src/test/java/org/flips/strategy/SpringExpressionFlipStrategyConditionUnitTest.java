package org.flips.strategy;

import org.flips.exception.SpringExpressionEvaluationFailureException;
import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SpringExpressionFlipStrategyConditionUnitTest {

    @Test
    public void shouldReturnTrueOnEvaluatingSpringExpressionGivenValidSpringExpression(){
        FeatureContext featureContext                                                   = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String inputExpression            = "@bean.property.equals('test')";
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        StandardEvaluationContext context = mock(StandardEvaluationContext.class);
        Expression expression             = mock(Expression.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);
        when(featureContext.getExpressionParser()).thenReturn(expressionParser);
        when(featureContext.getEvaluationContext()).thenReturn(context);
        when(expressionParser.parseExpression(inputExpression)).thenReturn(expression);
        when(expression.getValue(context)).thenReturn(true);

        SpringExpressionFlipStrategyCondition strategyCondition = new SpringExpressionFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(true, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("expression", "");
        verify(featureContext).getExpressionParser();
        verify(featureContext).getEvaluationContext();
        verify(expressionParser).parseExpression(inputExpression);
        verify(expression).getValue(context);
    }

    @Test
    public void shouldReturnFalseOnEvaluatingSpringExpressionGivenValidSpringExpression(){
        FeatureContext featureContext                                                   = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String inputExpression            = "@bean.property.equals('test')";
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        StandardEvaluationContext context = mock(StandardEvaluationContext.class);
        Expression expression             = mock(Expression.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);
        when(featureContext.getExpressionParser()).thenReturn(expressionParser);
        when(featureContext.getEvaluationContext()).thenReturn(context);
        when(expressionParser.parseExpression(inputExpression)).thenReturn(expression);
        when(expression.getValue(context)).thenReturn(false);

        SpringExpressionFlipStrategyCondition strategyCondition = new SpringExpressionFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("expression", "");
        verify(featureContext).getExpressionParser();
        verify(featureContext).getEvaluationContext();
        verify(expressionParser).parseExpression(inputExpression);
        verify(expression).getValue(context);
    }

    @Test
    public void shouldReturnFalseOnEvaluatingSpringExpressionGivenSpringExpressionEvaluatesToNull(){
        FeatureContext featureContext                                                   = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String inputExpression            = "@bean.property.equals('test')";
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        StandardEvaluationContext context = mock(StandardEvaluationContext.class);
        Expression expression             = mock(Expression.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);
        when(featureContext.getExpressionParser()).thenReturn(expressionParser);
        when(featureContext.getEvaluationContext()).thenReturn(context);
        when(expressionParser.parseExpression(inputExpression)).thenReturn(expression);
        when(expression.getValue(context)).thenReturn(null);

        SpringExpressionFlipStrategyCondition strategyCondition = new SpringExpressionFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("expression", "");
        verify(featureContext).getExpressionParser();
        verify(featureContext).getEvaluationContext();
        verify(expressionParser).parseExpression(inputExpression);
        verify(expression).getValue(context);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenExpressionIsEmpty(){
        FeatureContext featureContext                                                   = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String inputExpression            = "";
        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);

        SpringExpressionFlipStrategyCondition strategyCondition = new SpringExpressionFlipStrategyCondition();
        strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);
    }

    @Test(expected = SpringExpressionEvaluationFailureException.class)
    public void shouldThrowSpringExpressionEvaluationFailureExceptionGivenExpressionCannotBeParsed(){
        FeatureContext featureContext                                                   = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String inputExpression            = "@unknownbean.property.equals('test')";
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        StandardEvaluationContext context = mock(StandardEvaluationContext.class);
        Expression expression             = mock(Expression.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);
        when(featureContext.getExpressionParser()).thenReturn(expressionParser);
        when(featureContext.getEvaluationContext()).thenReturn(context);
        when(expressionParser.parseExpression(inputExpression)).thenReturn(expression);
        doThrow(SpelEvaluationException.class).when(expression).getValue(context);

        SpringExpressionFlipStrategyCondition strategyCondition = new SpringExpressionFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("expression", "");
        verify(featureContext).getExpressionParser();
        verify(featureContext).getEvaluationContext();
        verify(expressionParser).parseExpression(inputExpression);
        verify(expression).getValue(context);
    }

    @Test(expected = SpringExpressionEvaluationFailureException.class)
    public void shouldThrowSpringExpressionEvaluationFailureExceptionGivenExpressionEvaluationIsNotBoolean(){
        FeatureContext featureContext                                                   = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String inputExpression            = "@unknownbean.property.equals('test')";
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        StandardEvaluationContext context = mock(StandardEvaluationContext.class);
        Expression expression             = mock(Expression.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);
        when(featureContext.getExpressionParser()).thenReturn(expressionParser);
        when(featureContext.getEvaluationContext()).thenReturn(context);
        when(expressionParser.parseExpression(inputExpression)).thenReturn(expression);
        when(expression.getValue(context)).thenReturn("test_value");

        SpringExpressionFlipStrategyCondition strategyCondition = new SpringExpressionFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("expression", "");
        verify(featureContext).getExpressionParser();
        verify(featureContext).getEvaluationContext();
        verify(expressionParser).parseExpression(inputExpression);
        verify(expression).getValue(context);
    }
}