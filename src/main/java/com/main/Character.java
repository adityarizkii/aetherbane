package com.main;

public abstract class Character {
    private String name;
    private HpBar hp;
    private int score;

    public Character(String name, HpBar hp) {
        this.name = name;
        this.hp = hp;
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public HpBar getHp() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public abstract void takeDamage(int damage);
}

