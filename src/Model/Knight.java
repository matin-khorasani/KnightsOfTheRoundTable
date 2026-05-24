package Model;

import Model.Enums.KnightName;

public class Knight {
    private final KnightName knightName;
    private int hp;
    private int ap;
    private int currentAttack;
    private int currentMagicAttack;
    private int currentDefense;
    private int currentSpeed;
    private boolean isStunned;
    private boolean isAlive;

    public Knight(KnightName knightName) {
        this.knightName = knightName;
        this.hp = knightName.getMaxHp();
        this.ap = 3;
        this.currentAttack = knightName.getAttack();
        this.currentDefense = knightName.getDefense();
        this.currentMagicAttack = knightName.getMagicAttack();
        this.currentSpeed = knightName.getSpeed();
        this.isStunned = false;
        this.isAlive = true;
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

    public int getCurrentAttack() {
        return currentAttack;
    }

    public int getCurrentMagicAttack() {
        return currentMagicAttack;
    }

    public int getCurrentDefense() {
        return currentDefense;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
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

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void setCurrentDefense(int currentDefense) {
        this.currentDefense = currentDefense;
    }

    public void setCurrentMagicAttack(int currentMagicAttack) {
        this.currentMagicAttack = currentMagicAttack;
    }

    public void setCurrentAttack(int currentAttack) {
        this.currentAttack = currentAttack;
    }

}
