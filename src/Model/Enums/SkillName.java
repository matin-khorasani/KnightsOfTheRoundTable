package Model.Enums;

public enum SkillName {
    SHIELD_BASH(3),
    FORTIFY(5),
    STRIKE_COMMAND(2),
    ARMOR_BREAK(4),
    RALLY(2),
    SLASH(2),
    HEAVY_STRIKE(4),
    LIFE_STEAL(3),
    BERSERK(3),
    FIREBALL(2),
    LIGHTNING_STRIKE(4),
    ICE_BLAST(3),
    ARCANE_SURGE(2),
    SILENCE(4),
    HEAL(2),
    GROUP_HEAL(5),
    REVIVE(4),
    CLEANSE(3);
    private final int apCost;
    SkillName(int apCost) {
        this.apCost = apCost;
    }

    public int getApCost() {
        return apCost;
    }
}
