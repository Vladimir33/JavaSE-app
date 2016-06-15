package com.urise.webapp.exception;

/**
 * Created by Vladimir on 13.06.2016.
 */
public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }
}
