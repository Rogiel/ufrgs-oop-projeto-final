package br.ufrgs.dunjeonsdragons;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        final GameEntityFactory factory = new GameEntityFactory();
        final GameManager gameManager = new GameManager();

        final GamePlayer player = createPlayerFromInput(factory);
        gameManager.addEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME, player);

        final GameMap map = factory.createMap("DEFAULT_MAP", player);
        gameManager.addEntity(GameMap.DEFAULT_MAP_ENTITY_NAME, map);

        gameManager.addEventListener(event -> {
            System.out.println(event);
        });

        while(true) {
            gameManager.performTurn();
            if(map.getState() != GameMap.State.RUNNING) {
                break;
            }
        }
    }

    private static GamePlayer createPlayerFromInput(final GameEntityFactory factory) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Character name: ");
        final String name = reader.readLine();

        System.out.print("Choose your Race (HUMAN - RACE-B - RACE-C): ");
        final String race = reader.readLine();

        System.out.print("Choose your Class (WARRIOR - WIZARD - ROGUE): ");
        final String classe = reader.readLine();

        final GamePlayer player = factory.createPlayer(race, classe);
        if(!name.isEmpty()) {
            player.setName(name);
        }

        return player;
    }
}
