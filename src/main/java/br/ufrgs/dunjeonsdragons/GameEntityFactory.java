package br.ufrgs.dunjeonsdragons;

import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GameMonster;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;
import br.ufrgs.dunjeonsdragons.template.MapTemplate;
import br.ufrgs.dunjeonsdragons.template.MonsterTemplate;
import br.ufrgs.dunjeonsdragons.template.PlayerClassTemplate;
import br.ufrgs.dunjeonsdragons.template.PlayerRaceTemplate;
import br.ufrgs.dunjeonsdragons.template.loader.TemplateLoader;
import br.ufrgs.dunjeonsdragons.template.loader.XMLTemplateLoader;

/**
 * Created by Rogiel on 9/20/16.
 */
public class GameEntityFactory {

    /**
     * The template loader instance
     */
    public TemplateLoader loader = new XMLTemplateLoader(Main.class.getClassLoader().getResource("GameData.xml"));

    /**
     * Creates a new player object from the given classIdentifier template identifier
     *
     * @param classIdentifier the player class template identifier
     * @return a newly created GamePlayer object
     */
    public GamePlayer createPlayer(String raceIdentifier, String classIdentifier) {
        final PlayerRaceTemplate raceTemplate = (PlayerRaceTemplate) loader.load(raceIdentifier);
        final PlayerClassTemplate classTemplate = (PlayerClassTemplate) loader.load(classIdentifier);

        if(raceTemplate == null) {
            // TODO use a proper exception here
            throw new RuntimeException("Invalid race template");
        }

        if(classTemplate == null) {
            // TODO use a proper exception here
            throw new RuntimeException("Invalid class template");
        }

        return new GamePlayer(
                (PlayerRaceTemplate) loader.load(raceIdentifier),
                (PlayerClassTemplate) loader.load(classIdentifier)
        );
    }

    /**
     * Creates a new monster object from the given monster template identifier
     *
     * @param monsterTemplateIdentifier the monster template identifier
     * @param level                     the monster level
     * @return a newly created GameMonster object
     */
    public GameMonster createMonster(String monsterTemplateIdentifier, final int level) {
        return new GameMonster(
                (MonsterTemplate) loader.load(monsterTemplateIdentifier), level
        );
    }

    /**
     * Creates a new map object from the given map template identifier
     * and player instance
     *
     * @param mapTemplateIdentifier the map template identifier
     * @return a newly created GameMap object
     */
    public GameMap createMap(String mapTemplateIdentifier, final GamePlayer player) {
        return new GameMap(
                (MapTemplate) loader.load(mapTemplateIdentifier),
                player
        );
    }

}
