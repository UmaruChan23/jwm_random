package com.ctf.jwm_random_ctf.dto;

import com.ctf.jwm_random_ctf.dto.MyRandom;
import lombok.Data;

@Data
public class Step {
    private int currentStep = 0;
    private final MyRandom random;

    public Step(MyRandom random) {
        this.random = random;
    }

    public void addStep() {
        ++currentStep;
    }

}
