package com.darksoldier1404.dpr.seal;

import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.enums.SealType;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class Seal {
    private String mob;
    private SealType[] sealType = new SealType[8];

    public Seal(String mob) {
        this.mob = mob;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public SealType[] getSealType() {
        return sealType;
    }

    public void setSealType(SealType[] sealType) {
        this.sealType = sealType;
    }

    public boolean hasSeal(SealType sealType) {
        for (SealType st : this.sealType) {
            if (st.equals(sealType)) {
                return true;
            }
        }
        return false;
    }

    public void addSeal(SealType sealType, Player p) {
        switch (sealType) {
            case ONE: {
                if(!hasSeal(SealType.ONE)) {
                    this.sealType[0] = sealType;
                }else{
                    p.sendMessage(DRPG.getInstance().prefix + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case TWO: {
                if(!hasSeal(SealType.TWO)) {
                    this.sealType[1] = sealType;
                }else{
                    p.sendMessage(DRPG.getInstance().prefix + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case THREE: {
                if(!hasSeal(SealType.THREE)) {
                    this.sealType[2] = sealType;
                } else {
                    p.sendMessage(DRPG.getInstance().prefix + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case FOUR: {
                if(!hasSeal(SealType.FOUR)) {
                    this.sealType[3] = sealType;
                } else {
                    p.sendMessage(DRPG.getInstance().prefix + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case FIVE: {
                if(!hasSeal(SealType.FIVE)) {
                    this.sealType[4] = sealType;
                } else {
                    p.sendMessage(DRPG.getInstance().prefix + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case SIX: {
                if(!hasSeal(SealType.SIX)) {
                    this.sealType[5] = sealType;
                } else {
                    p.sendMessage(DRPG.getInstance().prefix + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case SEVEN: {
                if(!hasSeal(SealType.SEVEN)) {
                    this.sealType[6] = sealType;
                } else {
                    p.sendMessage(DRPG.getInstance().prefix + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case EIGHT: {
                if(!hasSeal(SealType.EIGHT)) {
                    this.sealType[7] = sealType;
                } else {
                    p.sendMessage(DRPG.getInstance().prefix + "이미 존재하는 도감 조각 입니다.");
                    return;
                }
            }
            case NINE: {
                if(!hasSeal(SealType.NINE)) {
                    this.sealType[8] = sealType;
                } else {
                    p.sendMessage(DRPG.getInstance().prefix + "이미 존재하는 도감 조각 입니다.");
                }
            }
        }
    }
}
