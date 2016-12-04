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

package br.ufrgs.dunjeonsdragons.gamelogic;

import br.ufrgs.dunjeonsdragons.event.GameEvent;
import br.ufrgs.dunjeonsdragons.event.GameEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    /**
     * A list entities currently managed by the game
     */
    private Map<String, GameEntity> entities;

    /**
     * A list of active game event listeners
     */
    private List<GameEventListener> eventListeners = new ArrayList<>();

    /**
     * The current game turn
     */
    private long turn;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new GameManager instance
     */
    public GameManager() {
        entities = new HashMap<>();
        turn = 0;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void addEntity(String name, GameEntity entity) {
        if (entities.put(name, entity) != null) {
            return;
        }
        entity.didAddToGameManager(this);
    }

    public void removeEntity(String name) {
        final GameEntity removed = entities.remove(name);
        if (removed == null) {
            return;
        }
        removed.didRemoveFromGameManager(this);
    }

    public GameEntity getEntity(String name) {
        return entities.get(name);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void addEventListener(GameEventListener eventListener) {
        this.eventListeners.add(eventListener);
    }

    public void removeEventListener(GameEventListener eventListener) {
        this.eventListeners.remove(eventListener);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void performTurn() {
        final List<GameEntity> entities = new ArrayList<>(this.entities.values());
        for (GameEntity entity : entities) {
            entity.performTurn(turn);
        }
        turn++;
    }

    public void triggerEvent(final GameEvent event) {
        for (final GameEventListener listener : eventListeners) {
            listener.onGameEvent(event);
        }
    }

}
