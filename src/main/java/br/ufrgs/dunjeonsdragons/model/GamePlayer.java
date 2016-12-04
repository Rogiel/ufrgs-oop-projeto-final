package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.template.PlayerClassTemplate;
import br.ufrgs.dunjeonsdragons.template.PlayerRaceTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rogiel on 9/13/16.
 */
public class GamePlayer extends GameCharacter {

    public static final String DEFAULT_PLAYER_ENTITY_NAME = "GamePlayer";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A template for the player class
     */
    private PlayerClassTemplate classTemplate;

    /**
     * A template for the player race
     */
    private PlayerRaceTemplate raceTemplate;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The character name
     */
    private String name;

    /**
     * The player level
     */
    private int level;

    /**
     * The player experience
     */
    private int experience;

    // -----------------------------------------------------------------------------------------------------------------

    public GamePlayer(PlayerRaceTemplate raceTemplate, PlayerClassTemplate classTemplate) {
        this.raceTemplate = raceTemplate;
        this.classTemplate = classTemplate;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public double getDamage() {
        switch (classTemplate.getPrimaryStat()) {
            case "Strength":
                return getStrength();
            case "Vitality":
                return getVitality();
            case "Dexterity":
                return getDexterity();
            case "Intelligence":
                return getIntelligence();
            default:
                return 0;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void transferClass(PlayerClassTemplate newClassTemplate) {
        // TODO implement a check to assert if the player can trasnfer classes
        classTemplate = newClassTemplate;
    }

    public void addExperience(int experience) {
        // TODO perform level up if necessary
        this.experience += experience;
    }

    public boolean hasSubclassOptions() {
        // TODO check if the player has subclass options
        return false;
    }

    public List<PlayerClassTemplate> getSubclassOptions() {
        return new ArrayList<>();
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected void levelUp() {
        level += 1;
        setHealth(getVitality());
        // TODO check for the maximum level
    }

    public void setLevel(int level) {
        // FIXME take this out
        this.level = level;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public int getStrength() {
        return raceTemplate.getStrength(level) + classTemplate.getStrength(level);
    }

    public int getVitality() {
        return raceTemplate.getVitality(level) + classTemplate.getVitality(level);
    }

    public int getDexterity() {
        return raceTemplate.getDexterity(level) + classTemplate.getDexterity(level);
    }

    public int getIntelligence() {
        return raceTemplate.getIntelligence(level) + classTemplate.getIntelligence(level);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * @return the player class template
     */
    public PlayerClassTemplate getClassTemplate() {
        return classTemplate;
    }

    /**
     * @return the player race template
     */
    public PlayerRaceTemplate getRaceTemplate() {
        return raceTemplate;
    }

    /**
     * @return the player level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the player experience points
     */
    public int getExperience() {
        return experience;
    }

    // -----------------------------------------------------------------------------------------------------------------


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
