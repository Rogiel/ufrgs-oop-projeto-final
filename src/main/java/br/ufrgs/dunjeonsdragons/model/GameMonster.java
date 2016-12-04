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

import br.ufrgs.dunjeonsdragons.template.MonsterTemplate;

public class GameMonster extends GameCharacter {

    /**
     * The monster template
     */
    private final MonsterTemplate template;

    /**
     * The monster level
     */
    private int level = 1;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Create a new monster instance
     *
     * @param template create the monster from the given template
     * @param level    the monster level
     */
    public GameMonster(final MonsterTemplate template, final int level) {
        this.template = template;
        this.level = level;
        resetHealth();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public double getDamage() {
        return template.getDamagePerLevel() * level;
    }

    @Override
    public double getMaxHealth() {
        return template.getHealthPerLevel() * level;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExperienceOnKill() {
        return template.getExperiencePerLevel() * level;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * @return the monster name
     */
    @Override
    public String getName() {
        return template.getName();
    }

}
