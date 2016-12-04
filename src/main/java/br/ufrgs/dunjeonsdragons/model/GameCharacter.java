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

    /**
     * A flag indicating if the target is dead
     */
    private boolean dead = false;

    // -----------------------------------------------------------------------------------------------------------------

    public abstract double getMaxHealth();

    public void resetHealth() {
        setHealth(getMaxHealth());
    }

    // -----------------------------------------------------------------------------------------------------------------

    public abstract double getDamage();

    public void attack(GameCharacter target) {
        // take life out of the target
        final double targetHealth = target.getHealth();
        target.setHealth(targetHealth - getDamage());

        if(target.getHealth() == 0) {
            didKill(target);
        }
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
        if(isDead()) {
            return;
        }
        if (health <= 0) {
            health = 0;
            dead = true;
        }
        this.health = health;
    }

    public boolean isDead() {
        return dead;
    }

}
