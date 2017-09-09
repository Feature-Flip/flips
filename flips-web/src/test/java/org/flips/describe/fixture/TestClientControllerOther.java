package org.flips.describe.fixture;

import org.flips.annotation.FlipBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test-other")
public class TestClientControllerOther {

    @FlipBean(with = TestClientController.class)
    @RequestMapping(value = "/className", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String className() {
        return TestClientControllerOther.class.getName();
    }

    @FlipBean(with = TestClientController.class)
    @RequestMapping(value = "/methodName", method = RequestMethod.GET)
    public void methodName() {
    }
}