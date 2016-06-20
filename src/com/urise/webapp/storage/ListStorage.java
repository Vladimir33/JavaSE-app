package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Iterator;
import java.util.Objects;


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
        collection.add(r);
    }


    @Override
    public void save(Resume r) {
        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume resume = iterator.next();
            if (Objects.equals(r.getUuid(), resume.getUuid())) {
                throw new ExistStorageException(r.getUuid());
            }
        }
        collection.add(r);
    }

    @Override
    public void delete(String uuid) {
        Iterator<Resume> iterator = collection.iterator();
        boolean ifFound = false;
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r.getUuid(), uuid)) {
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
        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r.getUuid(), uuid)) {
                return r;
            }
        }
        throw new NotExistStorageException(uuid);
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
