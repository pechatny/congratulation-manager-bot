package com.pechatny.congratulation.manager.db;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;
    private Long chatId;
    private String message;

    public State(Long id, Long chatId, Status status, String message) {
        this.id = id;
        this.status = status;
        this.chatId = chatId;
        this.message = message;
    }

    public State(Long chatId, Status status, String message) {
        this(null, chatId, status, message);
    }

    public State() {

    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Long getChatId() {
        return chatId;
    }

    public State withUpdatedStatus(Status status) {
        return new State(this.id, this.chatId, status, this.message);
    }

    public State withUpdatedMessage(String message) {
        return new State(this.id, this.chatId, this.status, message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(id, state.id) && status == state.status && Objects.equals(chatId, state.chatId) && Objects.equals(message, state.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, chatId, message);
    }
}
