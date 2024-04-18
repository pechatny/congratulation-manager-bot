package com.pechatny.congratulation.manager.command;

import com.pechatny.congratulation.manager.bot.Bot;
import com.pechatny.congratulation.manager.db.FakeStateRepository;
import com.pechatny.congratulation.manager.db.State;
import com.pechatny.congratulation.manager.db.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ParticipantsCommandTest {
    @Mock
    Bot bot;

    @Test
    public void shouldSaveStateAndSendMessageToChat() throws TelegramApiException {
        State state = new State(1L, 1L, Status.START, "Test message");

        State expectedState = state.withUpdatedStatus(Status.WAIT_FOR_POETRY);
        List<State> states = List.of(
                state.withUpdatedStatus(Status.START),
                state.withUpdatedStatus(Status.WAIT_FOR_PARTICIPANTS),
                state.withUpdatedStatus(Status.WAIT_FOR_POETRY)
        );

        Mockito.when(bot.execute(Mockito.any(BotApiMethod.class))).thenReturn(true);
        String message = "Test message";
        var stateRepository = new FakeStateRepository(states);
        var command = new ParticipantsCommand(bot, state, message, stateRepository);

        command.execute();
        Assertions.assertEquals(expectedState, stateRepository.lastSavedState());


        Mockito.verify(bot, times(1)).execute(Mockito.any(BotApiMethod.class));
    }
}
