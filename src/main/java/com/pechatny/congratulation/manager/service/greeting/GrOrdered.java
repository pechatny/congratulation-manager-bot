package com.pechatny.congratulation.manager.service.greeting;

import java.util.List;

public class GrOrdered implements Greeting<String> {
    private final Greeting<List<String>> origin;

    public GrOrdered(Greeting<List<String>> origin) {
        this.origin = origin;
    }

    @Override
    public String assignedGreeting() {
        return new GrMerged(origin).assignedGreeting();
    }

    @Override
    public String participants() {
        return origin.participants().toString();
    }
}
