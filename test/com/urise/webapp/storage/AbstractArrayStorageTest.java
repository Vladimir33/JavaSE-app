package com.urise.webapp.storage;


import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by Vladimir on 13.06.2016.
 */
public abstract class AbstractArrayStorageTest {


    private Storage storage;
    private Resume[] resumeStorage;
    private Resume[] testResumeStorage;

    public AbstractArrayStorageTest(Storage storage) throws NoSuchFieldException, IllegalAccessException {
        this.storage = storage;

        ArrayStorage arrayStorage = new ArrayStorage();
        Field fResumeStorage = arrayStorage.getClass().getSuperclass().getDeclaredField("storage");
        fResumeStorage.setAccessible(true);
        resumeStorage = (Resume[]) fResumeStorage.get(arrayStorage);
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        testResumeStorage = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        for (int i = 0; i < storage.size(); i++) {
            Assert.assertEquals(null, storage.get(testResumeStorage[i].toString()));
            Assert.assertEquals(0, storage.size());
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void update() throws Exception {
        storage.update(new Resume());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertArrayEquals(storage.getAll(), testResumeStorage);
    }

    @Test(expected = ExistStorageException.class)
    public void save() throws Exception {
        storage.save(new Resume(UUID_2));
    }

    @Test(expected = StorageException.class)
    public void overflowTest() throws Exception {
        for (int i = 0; i <= resumeStorage.length; i++) {
            storage.save(new Resume());
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.clear();
        storage.delete(UUID_2);
    }

    @Test
    public void get() throws Exception {
        Resume r = storage.get(UUID_1);
        Assert.assertEquals(r, new Resume(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("uuid4");
    }
}