package org.flips.describe.fixture;


import org.flips.annotation.FlipOff;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestClientController {

    @FlipOff
    @RequestMapping(value = "/featureDisabled", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void featureDisabled() {
    }

    @RequestMapping(value = "/featureEnabled", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void featureEnabled() {
    }

    @RequestMapping(value = "/className", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String className() {
        return TestClientController.class.getName();
    }

    @RequestMapping(value = "/methodName", method = RequestMethod.GET)
    @FlipOff
    public void methodName() {
    }
}