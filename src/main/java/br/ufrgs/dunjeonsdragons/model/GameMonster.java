package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.template.MonsterTemplate;

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
     * @param level    the monster level
     */
    public GameMonster(final MonsterTemplate template, final int level) {
        this.template = template;
        this.level = level;
        resetHealth();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public double getDamage() {
        return template.getDamagePerLevel() * level;
    }

    @Override
    public double getMaxHealth() {
        return template.getHealthPerLevel() * level;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExperienceOnKill() {
        return template.getExperiencePerLevel() * level;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * @return the monster name
     */
    @Override
    public String getName() {
        return template.getName();
    }

}
