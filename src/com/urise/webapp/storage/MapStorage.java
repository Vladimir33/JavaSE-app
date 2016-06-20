package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Iterator;
import java.util.Map;


/**
 * Created by Vladimir on 17.06.2016.
 */
public class MapStorage extends AbstractStorage {

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void update(Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    public void save(Resume r) {
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            if (entry.equals(r)) {
                throw new ExistStorageException(r.getUuid());
            }
        }
        map.put(r.getUuid(), r);
    }

    @Override
    public void delete(String uuid) {
        Iterator<Map.Entry<String, Resume>> iterator = map.entrySet().iterator();
        boolean ifFound = false;
        while (iterator.hasNext()) {
            Map.Entry<String, Resume> entry = iterator.next();
            if (uuid.equals(entry.getKey())) {
                ifFound = true;
                iterator.remove();
            }
        }
        if (!ifFound) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            if (uuid.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[map.size()]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
