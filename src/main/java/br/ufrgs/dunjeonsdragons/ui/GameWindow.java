package br.ufrgs.dunjeonsdragons.ui;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.GameLevel;
import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GameMonster;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.StringTokenizer;

/**
 * Created by Gustavo on 04/12/2016.
 */
public class GameWindow {
    public JPanel panel1;
    private JButton attackButton;
    private JButton nextLevelButton;
    private JButton nextMapButton;
    private JTextPane textPane1;
    private JButton combatButton;

    private final GameManager gameManager;

    public GameWindow(final GameManager gameManager) {
        this.gameManager = gameManager;

        attackButton.addActionListener(e -> handleAttack());
        combatButton.addActionListener(e -> handleCombat());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void handleCombat() {
        final GameLevel gameLevel = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
        final GameMonster monster = gameLevel.getMonster();

        while (!monster.isDead()) {
            handleAttack();
        }
        handleStatus();
    }

    private void handleAttack() {
        final GamePlayer player = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        final GameLevel gameLevel = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);

        player.attack(gameLevel.getMonster()); // player attacks mob
        if (!gameLevel.getMonster().isDead()) {
            gameLevel.getMonster().attack(player); // mob attacks player
        }

        if (gameLevel.isComplete()) {
            final GameMap gameMap = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
            gameMap.nextLevel();
        }

    }

    private void nextLevel() {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        if (!map.getCurrentLevel().isComplete()) {
            System.err.println("Current level is not complete.");
            return;
        }
        map.nextLevel();
    }

    private void nextMap() {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        if (map.getState() != GameMap.State.MAP_COMPLETE) {
            System.err.println("You must first win this map before going to the next.");
            return;
        }
        map.nextMap();
    }

    private void resetMap() {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        map.resetMap();
    }

    private void handleStatus() {
        final GamePlayer character = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        System.out.println("Character: " + character.getName() + ", Race: " + character.getRaceTemplate().getName() + ", Classe: " + character.getClassTemplate().getName() + " Level: " + character.getLevel());
        System.out.println("\tHealth: " + NumberFormat.getNumberInstance().format(character.getHealth()));
        System.out.println("\tDamage: " + NumberFormat.getNumberInstance().format(character.getDamage()));

        final GameLevel level = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
        if (level != null) {
            final GameMonster monster = level.getMonster();
            if (monster == null) {
                System.out.println("Monster:");
                System.out.println("\tNo monster on current level");
            } else {
                System.out.println("Monster: " + monster.getName());
                System.out.println("\tHealth: " + NumberFormat.getNumberInstance().format(monster.getHealth()));
                System.out.println("\tDamage: " + monster.getDamage());
            }
        }
    }

    private void handleExperience() {
        final GamePlayer character = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        System.out.println("Character: " + character.getName() + ", Race: " + character.getRaceTemplate().getName() + ", Classe: " + character.getClassTemplate().getName());
        System.out.println("\tExperience: " + NumberFormat.getNumberInstance().format(character.getExperience()));
    }
}
