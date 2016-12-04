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
     * The monster level
     */
    private int level = 1;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Create a new monster instance
     *
     * @param template create the monster from the given template
     */
    public GameMonster(final MonsterTemplate template, final int level) {
        this.template = template;
        this.level = level;
        setHealth(template.getHealthPerLevel() * level);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public double getDamage() {
        return template.getDamagePerLevel() * level;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * @return the monster name
     */
    public String getName() {
        return template.getName();
    }

}
