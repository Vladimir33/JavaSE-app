package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int resumeCount = 0;

    public void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }
    public int size() {
        return resumeCount;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
    //    Arrays.sort(storage, 0, resumeCount, null);
        return Arrays.copyOfRange(storage, 0, resumeCount);
    }

    protected abstract int getIndex(String uuid);
}
