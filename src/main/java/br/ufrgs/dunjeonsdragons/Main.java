package br.ufrgs.dunjeonsdragons;

import br.ufrgs.dunjeonsdragons.effects.DamageEffect;
import br.ufrgs.dunjeonsdragons.gamelogic.GameEntity;
import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.GameCharacter;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;
import br.ufrgs.dunjeonsdragons.template.PlayerClassTemplate;
import br.ufrgs.dunjeonsdragons.template.PlayerRaceTemplate;
import br.ufrgs.dunjeonsdragons.template.loader.XMLTemplateLoader;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        GameEntityFactory factory = new GameEntityFactory();

        GamePlayer player = factory.createPlayer("HUMAN", "BERSERK");
        player.setLevel(10);
        System.out.println(player.getStrength());

//        Timer timer = new Timer();
//
//        final double frameRate = 60.0;
//        final long frameMillis = (long)(1/frameRate * 1000.0);
//
//        GameManager gameManager = new GameManager();
//
//        GameCharacter aCharacter = new GameCharacter();
//        aCharacter.setHealth(100);
//        aCharacter.addEffect(new DamageEffect(2, 1));
//        gameManager.addEntity(aCharacter);
//
//        // schedules a timer to fire repeatedly every 1/60 seconds
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                gameManager.update();
//                System.out.println(String.format("%.1f", aCharacter.getHealth()));
//            }
//
//        }, frameMillis, frameMillis);
    }
}
