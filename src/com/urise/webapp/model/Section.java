package com.urise.webapp.model;

import static com.urise.webapp.MainPerson.objective_text;
import static com.urise.webapp.MainPerson.personal_text;

/**
 * Created by Vladimir on 01.07.2016.
 */
public abstract class Section {
    public Section(SectionType type) {
        this.type = type;
    }

    private SectionType type;

    public String resumeMapSwitch(SectionType type) {

        switch (type) {
            case PERSONAL:
                return "\n" + personal_text;
            case OBJECTIVE:
                return "\n" + objective_text;
            case ACHIEVEMENT:
                return "\nAchTest";
            default:
                return null;
        }
    }

    @Override
    public String toString() {

        return resumeMapSwitch(type);
    }
}
