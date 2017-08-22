package org.flips.store;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.flips.annotation.Flips;
import org.flips.model.FlipConditionEvaluator;
import org.flips.model.FlipConditionEvaluatorFactory;
import org.flips.processor.FlipAnnotationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class FlipAnnotationsStore {

    private static final Logger logger = LoggerFactory.getLogger(FlipAnnotationsStore.class);

    private final Map<Method, FlipConditionEvaluator> store = new HashMap<>();

    private ApplicationContext                      applicationContext;
    private FlipAnnotationProcessor                 flipAnnotationProcessor;
    private FlipConditionEvaluatorFactory           flipConditionEvaluatorFactory;

    @Autowired
    public FlipAnnotationsStore(ApplicationContext                   applicationContext,
                                FlipAnnotationProcessor              flipAnnotationProcessor,
                                FlipConditionEvaluatorFactory        flipConditionEvaluatorFactory) {

        this.applicationContext               = applicationContext;
        this.flipAnnotationProcessor          = flipAnnotationProcessor;
        this.flipConditionEvaluatorFactory    = flipConditionEvaluatorFactory;
    }

    @PostConstruct
    protected void buildFlipAnnotationsStore(){
        Map<String, Object> flipComponents = getFlipComponents();
        if ( !flipComponents.isEmpty() ) {
            flipComponents.entrySet().stream().forEach(entry -> storeMethodWithFlipConditionEvaluator(AopProxyUtils.ultimateTargetClass(entry.getValue())));
        }
        logger.info("Completed building FlipAnnotationsStore {}", store);
    }

    public boolean isFeatureEnabled(Method method) {
        return store
                .getOrDefault(method, flipConditionEvaluatorFactory.getEmptyFlipConditionEvaluator())
                .evaluate();
    }

    public int         getTotalMethodsCached(){
        return store.size();
    }

    public Set<Method> allMethodsCached() {
        return new HashSet<>(store.keySet());
    }

    private void storeMethodWithFlipConditionEvaluator(Class<?> clazz){
        Method[] methods = clazz.getDeclaredMethods();

        for( Method method : methods ){
            Method accessibleMethod = MethodUtils.getAccessibleMethod(method);
            if ( accessibleMethod != null ){
                FlipConditionEvaluator flipConditionEvaluator = flipAnnotationProcessor.getFlipConditionEvaluator(accessibleMethod);
                if ( !flipConditionEvaluator.isEmpty() ) {
                    logger.debug("Evaluated method accessibility {} to put in FlipAnnotationsStore as {}", method.getName(), flipConditionEvaluator.toString());
                    store.put(method, flipConditionEvaluator);
                }
            }
        }
    }

    private Map<String, Object> getFlipComponents() {
        return applicationContext.getBeansWithAnnotation(Flips.class);
    }
}