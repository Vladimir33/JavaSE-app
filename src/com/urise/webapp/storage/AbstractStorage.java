package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vladimir on 17.06.2016.
 */
public abstract class AbstractStorage implements Storage{
   protected Collection<Resume> collection = new ArrayList<>();
   protected Map<String, Resume> map = new HashMap<>();

    public abstract void clear();

    public abstract void update(Resume r);

    public abstract void save(Resume r);

    public abstract void delete(String uuid);

    public abstract Resume get(String uuid);

    public abstract Resume[] getAll();

    public abstract int size();
}
