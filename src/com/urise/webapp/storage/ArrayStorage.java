package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("ERROR");
        }
    }

    public void save(Resume r) {
        if (resumeCount == STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else if (!ifResumeExist(r.getUuid())) {
            storage[resumeCount] = r;
            resumeCount++;
        }
    }

    public void delete(String uuid) {
        if(!ifResumeNotExist(uuid)){
            storage[getIndex(uuid)] = storage[resumeCount - 1];
            storage[resumeCount - 1] = null;
            resumeCount--;
        }
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
