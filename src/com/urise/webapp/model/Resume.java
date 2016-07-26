package com.urise.webapp.model;

import java.util.*;

/**
 * Resume class
 */

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public void setSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void setContact(Map.Entry me) {
        contacts.put((ContactType) me.getKey(), (String) me.getValue());
    }

    public Resume(String fullName) {
        this.fullName = fullName;
        this.uuid = UUID.randomUUID().toString();
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume r) {
        int cmp = fullName.compareTo(r.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(r.uuid);
    }
}
