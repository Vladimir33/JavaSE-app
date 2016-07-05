package com.urise.webapp.model;

import com.urise.webapp.storage.MapUuidStorage;
import com.urise.webapp.storage.Storage;

/**
 * Created by Vladimir on 02.07.2016.
 */
public class Person {

    private Resume resume;
    private static final Storage STORAGE = new MapUuidStorage();

    public Person(String fullName) {
        resume = new Resume(fullName);
        resume.fillSectionMap();
        STORAGE.save(resume);
    }


    public String getFullName() {
        return resume.getFullName();
    }

    public Section getSection(SectionType type) {
        return resume.getSection(type);
    }

    public String getUuid() {
        return resume.getUuid();
    }

    public Resume get() {
        return STORAGE.get(resume.getUuid());
    }

    public void save() {
        STORAGE.save(resume);
    }

    public void update() {
        STORAGE.update(resume);
    }

    public void delete() {
        STORAGE.delete(resume.getUuid());
    }
}
