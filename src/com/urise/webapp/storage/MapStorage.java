package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;


/**
 * Created by Vladimir on 17.06.2016.
 */
public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected String getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            if (entry.getValue().getUuid().equals(uuid)) {
                return entry.getValue().getFullName();
            }
        }
        return null;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        map.put(searchKey.toString(), r);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        map.put(r.getFullName(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get(searchKey.toString());
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(searchKey.toString());
    }

    @Override
    protected List<Resume> sortedList() {
        List list = new ArrayList<>(map.values());
        Collections.sort(list, RESUME_COMPARATOR);
        return list;
    }

    @Override
    public int size() {
        return map.size();
    }
}
