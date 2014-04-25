package com.jayway.leaderboard.actors;

import akka.actor.UntypedActor;
import com.jayway.leaderboard.dto.Score;
import com.jayway.leaderboard.messages.RequestTopScoreMessage;
import com.jayway.leaderboard.messages.TopScoresResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelActor extends UntypedActor {

    private final Map<String, Score> scores = new HashMap<String, Score>();



    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Score) {
            Score newScore = (Score)message;

            if (scores.containsKey(newScore.user())) {
                Score currentScore = scores.get(newScore.user());

                if (newScore.score() >= currentScore.score()) {
                    scores.remove(currentScore.user());
                    scores.put(newScore.user(), newScore);
                }
            } else {
                scores.put(newScore.user(), newScore);
            }
        } else if (message instanceof RequestTopScoreMessage) {
            List<Score> all = new ArrayList<Score>();
            all.addAll(scores.values());

            Collections.sort(all);
            Collections.reverse(all);

            int topIndex = all.size() < 15 ? all.size() : 15;

            TopScoresResponse response = new TopScoresResponse(all.subList(0, topIndex));

            sender().tell(response, self());
        }
    }
}
