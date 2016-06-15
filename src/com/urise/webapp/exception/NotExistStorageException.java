package com.urise.webapp.exception;

/**
 * Created by Vladimir on 13.06.2016.
 */
public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + " not exist", uuid);
    }
}
