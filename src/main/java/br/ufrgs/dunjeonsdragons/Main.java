package br.ufrgs.dunjeonsdragons;

import br.ufrgs.dunjeonsdragons.effects.DamageEffect;
import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;
import br.ufrgs.dunjeonsdragons.template.MapTemplate;
import br.ufrgs.dunjeonsdragons.ui.GameUIController;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        final GameEntityFactory factory = new GameEntityFactory();
        final Timer timer = new Timer();

        final double frameRate = 60.0;
        final long frameMillis = (long)(1/frameRate * 1000.0);

        final GameManager gameManager = new GameManager();

        final GamePlayer player = factory.createPlayer("HUMAN", "BERSERK");
        player.setHealth(100);
        player.addEffect(new DamageEffect(2, 1));
        gameManager.addEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME, player);

        final GameMap map = factory.createMap("DEFAULT_MAP", player);
        gameManager.addEntity(GameMap.DEFAULT_MAP_ENTITY_NAME, map);

        // schedules a timer to fire repeatedly every 1/60 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameManager.update();
//                System.out.println(String.format("%.1f", player.getHealth()));
            }

        }, frameMillis, frameMillis);

        GameUIController ui = new GameUIController(gameManager);
        ui.start();
    }
}
