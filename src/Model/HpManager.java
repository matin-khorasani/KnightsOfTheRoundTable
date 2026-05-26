package Model;

import Model.Enums.KnightClass;

public class HpManager {
    public boolean dodgeStatus(Knight attacker, Knight defender) {
        KnightClass attackerClass = attacker.getKnightName().getKnightClass();
        if (attackerClass == KnightClass.COMMANDER || attackerClass == KnightClass.WARRIOR) {
            return attacker.getCurrentAttack() < defender.getCurrentSpeed();
        } else if (attackerClass == KnightClass.MAGE || attackerClass == KnightClass.HEALER) {
            return attacker.getCurrentMagicAttack() < defender.getCurrentSpeed();
        }
        return false;
    }

}
