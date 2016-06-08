package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


/**
 * Created by Vladimir on 01.06.2016.
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("ERROR");
        }
    }

    @Override
    public void save(Resume r) {
        if (resumeCount == STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else if (!ifResumeExist(r.getUuid())) {
            int index = Arrays.binarySearch(storage, 0, resumeCount, r);
            index = -index - 1;
            System.arraycopy(storage, index, storage, index + 1, storage.length - index - 1);
            storage[index] = r;
            resumeCount++;
        }
    }

    @Override
    public void delete(String uuid) {
        if (!ifResumeNotExist(uuid)) {
            int index = getIndex(uuid);
            System.arraycopy(storage, index + 1, storage, index, storage.length - index - 1);
            resumeCount--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, resumeCount, searchKey);
    }
}
