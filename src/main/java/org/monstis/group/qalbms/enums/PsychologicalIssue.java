package org.monstis.group.qalbms.enums;



public enum PsychologicalIssue {
    ANXIETY("Anxiety disorders including general anxiety, panic attacks", 
            "Тревожные расстройства, включая общее беспокойство, панические атаки", 
            "Tashvish buzilishlari, umumiy tashvish, panik hujumlarni o'z ichiga oladi"),
    DEPRESSION("Depression, including major depressive disorder", 
               "Депрессия, включая большое депрессивное расстройство", 
               "Depressiya, shu jumladan katta depressiv buzilish"),
    PTSD("Post-Traumatic Stress Disorder", 
         "Посттравматическое стрессовое расстройство", 
         "Posttravmatik stress buzilishi"),
    OCD("Obsessive-Compulsive Disorder", 
        "Обсессивно-компульсивное расстройство", 
        "Obsessiv-kompulsiv buzilish"),
    ADDICTION("Addiction disorders, including substance abuse", 
              "Зависимости, включая злоупотребление веществами", 
              "Giyohvandlik buzilishlari, moddalarni suiiste'mol qilishni o'z ichiga oladi"),
    EATING_DISORDERS("Eating disorders like anorexia, bulimia", 
                     "Расстройства пищевого поведения, такие как анорексия, булемия", 
                     "Oziqlanish buzilishlari, anoreksiya, bulimiya kabi"),
    PHOBIAS("Phobias, including social, specific, and agoraphobia", 
            "Фобии, включая социальную фобию, специфические фобии и агорафобию", 
            "Fobiyalar, ijtimoiy, maxsus va agorafobiya kabilar"),
    BIPOLAR_DISORDER("Bipolar disorder, including manic and depressive episodes", 
                     "Биполярное расстройство, включая манические и депрессивные эпизоды", 
                     "Bipolyar buzilish, manik va depressiv epizodlarni o'z ichiga oladi"),
    STRESS("Chronic stress and stress-related disorders", 
           "Хронический стресс и расстройства, связанные со стрессом", 
           "Xronik stress va stressga oid buzilishlar"),
    BORDERLINE_PERSONALITY("Borderline Personality Disorder", 
                           "Граничное расстройство личности", 
                           "Chegaraviy shaxsiyat buzilishi"),
    SCHIZOPHRENIA("Schizophrenia and related psychotic disorders", 
                 "Шизофрения и связанные с ней психотические расстройства", 
                 "Shizofreniya va unga aloqador psixotik buzilishlar"),
    ANGER_MANAGEMENT("Anger management and emotional regulation", 
                     "Управление гневом и эмоциональная регуляция", 
                     "G'azabni boshqarish va emotsionalni tartibga solish"),
    RELATIONSHIP_ISSUES("Issues with interpersonal relationships", 
                        "Проблемы в межличностных отношениях", 
                        "Shaxslararo munosabatlar bilan bog'liq muammolar"),
    SELF_ESTEM("Low self-esteem and confidence issues", 
               "Низкая самооценка и проблемы с уверенностью", 
               "O'zini past baholash va ishonch muammolari"),
    GRIEF("Grief and loss, including bereavement", 
          "Горе и утрата, включая горе от потери близких", 
          "Qayg'u va yo'qotish, shu jumladan yaqinlarini yo'qotish"),
    WORKPLACE_ISSUES("Workplace stress, burnout, and career counseling", 
                    "Стресс на рабочем месте, выгорание и карьерное консультирование", 
                    "Ish joyidagi stress, tugatish va karerani maslahatlash"),
    SEXUAL_ABUSE("Psychological issues stemming from sexual abuse trauma", 
                 "Психологические проблемы, возникающие из-за травмы сексуального насилия", 
                 "Jinsiy zo'ravonlik travmasidan kelib chiqqan psixologik muammolar"),
    GENDER_IDENTITY("Gender identity issues and transgender counseling", 
                    "Проблемы с гендерной идентичностью и консультации для трансгендерных людей", 
                    "Jinsiy identifikatsiya muammolari va transgenderlar uchun maslahatlashuv"),
    FAMILY_ISSUES("Family dynamics and parenting issues", 
                  "Семейные проблемы и вопросы воспитания", 
                  "Oila dinamikasi va ota-ona muammolari"),
    SOCIAL_ANXIETY("Social Anxiety Disorder", 
                   "Социальное тревожное расстройство", 
                   "Ijtimoiy tashvish buzilishi"),
    ADD("Attention Deficit Disorder, including ADHD", 
        "Расстройства внимания, включая СДВГ", 
        "Diqqat yetishmovchiligi buzilishi, shu jumladan ADHD"),
    SLEEP_DISORDERS("Sleep issues, insomnia, and related disorders", 
                    "Расстройства сна, бессонница и связанные с ними заболевания", 
                    "Uyqusizlik va unga oid buzilishlar");

    private final String descriptionEn;
    private final String descriptionRu;
    private final String descriptionUz;

    PsychologicalIssue(String descriptionEn, String descriptionRu, String descriptionUz) {
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
            default:
                return descriptionEn;
        }
    }
}
