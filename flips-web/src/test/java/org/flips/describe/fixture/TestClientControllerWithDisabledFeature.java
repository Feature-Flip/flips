package org.flips.describe.fixture;


import org.flips.annotation.Flips;
import org.flips.annotation.condition.FlipOff;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Flips
@RequestMapping(value = "/test")
public class TestClientControllerWithDisabledFeature {

    @FlipOff
    @RequestMapping(value = "/featureDisabled", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void featureDisabled() {
    }

    @RequestMapping(value = "/featureEnabled", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void featureEnabled() {
    }
}