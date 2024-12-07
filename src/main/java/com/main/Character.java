package com.main;

public abstract class Character {
    private String name;
    private HpBar hp;

    public Character(String name, HpBar hp) {
        this.name = name;
        this.hp = hp;
    }

    public HpBar getHp() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public abstract void takeDamage(int damage);
}

