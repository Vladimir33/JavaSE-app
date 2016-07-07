package com.urise.webapp.model;

import static com.urise.webapp.model.SectionType.OBJECTIVE;
import static com.urise.webapp.model.SectionType.PERSONAL;

/**
 * Created by Vladimir on 06.07.2016.
 */
public class TextTypeSection extends Section {

    public TextTypeSection(SectionType type, String text) {
        super(type);
        if (type == PERSONAL) {
            this.personal_text = PERSONAL.getTitle() + ":\n" + text;
            this.objective_text = "";
            this.type = type;
        } else if (type == OBJECTIVE) {
            this.personal_text = "";
            this.objective_text = OBJECTIVE.getTitle() + ":\n" + text;
            this.type = type;
        }
    }

    private SectionType type;
    private String personal_text;
    private String objective_text;

    public SectionType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextTypeSection that = (TextTypeSection) o;

        if (!personal_text.equals(that.personal_text)) return false;
        return objective_text.equals(that.objective_text);

    }

    @Override
    public int hashCode() {
        int result = personal_text.hashCode();
        result = 31 * result + objective_text.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return personal_text + objective_text;
    }
}
