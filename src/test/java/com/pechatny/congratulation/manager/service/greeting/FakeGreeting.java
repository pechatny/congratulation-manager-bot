package com.pechatny.congratulation.manager.service.greeting;

public class FakeGreeting implements Greeting<String> {

    @Override
    public String assignedGreeting() {
        return """
                1. Participant
                Greeting line 1
                Greeting line 2
                """;
    }

    @Override
    public String participants() {
        return """
                Participant 1
                Participant 2
                Participant 3
                """;
    }
}
