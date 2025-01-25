package org.monstis.group.qalbms.enums;

public enum Costs {
    BASIC("150 000 "),
    MEDIUM("250 000"),
    PRO("450 000");

    public final String costsInSom;


    Costs( String costsInSom) {
        this.costsInSom = costsInSom;

    }
    public String getCostsINSom() {
        return costsInSom;
    }

}
