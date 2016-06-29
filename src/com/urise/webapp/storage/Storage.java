package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;


public interface Storage {
    Comparator<Resume> RESUME_COMPARATOR =
            (o1, o2) -> o1.getFullName().compareTo(o2.getFullName());

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}
