package com.urise.webapp.model;


/**
 * Created by Vladimir on 01.07.2016.
 */
public abstract class Section {
    public Section(SectionType type) {
        this.type = type;
    }

    private SectionType type;

    public void saveSection(Resume resume, Section value) {

        resume.getAllSections().put(type, value);
    }

    public void updateSection(Resume resume, Section value) {
        SectionType type = value.getType();
        resume.getAllSections().put(type, value);
    }

    abstract SectionType getType();
}




