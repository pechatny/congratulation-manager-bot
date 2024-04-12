package com.pechatny.congratulation.manager.service.greeting;

public class GrBase implements Greeting<String> {

    private final String participants;
    private final String text;

    public GrBase(String participants, String text) {
        this.participants = participants;
        this.text = text;
    }

    @Override
    public String assignedGreeting() {
        return text;
    }

    @Override
    public String participants() {
        return participants;
    }
}
