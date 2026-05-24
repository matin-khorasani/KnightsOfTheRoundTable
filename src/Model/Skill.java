package Model;

import Model.Enums.SkillName;
import Model.Enums.TargetScope;
import Model.Enums.TargetType;

public class Skill {
    private SkillName skillName;
    private int apCost;
    private TargetScope targetScope;
    private TargetType targetType;

    public Skill(SkillName skillName, int apCost, TargetScope targetScope, TargetType targetType) {
        this.skillName = skillName;
        this.apCost = apCost;
        this.targetScope = targetScope;
        this.targetType = targetType;
    }

    public SkillName getSkillName() {
        return skillName;
    }

    public int getApCost() {
        return apCost;
    }

    public TargetScope getTargetScope() {
        return targetScope;
    }

    public TargetType getTargetType() {
        return targetType;
    }
}
