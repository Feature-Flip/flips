package org.flips.store;

import org.flips.model.FlipConditionEvaluator;
import org.flips.model.FlipConditionEvaluatorFactory;
import org.flips.processor.FlipAnnotationProcessor;
import org.flips.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Component
public class FlipAnnotationsStore {

    private static final Logger logger = LoggerFactory.getLogger(FlipAnnotationsStore.class);

    private final Map<Method, FlipConditionEvaluator> store = new HashMap<>();
    private final List<String> excludedPackages;


    private ApplicationContext                      applicationContext;
    private FlipAnnotationProcessor                 flipAnnotationProcessor;
    private FlipConditionEvaluatorFactory           flipConditionEvaluatorFactory;

    @Autowired
    public FlipAnnotationsStore(ApplicationContext                 applicationContext,
                                FlipAnnotationProcessor            flipAnnotationProcessor,
                                FlipConditionEvaluatorFactory      flipConditionEvaluatorFactory,
                                @Value("${exclude.package.scan}")  String excludePackagesToScan) {

        this.applicationContext               = applicationContext;
        this.flipAnnotationProcessor          = flipAnnotationProcessor;
        this.flipConditionEvaluatorFactory    = flipConditionEvaluatorFactory;
        this.excludedPackages                 = Arrays.asList(excludePackagesToScan.split(","));
    }

    @PostConstruct
    protected void buildFlipAnnotationsStore(){
        String[] flipComponents = getFlipComponents();
        if ( flipComponents.length != 0 ) {
            Map<Method, FlipConditionEvaluator> flipConditionEvaluatorMap =
                                    Arrays.stream(flipComponents)
                                          .flatMap(beanDefinition -> getAllMethodsWithConditionEvaluator(AopProxyUtils.ultimateTargetClass(applicationContext.getBean(beanDefinition))).stream())
                                          .collect(toMap(MethodConditionEvaluator::getMethod, MethodConditionEvaluator::getFlipConditionEvaluator));

            store.putAll(flipConditionEvaluatorMap);
        }

        logger.debug("Completed building FlipAnnotationsStore {}", store);
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

    private List<MethodConditionEvaluator> getAllMethodsWithConditionEvaluator(Class<?> clazz){
        if ( !isPackageExcludedFromScan(clazz.getPackage().getName()) ) {
            return Arrays.stream (clazz.getDeclaredMethods())
                  .map           (Utils::getAccessibleMethod)
                  .filter        (accessibleMethod         -> accessibleMethod != null)
                  .map           (accessibleMethod         -> new MethodConditionEvaluator(accessibleMethod, flipAnnotationProcessor.getFlipConditionEvaluator(accessibleMethod)))
                  .filter        (methodConditionEvaluator -> !methodConditionEvaluator.getFlipConditionEvaluator().isEmpty())
                  .collect       (toList());
        }
        return Collections.emptyList();
    }

    private boolean isPackageExcludedFromScan(String packageName){
        return excludedPackages
                .stream()
                .filter(excludedPackage -> packageName.startsWith(excludedPackage))
                .map   (pkg             -> true)
                .findFirst()
                .orElse(false);
    }

    private String[] getFlipComponents() {
        return applicationContext.getBeanDefinitionNames();
    }

    static class MethodConditionEvaluator {
        private Method method;
        private FlipConditionEvaluator flipConditionEvaluator;

        public MethodConditionEvaluator(Method method, FlipConditionEvaluator flipConditionEvaluator) {
            this.method = method;
            this.flipConditionEvaluator = flipConditionEvaluator;
        }

        public Method getMethod() {
            return method;
        }

        public FlipConditionEvaluator getFlipConditionEvaluator() {
            return flipConditionEvaluator;
        }
    }
}