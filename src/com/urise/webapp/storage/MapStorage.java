package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

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
        if (map.containsKey(r.getUuid())) {
            map.put(r.getUuid(), r);
        } else
            throw new NotExistStorageException(r.getUuid());
    }

    @Override
    public void save(Resume r) {
        if (!map.containsKey(r.getUuid())) {
            map.put(r.getUuid(), r);
        } else
            throw new ExistStorageException(r.getUuid());
    }

    @Override
    public void delete(String uuid) {
        if (map.containsKey(uuid)) {
            map.remove(uuid);
        } else
            throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume get(String uuid) {
        if (map.containsKey(uuid)) {
            return map.get(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
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
