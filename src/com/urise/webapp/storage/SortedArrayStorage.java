package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by Vladimir on 01.06.2016.
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = resumeCount - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }

    @Override
    protected void insertElement(Resume r, int index) {
        // http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        int insertIdx = -index - 1;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, resumeCount - insertIdx);
        storage[insertIdx] = r;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, resumeCount, searchKey);
    }

    @Override
    protected List<Resume> sortedList() {
        List<Resume> list = Arrays.asList((Arrays.copyOfRange(storage, 0, resumeCount)));
        Collections.sort(list, RESUME_COMPARATOR);
        return list;
    }
}
