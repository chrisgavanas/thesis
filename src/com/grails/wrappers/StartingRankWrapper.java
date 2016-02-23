package com.grails.wrappers;

public class StartingRankWrapper {
    private int startingRank;

    public StartingRankWrapper(int startingRank) {
        this.startingRank = startingRank;
    }

    public int getValue() {
        return this.startingRank;
    }

    public void plus() {
        this.startingRank++;
    }
}
