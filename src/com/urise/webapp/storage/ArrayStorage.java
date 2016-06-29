package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void fillDeletedElement(int index) {
        storage[index] = storage[resumeCount - 1];
    }

    @Override
    protected void insertElement(Resume r, int index) {
        storage[resumeCount] = r;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected List<Resume> sortedList() {
        List<Resume> list = Arrays.asList((Arrays.copyOfRange(storage, 0, resumeCount)));
        Collections.sort(list, RESUME_COMPARATOR);
        return list;
    }
}
