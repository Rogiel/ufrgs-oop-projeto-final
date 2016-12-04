package br.ufrgs.dunjeonsdragons.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
public abstract class GameCharacter extends GameObject {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The characters target
     */
    protected GameCharacter target;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The amount of health on the character
     */
    private double health;

    /**
     * The amount of energy on the character
     */
    private double energy;

    /**
     * A flag indicating if the target is dead
     */
    private boolean dead = false;

    // -----------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------

    public abstract double getDamage();

    public void attack() {
        if (target == null) {
            throw new RuntimeException("No target");
            // TODO proper exception
        }

        // take life out of the target
        final double targetHealth = target.getHealth();
        target.setHealth(targetHealth - getDamage());
    }

    // -----------------------------------------------------------------------------------------------------------------

    public GameCharacter getTarget() {
        return target;
    }

    public void setTarget(GameCharacter target) {
        this.target = target;
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
        if(isDead()) {
            return;
        }
        if (health <= 0) {
            health = 0;
            dead = true;
        }
        this.health = health;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        if (energy < 0) {
            energy = 0;
        }
        this.energy = energy;
    }

    public boolean isDead() {
        return dead;
    }

}
