package org.flips.fixture;

import org.flips.annotation.FlipBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TestClientFlipBeanSpringComponentSource {

    @FlipBean(with = TestClientFlipBeanSpringComponentTarget.class)
    public String map(String str){
        return str + ":" + "SOURCE";
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

    @FlipBean(with = TestClientFlipBeanSpringComponentSource.class)
    public String noflip(String str){
        return str + ":" + "SOURCE";
    }
}