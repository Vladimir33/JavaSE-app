package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    // Number of Resume;
    private int resumeCount = 0;

    public void clear() {

        for (int i = 0; i < resumeCount; i++)
            storage[i] = null;
        // shrink storageNoNull size to O;
        resumeCount = 0;
    }

    public int getResumeIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void update(Resume r) {
        // TODO check resume present
        int i = getResumeIndex(r.getUuid());
        if (i != -1) {
            storage[i] = r;
        } else {
            System.out.println("ERROR");
        }
    }

    public void save(Resume r) {
        // TODO check resume not present / for overflow
        if (getResumeIndex(r.getUuid()) == -1 && resumeCount != storage.length) {
            storage[resumeCount] = r;
            resumeCount++;
        } else {
            System.out.println("ERROR");
        }
    }

    public Resume get(String uuid) {
        // TODO check resume present
        int i = getResumeIndex(uuid);
        if (i == -1) {
            System.out.println("ERROR");
            return null;
        } else {
            return storage[i];
        }
    }

    public void delete(String uuid) {
        // TODO check resume present
        int i = getResumeIndex(uuid);
        if (i == -1) {
            System.out.println("ERROR");
        } else {
            storage[i] = storage[resumeCount - 1];
            storage[resumeCount - 1] = null;
            resumeCount--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {

        Resume[] storageNoNull = new Resume[resumeCount];

        for (int i = 0; i < resumeCount; i++) {
            storageNoNull[i] = storage[i];
        }
        return storageNoNull;
    }

    public int size() {

        return resumeCount;
    }
}
