package com.pechatny.congratulation.manager.service.greeting;

public class GrWithStatus<T> implements Greeting<T>, Status {

    private final Greeting<T> origin;
    private final Status status;

    public GrWithStatus(Greeting<T> greeting, Status status) {
        this.origin = greeting;
        this.status = status;
    }

    public GrWithStatus(Greeting<T> greeting) {
        this(greeting, Status.WAIT_FOR_PARTICIPANTS);
    }

    @Override
    public T assignedGreeting() {
        return origin.assignedGreeting();
    }

    @Override
    public T participants() {
        return origin.participants();
    }

    @Override
    public Status status() {
        return status;
    }

    enum Status {
        WAIT_FOR_PARTICIPANTS,
        WAIT_FOR_POETRY
    }
}
