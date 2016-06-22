package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

/**
 * Created by Vladimir on 17.06.2016.
 */
public class ListStorage extends AbstractStorage {

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public void update(Resume r) {
        if (!collection.contains(r)) {
            throw new NotExistStorageException(r.getUuid());
        } else
            collection.add(r);
    }


    @Override
    public void save(Resume r) {
        if (collection.contains(r)) {
            throw new ExistStorageException(r.getUuid());
        } else
            collection.add(r);
    }

    @Override
    public void delete(String uuid) {
        Resume r = new Resume(uuid);
        if (!collection.contains(r)) {
            throw new NotExistStorageException(uuid);
        } else
            collection.remove(r);
    }

    @Override
    public Resume get(String uuid) {
        Resume r = new Resume(uuid);
        int index = collection.indexOf(r);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return collection.get(index);
    }

    @Override
    public Resume[] getAll() {
        return collection.toArray(new Resume[collection.size()]);
    }


    @Override
    public int size() {
        return collection.size();
    }
}
