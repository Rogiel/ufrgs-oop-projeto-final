package br.ufrgs.dunjeonsdragons.ui;

import br.ufrgs.dunjeonsdragons.GameEntityFactory;
import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.GameLevel;
import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GameMonster;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;
import br.ufrgs.dunjeonsdragons.template.ExperienceTableEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.text.NumberFormat;

/**
 * Created by Gustavo on 04/12/2016.
 */
public class GameWindow {
    public JPanel panel1;
    private JButton attackButton;
    private JButton nextMapButton;
    private JTextPane logPanel;
    private JButton combatButton;
    private JLabel playerNameField;
    private JLabel playerLevelField;
    private JLabel playerExperienceField;
    private JLabel playerHpField;
    private JLabel monsterHpField;
    private JLabel monsterNameLabel;
    private JProgressBar experienceBar;
    private JProgressBar playerHpBar;
    private JProgressBar monsterHpBar;
    private JProgressBar gameProgressBar;
    private JButton resetMapButton;
    private JLabel monsterDamageField;
    private JLabel playerDamageField;
    private JProgressBar towerAscencionBar;
    private JLabel playerRaceField;
    private JLabel playerClassField;
    private JButton transferClassButton;
    private JLabel currentTowerLevel;
    private JLabel maxTowerLevel;
    private JScrollPane logScrollPane;

    private final GameManager gameManager;

    public GameWindow(final GameManager gameManager) {
        this.gameManager = gameManager;

        attackButton.addActionListener(e -> handleAttack());
        combatButton.addActionListener(e -> handleCombat());
//        nextLevelButton.addActionListener(e -> nextLevel());
        nextMapButton.addActionListener(e -> nextMap());
        resetMapButton.addActionListener(e -> resetMap());
        transferClassButton.addActionListener(e -> executeClassTransfer());

        // hooks into the "System.out" stream to redirect the output to the GUI console
        PrintStream outputStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                super.println(x);
                logPanel.setText(
                        logPanel.getText() + "\n" + x
                );
                logScrollPane.scrollRectToVisible(new Rectangle(0, logPanel.getBounds(null).height, 1, 1));
            }
        };
        System.setOut(outputStream);

        updateUI();
    }

    private void updateUI() {
        final GamePlayer player = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        playerNameField.setText(player.getName());
        playerLevelField.setText(Integer.toString(player.getLevel()));
        playerHpField.setText(Integer.toString((int) player.getHealth()) + "/" + Integer.toString((int) player.getMaxHealth()));
        playerExperienceField.setText(Long.toString(player.getExperience()));
        playerHpBar.setMaximum((int) (double) player.getMaxHealth());
        playerHpBar.setValue((int) (double) player.getHealth());
        playerDamageField.setText(Integer.toString((int) player.getDamage()));

        playerRaceField.setText(player.getRaceTemplate().getName());
        playerClassField.setText(player.getClassTemplate().getName());

        transferClassButton.setEnabled(player.hasSubclassOptions());

        experienceBar.setMaximum((int) player.getExperienceRequiredForNextLevel() - (int) player.getMinimumExperienceForCurrentLevel());
        experienceBar.setValue((int) player.getExperience() - (int) player.getMinimumExperienceForCurrentLevel());

        final GameMap gameMap = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        gameProgressBar.setMaximum(gameMap.getLevelCountInMap() - 1);
        gameProgressBar.setValue(gameMap.getCurrentLevelIndex());

        nextMapButton.setEnabled(gameMap.getState() == GameMap.State.MAP_COMPLETE);

        maxTowerLevel.setText(Integer.toString(gameMap.getTemplate().getMaxLevels()));
        currentTowerLevel.setText(Integer.toString((int) gameMap.getMapLevel()));

        towerAscencionBar.setMaximum(gameMap.getTemplate().getMaxLevels());
        towerAscencionBar.setValue((int) gameMap.getMapLevel());

        final GameLevel gameLevel = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
        if (gameLevel != null && gameLevel.getMonster() != null) {
            final GameMonster monster = gameLevel.getMonster();
            monsterNameLabel.setText(monster.getName());
            monsterHpField.setText(Integer.toString((int) monster.getHealth()) + "/" + Integer.toString((int) monster.getMaxHealth()));
            monsterHpBar.setMaximum((int) (double) monster.getMaxHealth());
            monsterHpBar.setValue((int) (double) monster.getHealth());
            monsterDamageField.setText(Integer.toString((int) monster.getDamage()));

            attackButton.setEnabled(true);
            combatButton.setEnabled(true);
            resetMapButton.setEnabled(false);
        } else {
            monsterNameLabel.setText(null);
            monsterHpField.setText("0/0");
            monsterHpBar.setMaximum(1);
            monsterHpBar.setValue(0);
            monsterDamageField.setText(null);

            resetMapButton.setEnabled(true);
            attackButton.setEnabled(false);
            combatButton.setEnabled(false);
        }
    }

    private void handleCombat() {
        final GameLevel gameLevel = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
        final GameMonster monster = gameLevel.getMonster();
        final GamePlayer player = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);

        while (!monster.isDead()) {
            if (player.isDead()) {
                handleStatus();
                return;
            }
            handleAttack();
        }
        handleStatus();
    }

    private void handleAttack() {
        final GamePlayer player = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        final GameLevel gameLevel = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);

        if (player.isDead()) {
            return;
        }

        player.attack(gameLevel.getMonster()); // player attacks mob
        if (!gameLevel.getMonster().isDead()) {
            gameLevel.getMonster().attack(player); // mob attacks player
        }

        if (gameLevel.isComplete()) {
            final GameMap gameMap = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
            gameMap.nextLevel();
        }
        updateUI();
    }

    private void nextMap() {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        if (map.getState() != GameMap.State.MAP_COMPLETE) {
            System.err.println("You must first win this map before going to the next.");
            return;
        }
        map.nextMap();
        updateUI();
    }

    private void executeClassTransfer() {
        final GamePlayer player = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        if (player.hasSubclassOptions()) {
            final ClassTransferDialog classTransferDialog = new ClassTransferDialog(player.getSubclassOptions());
            classTransferDialog.pack();
            classTransferDialog.setVisible(true);
            if (classTransferDialog.getSelectedPlayerClassTemplate() != null) {
                player.transferClass(classTransferDialog.getSelectedPlayerClassTemplate());
            }
        }
    }

    private void resetMap() {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        map.resetMap();
        updateUI();
    }

    private void handleStatus() {
        updateUI();
    }
}
