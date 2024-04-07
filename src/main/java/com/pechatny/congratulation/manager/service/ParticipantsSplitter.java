package com.pechatny.congratulation.manager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class ParticipantsSplitter {
    public static final String NEW_LINE = System.getProperty("line.separator");

    public PreparedGreeting splitParticipants(String participants, String poem) {
        var participantList = participants.trim()
                .lines()
                .filter(line -> !line.isBlank())
                .map(String::trim)
                .collect(Collectors.toList());

        Collections.shuffle(participantList);

        var poemLines = poem.trim()
                .lines()
                .filter(line -> !line.isBlank())
                .map(String::trim)
                .toList();

        var participantsCount = participantList.size();
        var poemLinesCount = poemLines.size();

        var forAllParticipantsLineCount = poemLinesCount / participantsCount;
        var extraLines = poemLinesCount % participantsCount;

        var poemLinesList = new ArrayList<String>();
        var participantsIterator = participantList.iterator();
        var poemIterator = poemLines.iterator();

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

        return new PreparedGreeting(participantList, poemLinesList);
    }

}
