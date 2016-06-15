package com.urise.webapp.storage;

import java.lang.reflect.Field;

/**
 * Created by Vladimir on 13.06.2016.
 */
public class ArrayStorageTest extends AbstractArrayStorageTest{

    public ArrayStorageTest() throws NoSuchFieldException, IllegalAccessException {
        super(new ArrayStorage());
    }
}