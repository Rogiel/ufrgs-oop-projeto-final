package br.ufrgs.dunjeonsdragons.model;

import br.ufrgs.dunjeonsdragons.template.ExperienceTableEntry;
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

    /**
     * The players experience table
     */
    private List<ExperienceTableEntry> experienceTable;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The character name
     */
    private String name;

    /**
     * The player level
     */
    private int level = 1;

    /**
     * The player experience
     */
    private long experience;

    // -----------------------------------------------------------------------------------------------------------------

    public GamePlayer(PlayerRaceTemplate raceTemplate, PlayerClassTemplate classTemplate, List<ExperienceTableEntry> experienceTable) {
        this.raceTemplate = raceTemplate;
        this.classTemplate = classTemplate;
        this.experienceTable = experienceTable;
        resetHealth();
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
            case "StrenghIntelligence":
                return getStrength() + getIntelligence();
            default:
                return 0;
        }
    }

    @Override
    public double getMaxHealth() {
        return getVitality();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void didKill(GameCharacter character) {
        if (character instanceof GameMonster) {
            addExperience(((GameMonster) character).getExperienceOnKill());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void transferClass(PlayerClassTemplate newClassTemplate) {
        if (!classTemplate.getSubclasses().contains(newClassTemplate)) {
            // TODO use a proper exception
            throw new RuntimeException("The player cannot transfer to the class " + newClassTemplate.getName());
        }
        classTemplate = newClassTemplate;
        resetHealth();
    }

    public void addExperience(long experienceToAdd) {
        this.experience += experienceToAdd;
        System.out.println(name + " got " + experienceToAdd + " experience points.");
        for (ExperienceTableEntry entry : experienceTable) {
            if (this.experience >= entry.getExperience() && this.level < entry.getLevel()) {
                this.level = (int) entry.getLevel();
            }
        }
    }

    public boolean hasSubclassOptions() {
        // TODO check if the player has subclass options
        return false;
    }

    public List<PlayerClassTemplate> getSubclassOptions() {
        return new ArrayList<>();
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
    public long getExperience() {
        return experience;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
