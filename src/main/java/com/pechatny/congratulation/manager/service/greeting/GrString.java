package com.pechatny.congratulation.manager.service.greeting;

public class GrString implements Greeting<String> {

    private final String participants;
    private final String text;

    public GrString(String participants, String text) {
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
