package org.monstis.group.qalbms.enums;

public enum Costs {
    BASIC("150 000 ","3","зарекомендовали себя наилучшим образом" ),
    MEDIUM("250 000","5", "Провели более 200 Сессий, и зарекомендовали себя наилучшим образом"),
    PRO("450 000","7", "Супервизоры и другое");

    public final String costsInSom;

    public String getExperience() {
        return experience;
    }

    public final String experience;

    public String getSubtitle() {
        return subtitle;
    }

    public final String subtitle;


    Costs(String costsInSom, String experience, String subtitle) {
        this.costsInSom = costsInSom;
        this.experience = experience;
        this.subtitle = subtitle;
    }
    public String getCostsINSom() {
        return costsInSom;
    }

    public static Costs getByName(String name) {
        for (Costs cost : Costs.values()) {
            if (cost.name().equalsIgnoreCase(name)) {
                return cost;
            }
        }
        throw new IllegalArgumentException("No cost enum found with name: " + name);
    }


}
