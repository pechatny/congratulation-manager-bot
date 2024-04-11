package com.pechatny.congratulation.manager.service.greeting;

import java.util.ArrayList;
import java.util.List;

public class GrMerged implements Greeting<String> {
    public static final String NEW_LINE = System.getProperty("line.separator");
    private final List<String> participants;
    private final List<String> lines;

    public GrMerged(Greeting<List<String>> greeting) {
        this(greeting.participants(), greeting.assignedGreeting());
    }

    public GrMerged(List<String> participants, List<String> lines) {
        this.participants = participants;
        this.lines = lines;
    }

    @Override
    public String assignedGreeting() {
        var participantsCount = participants.size();
        var poemLinesCount = lines.size();

        var forAllParticipantsLineCount = poemLinesCount / participantsCount;
        var extraLines = poemLinesCount % participantsCount;

        var poemLinesList = new ArrayList<String>();
        var participantsIterator = participants.iterator();
        var poemIterator = lines.iterator();

        while (participantsIterator.hasNext()) {
            participantsIterator.next();
            StringBuilder lineBuilder = new StringBuilder();
            for (int i = forAllParticipantsLineCount; i != 0; i--) {
                lineBuilder.append(poemIterator.next());
                lineBuilder.append(NEW_LINE);
            }

            if (extraLines > 0) {
                lineBuilder.append(poemIterator.next());
                lineBuilder.append(NEW_LINE);
                extraLines--;
            }
            poemLinesList.add(lineBuilder.toString());
        }

        if (participants.size() != poemLinesList.size()) throw new IllegalArgumentException();

        StringBuilder buffer = new StringBuilder();
        var participantIterator = participants.iterator();
        poemIterator = poemLinesList.iterator();
        var counter = 0;

        while (participantIterator.hasNext() && poemIterator.hasNext()) {
            buffer.append(++counter).append(". ");
            buffer.append(participantIterator.next());
            buffer.append(NEW_LINE);
            buffer.append(poemIterator.next());
            buffer.append(NEW_LINE);
        }

        return buffer.toString();
    }

    @Override
    public String participants() {
        return participants.toString();
    }
}
