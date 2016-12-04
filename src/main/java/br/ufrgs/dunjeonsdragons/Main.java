package br.ufrgs.dunjeonsdragons;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;
import br.ufrgs.dunjeonsdragons.template.MapTemplate;
import br.ufrgs.dunjeonsdragons.template.PlayerClassTemplate;
import br.ufrgs.dunjeonsdragons.template.PlayerRaceTemplate;
import br.ufrgs.dunjeonsdragons.template.Template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        final GameEntityFactory factory = new GameEntityFactory();
        final GameManager gameManager = new GameManager();

        System.out.print("Welcome To DunJeons & Dragons! \n\n~~~ AQUI VAI A LISTA DE COMANDOS ~~~\n\n");

        final GamePlayer player = createPlayerFromInput(factory);
        gameManager.addEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME, player);

        final GameMap map = factory.createMap("TOWER_1", player);
        gameManager.addEntity(GameMap.DEFAULT_MAP_ENTITY_NAME, map);

        while (true) {
            gameManager.performTurn();
            if (map.getState() != GameMap.State.RUNNING) {
                break;
            }
        }
    }

    private static GamePlayer createPlayerFromInput(final GameEntityFactory factory) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Create your Character \n");
        System.out.print("Character name: ");
        final String name = reader.readLine();

        System.out.print("Choose your Race (HUMAN - ORC - ELF): ");
        final String race = reader.readLine();

        final Template raceTemplateRaw = factory.loader.load(race);
        if (raceTemplateRaw == null) {
            System.err.println("Race " + race + " is invalid.");
        }

        final PlayerRaceTemplate raceTemplate = (PlayerRaceTemplate) raceTemplateRaw;

        final StringBuilder builder = new StringBuilder("Choose your Class (");
        for(PlayerClassTemplate allowedClassTemplate : raceTemplate.getStartingClasses()) {
            builder.append(allowedClassTemplate.getIdentifier()+" - ");
        }
        builder.delete(builder.length() - 3, builder.length());
        builder.append("): ");

        System.out.print(builder.toString());
        final String classe = reader.readLine();

        final PlayerClassTemplate classTemplate = (PlayerClassTemplate) factory.loader.load(classe);
        if (classTemplate == null) {
            System.err.println("Class " + classe + " is invalid.");
        }
        if (!raceTemplate.getStartingClasses().contains(classTemplate)) {
            System.err.println("Race " + race + " cannot be of class " + classe);
        }

        final GamePlayer player = factory.createPlayer(race, classe, factory.loader.getExperienceTable());
        if (!name.isEmpty()) {
            player.setName(name);
        }

        System.out.print("Character creation succesful! \n");
        return player;
    }
}
