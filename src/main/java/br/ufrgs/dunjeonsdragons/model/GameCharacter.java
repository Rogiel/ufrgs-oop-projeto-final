/*
 * Copyright (c) 2016
 *      Rogiel Sulzbach, Júlio Balena & Gustavo Oliveira.
 *      All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *      This product includes software developed by the copyright holders
 *      and its contributors.
 * 4. Neither the name of the copyright holders nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

package br.ufrgs.dunjeonsdragons.model;

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
