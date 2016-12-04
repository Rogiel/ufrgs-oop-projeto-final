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
package br.ufrgs.dunjeonsdragons;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;
import br.ufrgs.dunjeonsdragons.ui.CreatePlayerDialog;
import br.ufrgs.dunjeonsdragons.ui.GameWindow;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        final GameEntityFactory factory = new GameEntityFactory(
                Main.class.getClassLoader().getResource("GameData.xml")
        );
        final GameManager gameManager = new GameManager();

        System.out.println("Welcome To DunJeons & Dragons!");

        final GamePlayer player = createPlayerFromDialog(factory);
        gameManager.addEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME, player);

        final GameMap map = factory.createMap("TOWER_1", player);
        gameManager.addEntity(GameMap.DEFAULT_MAP_ENTITY_NAME, map);

//        final GameUIController uiController = new GameUIController(gameManager);

        final GameWindow gameWindow = new GameWindow(gameManager);
        final JFrame frame = new JFrame();
        frame.setContentPane(gameWindow.panel1);

        frame.pack();
        frame.setVisible(true);

//        while (true) {
//
////            uiController.handleUserInput();
////            gameManager.performTurn();
////            if (map.getState() == GameMap.State.DEFEAT) {
////                break;
////            }
//        }
    }

    private static GamePlayer createPlayerFromDialog(final GameEntityFactory factory) {
        final CreatePlayerDialog createPlayerDialog = new CreatePlayerDialog(factory);
        createPlayerDialog.pack();
        createPlayerDialog.setVisible(true);

        System.out.println("Character creation successful!");

        return createPlayerDialog.createPlayer();
    }
}
