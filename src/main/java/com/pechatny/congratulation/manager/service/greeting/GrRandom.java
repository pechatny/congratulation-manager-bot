package com.pechatny.congratulation.manager.service.greeting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrRandom implements Greeting<String> {
    private final Greeting<List<String>> origin;

    public GrRandom(Greeting<List<String>> origin) {
        this.origin = origin;
    }

    @Override
    public String assignedGreeting() {
        var participantList = new ArrayList<>(origin.participants());

        Collections.shuffle(participantList);

        return new GrMerged(
            participantList,
            origin.assignedGreeting()
        ).assignedGreeting();
    }

    @Override
    public String participants() {
        return origin.participants().toString();
    }
}
