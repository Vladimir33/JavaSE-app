package com.urise.webapp;

import com.urise.webapp.model.*;

/**
 * Created by Vladimir on 02.07.2016.
 */
public class MainPerson {

    private static final String NAME_1 = "Bill";
    public static final String personal_text;
    public static final String objective_text;

    private static final Person RESUME_1;

    static {
        RESUME_1 = new Person(NAME_1);

        personal_text = "Аналитический склад ума, сильная логика, креативность, инициативность.";
        objective_text = "Ведущий преподаватель онлайн школы U-Rise по обучению Java SE.";
    }

    public static void main(String[] args) {

        System.out.println(RESUME_1.getFullName());
        System.out.println(RESUME_1.getSection(SectionType.PERSONAL));
        System.out.println(RESUME_1.getSection(SectionType.OBJECTIVE));
        System.out.println(RESUME_1.get());
    }
}
