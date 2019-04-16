package com.example.galaxian;

public class player {

    String name;
    int score;

    public player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public player() {
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
