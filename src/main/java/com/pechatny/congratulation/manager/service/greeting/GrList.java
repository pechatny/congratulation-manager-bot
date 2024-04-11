package com.pechatny.congratulation.manager.service.greeting;

import java.util.List;

public class GrList implements Greeting<List<String>> {
    private final Greeting<String> origin;

    public GrList(Greeting<String> origin) {
        this.origin = origin;
    }

    @Override
    public List<String> assignedGreeting() {
        return origin.assignedGreeting().trim()
            .lines()
            .filter(line -> !line.isBlank())
            .map(String::trim)
            .toList();
    }

    @Override
    public List<String> participants() {
        return origin.participants()
            .trim()
            .lines()
            .filter(line -> !line.isBlank())
            .map(String::trim)
            .toList();
    }
}
