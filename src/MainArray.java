/**
 *
 * Test for ArrayStorage
 */

public class MainArray {

    static ArrayStorage arrayStorage = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.uuid = "uuid1";
        Resume r2 = new Resume();
        r2.uuid = "uuid2";
        Resume r3 = new Resume();
        r3.uuid = "uuid3";
        Resume r4 = new Resume();
        r4.uuid = "uuid4";
        Resume r5 = new Resume();
        r5.uuid = "uuid5";


        arrayStorage.save(r1);
        arrayStorage.save(r2);
        arrayStorage.save(r3);
        arrayStorage.save(r4);
        arrayStorage.save(r5);


        System.out.println("Get r1: " + arrayStorage.get(r1.uuid));
        System.out.println("Size: " + arrayStorage.size());

        printAll();
       // arrayStorage.delete(r1.uuid);
      //  arrayStorage.delete(r2.uuid);
        arrayStorage.delete(r3.uuid);
           arrayStorage.delete(r4.uuid);
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
