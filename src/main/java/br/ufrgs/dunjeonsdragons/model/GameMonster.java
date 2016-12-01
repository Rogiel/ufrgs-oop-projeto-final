package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.template.MonsterTemplate;

/**
 * Created by Rogiel on 9/13/16.
 */
public class GameMonster extends GameCharacter {

    private final MonsterTemplate template;

    public GameMonster(final MonsterTemplate template) {
        this.template = template;
    }

}
