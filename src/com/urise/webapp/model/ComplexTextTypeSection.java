package com.urise.webapp.model;

import java.util.Collections;
import java.util.List;

import static com.urise.webapp.model.SectionType.ACHIEVEMENT;
import static com.urise.webapp.model.SectionType.QUALIFICATIONS;

/**
 * Created by Vladimir on 07.07.2016.
 */
public class ComplexTextTypeSection extends Section{

    StringBuilder sb = new StringBuilder();

    public ComplexTextTypeSection(SectionType type, List<String> list) {
        super(type);
        sb.append(type.getTitle()).append(":\n");
        for (String str : list) {
            sb.append(str);
        }

        if (type == ACHIEVEMENT) {
            this.achieveList = list;
            this.qualList = Collections.emptyList();
            this.type = type;
        } else if (type == QUALIFICATIONS) {
            this.achieveList = Collections.emptyList();
            this.qualList = list;
            this.type = type;
        }
    }

    private SectionType type;
    private List<String> achieveList;
    private List<String> qualList;

    public SectionType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexTextTypeSection that = (ComplexTextTypeSection) o;

        if (type != that.type) return false;
        if (achieveList != null ? !achieveList.equals(that.achieveList) : that.achieveList != null) return false;
        return qualList != null ? qualList.equals(that.qualList) : that.qualList == null;

    }

    @Override
    public int hashCode() {
        int result = achieveList != null ? achieveList.hashCode() : 0;
        result = 31 * result + (qualList != null ? qualList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
