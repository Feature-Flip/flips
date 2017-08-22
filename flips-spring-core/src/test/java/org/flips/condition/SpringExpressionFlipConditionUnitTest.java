package org.flips.condition;

import org.flips.exception.SpringExpressionEvaluationFailureException;
import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SpringExpressionFlipConditionUnitTest {

    @Test
    public void shouldReturnTrueOnEvaluatingSpringExpressionGivenValidSpringExpression(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String inputExpression            = "@bean.property.equals('test')";
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        StandardEvaluationContext context = mock(StandardEvaluationContext.class);
        Expression expression             = mock(Expression.class);

        when(flipAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);
        when(featureContext.getExpressionParser()).thenReturn(expressionParser);
        when(featureContext.getEvaluationContext()).thenReturn(context);
        when(expressionParser.parseExpression(inputExpression)).thenReturn(expression);
        when(expression.getValue(context)).thenReturn(true);

        SpringExpressionFlipCondition condition = new SpringExpressionFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(true, result);
        verify(flipAnnotationAttributes).getAttributeValue("expression", "");
        verify(featureContext).getExpressionParser();
        verify(featureContext).getEvaluationContext();
        verify(expressionParser).parseExpression(inputExpression);
        verify(expression).getValue(context);
    }

    @Test
    public void shouldReturnFalseOnEvaluatingSpringExpressionGivenValidSpringExpression(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String inputExpression            = "@bean.property.equals('test')";
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        StandardEvaluationContext context = mock(StandardEvaluationContext.class);
        Expression expression             = mock(Expression.class);

        when(flipAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);
        when(featureContext.getExpressionParser()).thenReturn(expressionParser);
        when(featureContext.getEvaluationContext()).thenReturn(context);
        when(expressionParser.parseExpression(inputExpression)).thenReturn(expression);
        when(expression.getValue(context)).thenReturn(false);

        SpringExpressionFlipCondition condition = new SpringExpressionFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("expression", "");
        verify(featureContext).getExpressionParser();
        verify(featureContext).getEvaluationContext();
        verify(expressionParser).parseExpression(inputExpression);
        verify(expression).getValue(context);
    }

    @Test
    public void shouldReturnFalseOnEvaluatingSpringExpressionGivenSpringExpressionEvaluatesToNull(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String inputExpression            = "@bean.property.equals('test')";
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        StandardEvaluationContext context = mock(StandardEvaluationContext.class);
        Expression expression             = mock(Expression.class);

        when(flipAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);
        when(featureContext.getExpressionParser()).thenReturn(expressionParser);
        when(featureContext.getEvaluationContext()).thenReturn(context);
        when(expressionParser.parseExpression(inputExpression)).thenReturn(expression);
        when(expression.getValue(context)).thenReturn(null);

        SpringExpressionFlipCondition condition = new SpringExpressionFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("expression", "");
        verify(featureContext).getExpressionParser();
        verify(featureContext).getEvaluationContext();
        verify(expressionParser).parseExpression(inputExpression);
        verify(expression).getValue(context);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenExpressionIsEmpty(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String inputExpression            = "";
        when(flipAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);

        SpringExpressionFlipCondition condition = new SpringExpressionFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }

    @Test(expected = SpringExpressionEvaluationFailureException.class)
    public void shouldThrowSpringExpressionEvaluationFailureExceptionGivenExpressionCannotBeParsed(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String inputExpression            = "@unknownbean.property.equals('test')";
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        StandardEvaluationContext context = mock(StandardEvaluationContext.class);
        Expression expression             = mock(Expression.class);

        when(flipAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);
        when(featureContext.getExpressionParser()).thenReturn(expressionParser);
        when(featureContext.getEvaluationContext()).thenReturn(context);
        when(expressionParser.parseExpression(inputExpression)).thenReturn(expression);
        doThrow(SpelEvaluationException.class).when(expression).getValue(context);

        SpringExpressionFlipCondition condition = new SpringExpressionFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("expression", "");
        verify(featureContext).getExpressionParser();
        verify(featureContext).getEvaluationContext();
        verify(expressionParser).parseExpression(inputExpression);
        verify(expression).getValue(context);
    }

    @Test(expected = SpringExpressionEvaluationFailureException.class)
    public void shouldThrowSpringExpressionEvaluationFailureExceptionGivenExpressionEvaluationIsNotBoolean(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String inputExpression            = "@unknownbean.property.equals('test')";
        ExpressionParser expressionParser = mock(ExpressionParser.class);
        StandardEvaluationContext context = mock(StandardEvaluationContext.class);
        Expression expression             = mock(Expression.class);

        when(flipAnnotationAttributes.getAttributeValue("expression", "")).thenReturn(inputExpression);
        when(featureContext.getExpressionParser()).thenReturn(expressionParser);
        when(featureContext.getEvaluationContext()).thenReturn(context);
        when(expressionParser.parseExpression(inputExpression)).thenReturn(expression);
        when(expression.getValue(context)).thenReturn("test_value");

        SpringExpressionFlipCondition condition = new SpringExpressionFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("expression", "");
        verify(featureContext).getExpressionParser();
        verify(featureContext).getEvaluationContext();
        verify(expressionParser).parseExpression(inputExpression);
        verify(expression).getValue(context);
    }
}