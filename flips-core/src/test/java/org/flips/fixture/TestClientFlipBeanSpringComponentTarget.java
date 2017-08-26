package org.flips.fixture;

import org.flips.annotation.FlipOff;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TestClientFlipBeanSpringComponentTarget {

    public String map(String str){
        return str + ":" + "TARGET";
    }

    @FlipOff
    public LocalDate currentDate(){
        return LocalDate.now();
    }

    private LocalDate previousDate(){
        return currentDate().minusDays(1);
    }
}
