package com.main;

public class Monster extends Character {
    public Monster(String name, HpBar hp){
        super(name, hp);
    }

    @Override
    public void takeDamage(int damage) {
        System.out.println("monster take demgae");
        super.getHp().reduceHP(damage);
    }
}
