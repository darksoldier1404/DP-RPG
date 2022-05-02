package com.darksoldier1404.dpr.illustrate;

import com.darksoldier1404.dpr.enums.IllustrateType;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class Illustrate {
    private String mob;
    private List<IllustrateType> types = new ArrayList<>();

    private boolean recivedPrize;

    public Illustrate(String mob) {
        this.mob = mob;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public List<IllustrateType> getSealType() {
        return types;
    }

    public void setSealType(List<IllustrateType> illustrateType) {
        this.types = illustrateType;
    }

    public boolean hasSeal(IllustrateType illustrateType) {
        return types.contains(illustrateType);
    }

    public boolean addSeal(IllustrateType illustrateType) {
        if(!hasSeal(illustrateType)) {
            this.types.add(illustrateType);
            return true;
        } else {
            return false;
        }
    }

    public boolean isRecivedPrize() {
        return recivedPrize;
    }

    public void setRecivedPrize(boolean recivedPrize) {
        this.recivedPrize = recivedPrize;
    }
}
