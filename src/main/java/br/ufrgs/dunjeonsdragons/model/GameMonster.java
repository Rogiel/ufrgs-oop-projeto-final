package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.template.MonsterTemplate;

/**
 * Created by Rogiel on 9/13/16.
 */
public class GameMonster extends GameCharacter {

    /**
     * The monster template
     */
    private final MonsterTemplate template;

    /**
     * Create a new monster instance
     *
     * @param template create the monster from the given template
     */
    public GameMonster(final MonsterTemplate template) {
        this.template = template;
    }

    /**
     * @return the monster name
     */
    public String getName() {
        return template.getName();
    }

}
