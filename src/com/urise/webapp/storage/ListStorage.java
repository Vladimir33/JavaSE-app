package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




/**
 * Created by Vladimir on 17.06.2016.
 */
public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> list = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index != null;
    }

    @Override
    protected void doUpdate(Resume r, Integer index) {
        list.set(index, r);
    }

    @Override
    protected void doSave(Resume r, Integer index) {
        list.add(r);
    }

    @Override
    protected Resume doGet(Integer index) {
        return list.get(index);
    }

    @Override
    protected void doDelete(Integer index) {
        list.remove(index.intValue());
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<Resume> copyAll() {
        return new ArrayList<>(list);
    }

    @Override
    public int size() {
        return list.size();
    }
}