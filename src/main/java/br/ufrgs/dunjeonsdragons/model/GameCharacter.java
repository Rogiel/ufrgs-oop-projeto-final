package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.effects.Effect;
import br.ufrgs.dunjeonsdragons.effects.EffectApplyState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
public class GameCharacter extends GameObject {

    /**
     * A list of the currently active skills for the player
     */
    private List<GameSkill> skills;

    /**
     * A list containing all active effects on the character
     */
    private List<Effect> activeEffects;

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

    public GameCharacter() {
        skills = new ArrayList<>();
        activeEffects = new ArrayList<>();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void castSkill(GameSkill skill) {
        if (!this.skills.contains(skill)) {
            throw new RuntimeException();
            // TODO throw a proper error
        }

        skill.cast(this, Collections.singletonList(target));
    }

    public void attack() {
        if (target == null) {
            throw new RuntimeException("No target");
            // TODO proper exception
        }

        // take life out of the target
        final double targetHealth = target.getHealth();
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
    public void update(double elapsedTime) {
        super.update(elapsedTime);

        List<Effect> effectsToBeRemoved = new ArrayList<>();

        // apply the effects
        for (Effect effect : activeEffects) {
            EffectApplyState state = effect.apply(this, elapsedTime);
            if (state == EffectApplyState.EXPIRE_EFFECT) {
                effectsToBeRemoved.add(effect);
            }
        }

        // now remove the effects. We remove them after applying to keep a fixed view for all other effects
        activeEffects.removeAll(effectsToBeRemoved);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public List<Effect> getActiveEffects() {
        return activeEffects;
    }

    public void addEffect(Effect effect) {
        activeEffects.add(effect);
    }

    public void addEffects(List<Effect> effects) {
        activeEffects.addAll(effects);
    }

    public void removeEffect(Effect effect) {
        activeEffects.remove(effect);
    }

    public void removeEffects(List<Effect> effects) {
        activeEffects.removeAll(effects);
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
