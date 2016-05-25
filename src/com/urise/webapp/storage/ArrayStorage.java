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

        for (int j = 0; j < storage.length; j++)
            storage[j] = null;
        // shrink storageNoNull size to O;
        resumeCount = 0;
    }

    public void update(Resume r) {
        // TODO check resume present

        int i = 0;
        while ((!storage[i].equals(r)) && (i < resumeCount)) {
            i++;
        }
        if(i < resumeCount){
            storage[i] = new Resume();
        }else{
            System.out.println("ERROR");
        }
    }

    public void save(Resume r) {

        // TODO check resume not present
        int i = 0;
        while ((!storage[i].equals(r)) && (i < resumeCount)) {
            i++;
        }
        // TODO check for overflow / check resume not present
        if (resumeCount < storage.length && i == resumeCount) {
            storage[resumeCount] = r;
            // Resume size;
            resumeCount++;
        }else{
            System.out.println("ERROR");
        }
    }

    public Resume get(String uuid) {
        // TODO check resume present
        int i = 0;
        while ((!storage[i].getUuid().equals(uuid)) && (i < resumeCount)) {
            i++;
        }
        if (i == resumeCount) {
            System.out.println("ERROR");
        }else {

            for (int j = 0; j < resumeCount; j++) {
                if (storage[j].getUuid().equals(uuid)) {
                    return storage[j];
                }
            }
        }
            return null;
    }

    public void delete(String uuid) {
        // TODO check resume present
        int i = 0;
        while ((!storage[i].getUuid().equals(uuid)) && (i < resumeCount)) {
            i++;
        }
        if (i == resumeCount) {
            System.out.println("ERROR");
        }else {

            int m = 0;
            while ((storage[m].getUuid() != uuid) && (m < resumeCount)) {
                m++;
            }
            // copy / shift
            System.arraycopy(storage, m + 1, storage, m, storage.length - m - 1);

            resumeCount--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {

        Resume[] storageNoNull = new Resume[resumeCount];
        int k = 0;
        for (int j = 0; j < resumeCount; j++) {
            if (storage[j].getUuid() != null) {
                storageNoNull[k] = storage[j];
                k++;
            }
        }
        return storageNoNull;
    }

    public int size() {

        return resumeCount;
    }
}
