package br.ufrgs.dunjeonsdragons.effects;

import br.ufrgs.dunjeonsdragons.model.GameCharacter;

/**
 * Created by Rogiel on 9/13/16.
 */
public interface Effect {

    /**
     * Applies the effect to the character
     *
     * @param character   the character to apply the effect to
     * @param elapsedTime the time elapsed since the last game frame
     * @return the effect apply state
     */
    EffectApplyState apply(GameCharacter character, double elapsedTime);

}
