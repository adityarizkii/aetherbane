package com.main;

public class Wizard extends Character {
    public Wizard(String name, HpBar hp){
        super(name, hp);
    }

    @Override
    public void takeDamage(int damage) {
        System.out.println("wizard take damage");
        super.getHp().reduceHP(damage);
    }
}
