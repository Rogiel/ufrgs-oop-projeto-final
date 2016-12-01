package br.ufrgs.dunjeonsdragons.effects;

import br.ufrgs.dunjeonsdragons.model.GameCharacter;

/**
 * Created by Rogiel on 9/13/16.
 */
public class HealEffect extends AbstractTimedEffect {

    /**
     * The rate to apply damage at (in hit points per second)
     */
    private double damageRate;

    public HealEffect(double duration, double damageRate) {
        super(duration);
        this.damageRate = damageRate;
    }

    @Override
    void doApply(GameCharacter character, double elapsedTime) {
        double damage = damageRate * elapsedTime;
        character.setHealth(character.getHealth() - damage);
        // TODO implement
    }

}
