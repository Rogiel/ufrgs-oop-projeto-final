package br.ufrgs.dunjeonsdragons.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
public abstract class GameCharacter extends GameObject {

    /**
     * The amount of health on the character
     */
    private double health;

    // -----------------------------------------------------------------------------------------------------------------

    public abstract String getName();

    // -----------------------------------------------------------------------------------------------------------------

    public abstract double getMaxHealth();

    public void resetHealth() {
        health = getMaxHealth();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public abstract double getDamage();

    public void attack(GameCharacter target) {
        if (isDead() || target.isDead()) {
            return;
        }

        // take life out of the target
        final double targetHealth = target.getHealth();
        target.setHealth(targetHealth - getDamage());

        System.out.println(getName() + " dealt " + getDamage() + " damage to " + target.getName());

        if (target.getHealth() == 0) {
            didKill(target);
            target.didDie(this);
        }
    }

    public void didDie(GameCharacter killer) {
        System.out.println(getName() + " has died to " + killer.getName());
    }

    public void didKill(GameCharacter character) {

    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void performTurn(long turn) {
        super.performTurn(turn);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        if (health <= 0) {
            this.health = 0;
            return;
        }
        this.health = health;
    }

    public boolean isDead() {
        return health == 0;
    }

}
