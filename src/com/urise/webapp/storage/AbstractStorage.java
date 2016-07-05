package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Vladimir on 17.06.2016.
 */
public abstract class AbstractStorage<SK> implements Storage {
    protected static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doUpdate(Resume r, SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doSave(Resume r, SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract List<Resume> copyAll();

    public void update(Resume r) {
        LOG.info("Update " + r);
        SK searchKey = getExistedSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        SK searchKey = getNotExistedSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            NotExistStorageException e = new NotExistStorageException(uuid);
            LOG.log(Level.WARNING, e.getMessage(), e);
            throw e;
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            ExistStorageException e = new ExistStorageException(uuid);
            LOG.log(Level.WARNING, e.getMessage(), e);
            throw e;
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = copyAll();
        Collections.sort(list);
        return list;
    }
}


