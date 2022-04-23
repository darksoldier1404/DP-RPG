package com.darksoldier1404.dpr.illustrate;

import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.enums.IllustrateType;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class Illustrate {
    private String mob;
    private IllustrateType[] illustrateType = new IllustrateType[8];

    public Illustrate(String mob) {
        this.mob = mob;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public IllustrateType[] getSealType() {
        return illustrateType;
    }

    public void setSealType(IllustrateType[] illustrateType) {
        this.illustrateType = illustrateType;
    }

    public boolean hasSeal(IllustrateType illustrateType) {
        for (IllustrateType st : this.illustrateType) {
            if (st.equals(illustrateType)) {
                return true;
            }
        }
        return false;
    }

    public void addSeal(IllustrateType illustrateType, Player p) {
        switch (illustrateType) {
            case ONE: {
                if(!hasSeal(IllustrateType.ONE)) {
                    this.illustrateType[0] = illustrateType;
                }else{
                    p.sendMessage(DRPG.data.getPrefix() + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case TWO: {
                if(!hasSeal(IllustrateType.TWO)) {
                    this.illustrateType[1] = illustrateType;
                }else{
                    p.sendMessage(DRPG.data.getPrefix() + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case THREE: {
                if(!hasSeal(IllustrateType.THREE)) {
                    this.illustrateType[2] = illustrateType;
                } else {
                    p.sendMessage(DRPG.data.getPrefix() + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case FOUR: {
                if(!hasSeal(IllustrateType.FOUR)) {
                    this.illustrateType[3] = illustrateType;
                } else {
                    p.sendMessage(DRPG.data.getPrefix() + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case FIVE: {
                if(!hasSeal(IllustrateType.FIVE)) {
                    this.illustrateType[4] = illustrateType;
                } else {
                    p.sendMessage(DRPG.data.getPrefix() + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case SIX: {
                if(!hasSeal(IllustrateType.SIX)) {
                    this.illustrateType[5] = illustrateType;
                } else {
                    p.sendMessage(DRPG.data.getPrefix() + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case SEVEN: {
                if(!hasSeal(IllustrateType.SEVEN)) {
                    this.illustrateType[6] = illustrateType;
                } else {
                    p.sendMessage(DRPG.data.getPrefix() + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case EIGHT: {
                if(!hasSeal(IllustrateType.EIGHT)) {
                    this.illustrateType[7] = illustrateType;
                } else {
                    p.sendMessage(DRPG.data.getPrefix() + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case NINE: {
                if(!hasSeal(IllustrateType.NINE)) {
                    this.illustrateType[8] = illustrateType;
                } else {
                    p.sendMessage(DRPG.data.getPrefix() + "이미 존재하는 도감 조각 입니다.");
                }
            }
        }
    }
}
