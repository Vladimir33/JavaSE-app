/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    // Number of Resume;
    int resumeCount = 0;

    void clear() {

        for (int j = 0; j < storage.length; j++)
            storage[j] = null;
        // shrink storageNoNull size to O;
        resumeCount = 0;
    }

    void save(Resume r) {

        storage[resumeCount] = r;
        // Resume size;
        resumeCount++;
    }

    Resume get(String uuid) {

        for (int j = 0; j < resumeCount; j++) {
            if (storage[j].uuid.equals(uuid)) {
                return storage[j];
            }
        }
        return null;
    }

    void delete(String uuid) {

        int m = 0;
        while ((storage[m].uuid != uuid) && (m < resumeCount)) {

            m++;
        }
        // copy / shift
        System.arraycopy(storage, m + 1, storage, m, storage.length - m - 1);

        resumeCount--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        Resume[] storageNoNull = new Resume[resumeCount];
        int k = 0;
        for (int j = 0; j < resumeCount; j++) {
            if (storage[j].uuid != null) {
                storageNoNull[k] = storage[j];
                k++;
            }
        }
        return storageNoNull;
    }

    int size() {
        return resumeCount;
    }
}
