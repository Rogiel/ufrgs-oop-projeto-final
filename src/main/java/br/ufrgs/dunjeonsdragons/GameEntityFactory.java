package br.ufrgs.dunjeonsdragons;

import br.ufrgs.dunjeonsdragons.model.GameMonster;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;
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
    private TemplateLoader loader = new XMLTemplateLoader(Main.class.getClassLoader().getResource("GameData.xml"));

    /**
     * Creates a new player object from the given classIdentifier template identifier
     *
     * @param classIdentifier the player class template identifier
     * @return a newly created GamePlayer object
     */
    public GamePlayer createPlayer(String raceIdentifier, String classIdentifier) {
        return new GamePlayer(
                (PlayerRaceTemplate) loader.load(raceIdentifier),
                (PlayerClassTemplate) loader.load(classIdentifier)
        );
    }

    /**
     * Creates a new monster object from the given monster template identifier
     *
     * @param monsterTemplateIdentifier the monster template identifier
     * @return a newly created GameMonster object
     */
    public GameMonster createMonster(String monsterTemplateIdentifier) {
        return new GameMonster();
    }

}
