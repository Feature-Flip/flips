package org.flips.condition;

import org.flips.exception.SpringExpressionEvaluationFailureException;
import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.flips.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.stereotype.Component;

@Component
public class SpringExpressionFlipStrategyCondition implements FeatureFlipStrategyCondition {

    private static final Logger logger = LoggerFactory.getLogger(SpringExpressionFlipStrategyCondition.class);

    @Override
    public boolean evaluateCondition(FeatureContext featureContext,
                                     FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes) {

        String expression = featureFlipStrategyAnnotationAttributes.getAttributeValue("expression", "");
        ValidationUtils.requireNonEmpty(expression, "expression element can not be NULL or EMPTY when using @SpringExpressionFlipStrategy");

        return evaluateExpression(featureContext, expression);
    }

    private boolean evaluateExpression(FeatureContext featureContext, String expression){
        ExpressionParser parser           = featureContext.getExpressionParser();
        EvaluationContext context         = featureContext.getEvaluationContext();
        Expression parsedExpression       = parser.parseExpression(expression);
        Object value                      = null;
        try{
            value                    = parsedExpression.getValue(context);
            logger.info("SpringExpressionFlipStrategyCondition: Evaluated expression {} to {}", expression, value);
        }catch (SpelEvaluationException e){
            throw new SpringExpressionEvaluationFailureException("Evaluation failed for " + expression + ", returned value " + value, e);
        }
        if ( value != null && !Boolean.class.isInstance(value) )
            throw new SpringExpressionEvaluationFailureException("Can not convert " + value + " to boolean from expression " + expression);

        return value == null ? false : (boolean)value;
    }
}