package com.pechatny.congratulation.manager.service.greeting;

public interface Greeting<T> {
    T assignedGreeting();

    T participants();
}
