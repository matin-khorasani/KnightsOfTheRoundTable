package Model.Enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum KnightName {
    ARTHUR(KnightClass.COMMANDER, 200, 50, 15, 60, 30,
            Arrays.asList(SkillName.SHIELD_BASH, SkillName.FORTIFY, SkillName.ARMOR_BREAK,
                    SkillName.RALLY)),
    MORDRED(KnightClass.COMMANDER, 180, 40, 20, 50, 35,
            Arrays.asList(SkillName.SHIELD_BASH, SkillName.STRIKE_COMMAND, SkillName.ARMOR_BREAK,
                    SkillName.RALLY)),
    LANCELOT(KnightClass.WARRIOR, 170, 60, 5, 70, 50,
            Arrays.asList(SkillName.SLASH, SkillName.HEAVY_STRIKE,
                    SkillName.LIFE_STEAL, SkillName.BERSERK)),
    GALAHAD(KnightClass.HEALER, 150, 20, 25, 40, 70,
            Arrays.asList(SkillName.HEAL, SkillName.GROUP_HEAL,
                    SkillName.REVIVE, SkillName.CLEANSE)),
    MORGAN(KnightClass.MAGE, 120, 10, 45, 35, 60,
            Arrays.asList(SkillName.FIREBALL, SkillName.LIGHTNING_STRIKE,
                    SkillName.ARCANE_SURGE, SkillName.SILENCE)),
    MERLIN(KnightClass.MAGE, 130, 5, 55, 40, 55,
            Arrays.asList(SkillName.FIREBALL, SkillName.LIGHTNING_STRIKE,
                    SkillName.ICE_BLAST, SkillName.ARCANE_SURGE));

    private final KnightClass knightClass;
    private final int maxHp;
    private final int attack;
    private final int magicAttack;
    private final int defense;
    private final int speed;
    private final List<SkillName> skills;

    KnightName(KnightClass knightClass, int maxHp, int attack, int magicAttack, int defense, int speed, List<SkillName> skills) {
        this.knightClass = knightClass;
        this.maxHp = maxHp;
        this.attack = attack;
        this.magicAttack = magicAttack;
        this.defense = defense;
        this.speed = speed;
        this.skills = skills;
    }

    public KnightClass getKnightClass() {
        return knightClass;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getMagicAttack() {
        return magicAttack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public List<SkillName> getSkills() {
        return skills;
    }
}
