import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;

/**
 *
 * Test for com.urise.webapp.storage.ArrayStorage
 */

public class MainArray {

    static ArrayStorage arrayStorage = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume r4 = new Resume();
        r4.setUuid("uuid4");
        Resume r5 = new Resume();
        r5.setUuid("uuid5");


        arrayStorage.save(r1);
        arrayStorage.save(r2);
        arrayStorage.save(r3);
        arrayStorage.save(r4);
        arrayStorage.save(r5);


        System.out.println("Get r1: " + arrayStorage.get(r1.getUuid()));
        System.out.println("Size: " + arrayStorage.size());

        printAll();
       // arrayStorage.delete(r1.uuid);
       // arrayStorage.delete(r2.uuid);
        arrayStorage.delete(r3.getUuid());
           arrayStorage.delete(r4.getUuid());
        //   arrayStorage.delete(r5.uuid);

        printAll();
        arrayStorage.clear();
        printAll();

        System.out.println("Size: " + arrayStorage.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : arrayStorage.getAll()) {
            System.out.println(r);
        }
    }
}
