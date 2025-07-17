package com.quest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestStatistics implements Serializable {

    private int totalGamesPlayed = 0;
    private int wins = 0;
    private int losses = 0;

    public void incrementWin() {
        this.totalGamesPlayed++;
        this.wins++;
    }

    public void incrementLoss() {
        this.totalGamesPlayed++;
        this.losses++;
    }
}