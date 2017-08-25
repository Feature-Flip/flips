package org.flips.store;

import org.flips.model.FlipConditionEvaluator;
import org.flips.model.FlipConditionEvaluatorFactory;
import org.flips.processor.FlipAnnotationProcessor;
import org.flips.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.*;

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
        String[] flipComponents = getFlipComponents();
        if ( flipComponents.length != 0 ) {
            Arrays.stream(flipComponents).forEach(beanDefinition -> storeMethodWithFlipConditionEvaluator(AopProxyUtils.ultimateTargetClass(applicationContext.getBean(beanDefinition))));
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
            Method accessibleMethod = Utils.getAccessibleMethod(method);
            if ( accessibleMethod != null ){
                FlipConditionEvaluator flipConditionEvaluator = flipAnnotationProcessor.getFlipConditionEvaluator(accessibleMethod);
                if ( !flipConditionEvaluator.isEmpty() ) {
                    logger.debug("Evaluated method accessibility {} to put in FlipAnnotationsStore as {}", method.getName(), flipConditionEvaluator.toString());
                    store.put(method, flipConditionEvaluator);
                }
            }
        }
    }

    private String[] getFlipComponents() {
        return applicationContext.getBeanDefinitionNames();
    }
}