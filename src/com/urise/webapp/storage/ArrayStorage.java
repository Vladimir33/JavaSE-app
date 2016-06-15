package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

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

    protected int getIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
