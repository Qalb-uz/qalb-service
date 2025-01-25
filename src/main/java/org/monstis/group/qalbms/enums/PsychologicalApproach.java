package org.monstis.group.qalbms.enums;

public enum PsychologicalApproach {
    COGNITIVE_BEHAVIORAL_THERAPY("Cognitive Behavioral Therapy", "Когнитивно-поведенческая терапия", "Когнитив-бихевиорал терапия"),
    PSYCHODYNAMIC_THERAPY("Psychodynamic Therapy", "Психодинамическая терапия", "Психодинамик терапия"),
    HUMANISTIC_THERAPY("Humanistic Therapy", "Гуманистическая терапия", "Гуманистик терапия"),
    EXISTENTIAL_THERAPY("Existential Therapy", "Экзистенциальная терапия", "Экзистенциал терапия"),
    SYSTEMIC_THERAPY("Systemic Therapy", "Системная терапия", "Системавий терапия"),
    MINDFULNESS_BASED_THERAPY("Mindfulness-Based Therapy", "Терапия, основанная на внимательности", "Диqqatga asoslangan terapiya");

    private final String descriptionEn;
    private final String descriptionRu;
    private final String descriptionUz;

    PsychologicalApproach(String descriptionEn, String descriptionRu, String descriptionUz) {
        this.descriptionEn = descriptionEn;
        this.descriptionRu = descriptionRu;
        this.descriptionUz = descriptionUz;
    }

    public String getDescription(String language) {
        switch (language.toLowerCase()) {
            case "ru":
                return descriptionRu;
            case "uz":
                return descriptionUz;
            case "en":
            default:
                return descriptionEn;
        }
    }

}
