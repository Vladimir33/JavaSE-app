package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Vladimir on 02.07.2016.
 */
public class MainResume {

    private static final String NAME_1 = "Bill";


    private static final Resume RESUME_1;
    private static String personal_text;
    private static String objective_text;
    private static TextTypeSection personalSection;
    private static TextTypeSection objectiveSection;
    private static List<String> achieveList;
    private static List<String> qualList;
    private static String achFirst_text;
    private static String achSecond_text;
    private static String qualFirst_text;
    private static String qualSecond_text;
    private static ComplexTextTypeSection achieveSection;
    private static ComplexTextTypeSection qualSection;

    static {
        RESUME_1 = new Resume(NAME_1);

        personal_text = "Аналитический склад ума, сильная логика, креативность, инициативность.";
        objective_text = "Ведущий преподаватель онлайн школы U-Rise по обучению Java SE.";
        personalSection = new TextTypeSection(SectionType.PERSONAL, personal_text);
        objectiveSection = new TextTypeSection(SectionType.OBJECTIVE, objective_text);
        personalSection.saveSection(RESUME_1, personalSection);
        objectiveSection.saveSection(RESUME_1, objectiveSection);

        achFirst_text = "Разработка проектов Практика Java, разработка Web приложения Java Enterprise";
        achSecond_text = "Реализация двухфакторной аутентификации для онлайн платформы\n" +
                "управления проектами Wrike.";

        achieveList = new ArrayList<>(asList(achFirst_text, achSecond_text));

        qualFirst_text = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2";
        qualSecond_text = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";

        qualList = new ArrayList<>(asList(qualFirst_text, qualSecond_text));

        achieveSection = new ComplexTextTypeSection(SectionType.ACHIEVEMENT, achieveList);
        achieveSection.saveSection(RESUME_1, achieveSection);

        qualSection = new ComplexTextTypeSection(SectionType.QUALIFICATIONS, qualList);
        qualSection.saveSection(RESUME_1, qualSection);
    }


    public static void main(String[] args) {

        System.out.println(RESUME_1.getSection(SectionType.PERSONAL));
        System.out.println(RESUME_1.getSection(SectionType.OBJECTIVE));
        System.out.println(RESUME_1.getSection(SectionType.ACHIEVEMENT));
        System.out.println(RESUME_1.getSection(SectionType.QUALIFICATIONS));
    }
}
