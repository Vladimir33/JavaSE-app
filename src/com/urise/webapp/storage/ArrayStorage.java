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

    public void update(Resume r) {
        // TODO check resume present
        boolean resumePresent = false;
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].equals(r)) {
                resumePresent = true;
                storage[i] = new Resume();
            }
        }
        if (!resumePresent) {
            System.out.println("ERROR");
        }
    }

    public void save(Resume r) {
        // TODO check resume not present
        boolean resumePresent = false;
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].equals(r)) {
                resumePresent = true;
            }
        }
        // TODO check for overflow
        boolean ifOverflow = false;
        if (resumeCount == storage.length) {
            ifOverflow = true;
        }
        if (!resumePresent && !ifOverflow) {
            storage[resumeCount] = r;
            resumeCount++;
        } else {
            System.out.println("ERROR");
        }
    }

    public Resume get(String uuid) {
        // TODO check resume present
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("ERROR");
        return null;
    }

    public void delete(String uuid) {
        // TODO check resume present
        boolean resumePresent = false;
        for (int i = 0; i < resumeCount; i++) {
            if (uuid == storage[i].getUuid()) {
                resumePresent = true;
                storage[i] = storage[resumeCount - 1];
                storage[resumeCount - 1] = null;
                resumeCount--;
            }
        }
        if (!resumePresent) {
            System.out.println("ERROR");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {

        Resume[] storageNoNull = new Resume[resumeCount];

        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid() != null) {
                storageNoNull[i] = storage[i];
            }
        }
        return storageNoNull;
    }

    public int size() {

        return resumeCount;
    }
}
