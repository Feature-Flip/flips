package org.flips.fixture;

import org.flips.annotation.FlipBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@FlipBean(with = TestClientFlipBeanSpringComponentTarget.class)
public class TestClientFlipBeanSpringComponentSource {

    public String map(String str){
        return str + ":" + "SOURCE";
    }

    public LocalDate currentDate(){
        return LocalDate.now();
    }

    public LocalDate nextDate(){
        return currentDate().plusDays(1);
    }

    public LocalDate previousDate(){
        return currentDate().minusDays(1);
    }
}