package org.flips.describe.controller;

import org.flips.describe.model.FlipDescription;
import org.flips.store.FlipAnnotationsStore;
import org.flips.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/describe")
public class FlipDescriptionController {

    private FlipAnnotationsStore flipAnnotationsStore;

    @Autowired
    public FlipDescriptionController(@Lazy FlipAnnotationsStore flipAnnotationsStore) {
        this.flipAnnotationsStore = flipAnnotationsStore;
    }

    @RequestMapping(value = "/features", method = RequestMethod.GET)
    @ResponseBody
    public List<FlipDescription> describeFeatures(){
        return getAllFlipDescription(null);
    }

    @RequestMapping(value = "/features/{featureName}", method = RequestMethod.GET)
    @ResponseBody
    public List<FlipDescription> describeFeaturesWithFilter(@PathVariable("featureName") String featureName){
        return getAllFlipDescription(featureName);
    }

    private List<FlipDescription> getAllFlipDescription(String featureName) {
        return getAllMethodsCached()
                .stream()
                .filter (getFeatureFilter(featureName))
                .map    (this::buildFlipDescription)
                .sorted (Comparator.comparing(FlipDescription::getMethodName))
                .collect(toList());
    }

    private Set<Method> getAllMethodsCached() {
        return flipAnnotationsStore.allMethodsCached();
    }

    private Predicate<Method> getFeatureFilter(String featureName){
        if ( Utils.isEmpty(featureName) ) return (Method method) -> true;
        return                                   (Method method) -> method.getName().equals(featureName);
    }

    private FlipDescription buildFlipDescription(Method method) {
        return new FlipDescription(method.getName(),
                                          method.getDeclaringClass().getName(),
                                          flipAnnotationsStore.isFeatureEnabled(method)
                                          );
    }
}