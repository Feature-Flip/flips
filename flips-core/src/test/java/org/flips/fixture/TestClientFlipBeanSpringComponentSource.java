package org.flips.fixture;

import org.flips.annotation.FlipBean;
import org.flips.annotation.FlipOff;
import org.flips.annotation.FlipOnEnvironmentProperty;
import org.flips.annotation.FlipOnProfiles;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TestClientFlipBeanSpringComponentSource {

    @FlipBean(with = TestClientFlipBeanSpringComponentSource.class)
    public String noFlip(String str){
        return str + ":" + "SOURCE";
    }

    @FlipBean(with = TestClientFlipBeanSpringComponentTarget.class)
    public String map(String str){
        return str + ":" + "SOURCE";
    }

    @FlipBean(with = TestClientFlipBeanSpringComponentTarget.class)
    @FlipOnProfiles(activeProfiles = "dev")
    public String changeCase(String str){
        return str.toUpperCase();
    }

    @FlipBean(with = TestClientFlipBeanSpringComponentTarget.class)
    @FlipOff
    public String identity(String str){
        return str;
    }

    @FlipBean(with = TestClientFlipBeanSpringComponentTarget.class)
    @FlipOnProfiles(activeProfiles = "dev")
    @FlipOnEnvironmentProperty(property = "flip.bean", expectedValue = "1")
    public String html(){
        return "<html></html>";
    }

    @FlipBean(with = TestClientFlipBeanSpringComponentTarget.class)
    public LocalDate currentDate(){
        return LocalDate.now();
    }

    @FlipBean(with = TestClientFlipBeanSpringComponentTarget.class)
    public LocalDate nextDate(){
        return currentDate().plusDays(1);
    }

    @FlipBean(with = TestClientFlipBeanSpringComponentTarget.class)
    public LocalDate previousDate(){
        return currentDate().minusDays(1);
    }
}