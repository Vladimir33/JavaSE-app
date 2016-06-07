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
        if (!ifResumeExistStorageOverflow(r.getUuid())) {
            storage[resumeCount] = r;
            resumeCount++;
            Arrays.sort(storage, 0, resumeCount, null);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
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
