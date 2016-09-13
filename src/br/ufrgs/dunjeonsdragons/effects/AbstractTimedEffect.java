package br.ufrgs.dunjeonsdragons.effects;

import br.ufrgs.dunjeonsdragons.model.GameCharacter;

/**
 * Created by Rogiel on 9/13/16.
 */
public abstract class AbstractTimedEffect implements Effect {

    /**
     * The effect duration (in seconds)
     */
    private double duration;

    /**
     * The elapsed effect duration
     */
    private double elapsed;

    public AbstractTimedEffect(double duration) {
        this.duration = duration;
    }

    /**
     * Performs the actual effect implementation.
     *
     * This method is automatically truncates and finishes the effect if needed.
     *
     * @param character   the character to apply the effect to
     * @param elapsedTime the time elapsed since the last game frame
     */
    abstract void doApply(GameCharacter character, double elapsedTime);

    @Override
    public EffectApplyState apply(GameCharacter character, double elapsedTime) {
        if(elapsed >= duration) {
            return EffectApplyState.EXPIRE_EFFECT;
        }

        double remaining = duration - elapsedTime;
        double effectiveRemaining = Math.min(elapsedTime, remaining);

        elapsed += elapsedTime;

        doApply(character, effectiveRemaining);
        if(effectiveRemaining < elapsedTime) {
            return EffectApplyState.EXPIRE_EFFECT;
        }
        return EffectApplyState.KEEP_EFFECT;
    }
}
