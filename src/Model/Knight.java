package Model;

import Model.Enums.KnightClass;
import Model.Enums.KnightName;
import Model.Enums.SkillName;

public class Knight {
    private final KnightName knightName;
    private int hp;
    private int ap;
    private boolean isStunned;
    private boolean isAlive;
    private int damageDealt;
    private final int baseAttack;
    private final int baseMagicAttack;
    private final int baseDefense;
    private final int baseSpeed;
    private int attackPercent = 100;
    private int magicAttackPercent = 100;
    private int defensePercent = 100;
    private int speedPercent = 100;


    public Knight(KnightName knightName) {
        this.knightName = knightName;
        this.hp = knightName.getMaxHp();
        this.ap = 3;
        this.isStunned = false;
        this.isAlive = true;
        this.damageDealt = 0;
        this.baseAttack = knightName.getAttack();
        this.baseMagicAttack = knightName.getMagicAttack();
        this.baseDefense = knightName.getDefense();
        this.baseSpeed = knightName.getSpeed();

    }
    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, knightName.getMaxHp()));
        if (this.hp == 0) {
            this.isAlive = false;
        }
    }
    public void setAp(int ap, int turnPoint) {
        this.ap = Math.max(0, Math.min(ap + turnPoint, 5));
    }
    public KnightName getKnightName() {
        return knightName;
    }

    public int getHp() {
        return hp;
    }

    public int getAp() {
        return ap;
    }

    public boolean isStunned() {
        return isStunned;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setStunned(boolean stunned) {
        isStunned = stunned;
    }


    public int getDamageDealt() {
        return damageDealt;
    }
    public int getCurrentAttack() {
        return (baseAttack * attackPercent) / 100;
    }

    public int getCurrentMagicAttack() {
        return (baseMagicAttack * magicAttackPercent) / 100;
    }

    public int getCurrentDefense() {
        return (baseDefense * defensePercent) / 100;
    }

    public int getCurrentSpeed() {
        return (baseSpeed * speedPercent) / 100;
    }

    public int getAttackPercent() {
        return attackPercent;
    }

    public int getMagicAttackPercent() {
        return magicAttackPercent;
    }

    public int getDefensePercent() {
        return defensePercent;
    }

    public int getSpeedPercent() {
        return speedPercent;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }
    public void setAttackPercent(int percent) {
        this.attackPercent = percent;
    }

    public void setMagicAttackPercent(int percent) {
        this.magicAttackPercent = percent;
    }

    public void setDefensePercent(int percent) {
        this.defensePercent = percent;
    }

    public void setSpeedPercent(int percent) {
        this.speedPercent = percent;
    }

    public int calculateDamage(Knight target, SkillName skillName) {
        int damage;
        if (getKnightName().getKnightClass() == KnightClass.MAGE) {
            damage = getCurrentMagicAttack();
        }
        else if (skillName == SkillName.HEAVY_STRIKE) {
            damage = (int) Math.floor(1.5 * getCurrentAttack()) - (int) Math.floor(0.3 * target.getCurrentDefense());
        }
        else {
            damage = getCurrentAttack() - (int) Math.floor(0.3 * target.getCurrentDefense());
        }
        return damage;
    }
    public void applyDamage(int damage, Knight attacker) {
        this.setHp(this.getHp() - damage);
        attacker.setDamageDealt(attacker.getDamageDealt() + damage);
    }
    public boolean dodgeStatus(Knight attacker) {
        KnightClass attackerClass = attacker.getKnightName().getKnightClass();
        if (attackerClass == KnightClass.COMMANDER || attackerClass == KnightClass.WARRIOR) {
            return attacker.getCurrentAttack() < this.getCurrentSpeed();
        } else if (attackerClass == KnightClass.MAGE || attackerClass == KnightClass.HEALER) {
            return attacker.getCurrentMagicAttack() < this.getCurrentSpeed();
        }
        return false;
    }
    public void resetToBaseStats() {
        this.attackPercent = 100;
        this.magicAttackPercent = 100;
        this.defensePercent = 100;
        this.speedPercent = 100;
    }
    public int applyHeal(int percent, boolean isRevive) {
        int maxHp = knightName.getMaxHp();
        int currentHp = this.hp;
        int healAmount = (maxHp * Math.abs(percent)) / 100;
        if (isRevive) {
            if (this.isAlive) {
                return -1;
            }
            this.hp = Math.min(healAmount, maxHp);
            this.ap = 3;
            this.isAlive = true;
            return healAmount;
        }
        if (percent < 0) {
            int newHp = Math.max(0, currentHp - healAmount);
            this.setHp(newHp);
            return -healAmount;
        }
        int newHp = Math.min(maxHp, currentHp + healAmount);
        this.setHp(newHp);
        return healAmount;
    }
}
