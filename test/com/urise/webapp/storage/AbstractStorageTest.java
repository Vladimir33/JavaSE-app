package com.urise.webapp.storage;


import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;


import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;
import static com.urise.webapp.util.SectionUtil.*;
import static java.time.Month.JANUARY;
import static java.time.Month.OCTOBER;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vladimir on 13.06.2016.
 */
public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Grigory Kislin");

        createTextSection("Аналитический склад ума");
        saveTextSection(RESUME_1, PERSONAL);

        createTextSection("Ведущий преподаватель онлайн школы U-Rise");
        saveTextSection(RESUME_1, OBJECTIVE);

        createListSection(Arrays.asList("Разработка проектов \"Практика Java, разработка Web приложения\"",
                "Реализация двухфакторной аутентификации для онлайн платформы\n" +
                        "управления проектами Wrike. "));
        saveListSection(RESUME_1, ACHIEVEMENT);

        createListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce"));
        saveListSection(RESUME_1, QUALIFICATIONS);

        createOrganization("Wrike", "www.wrike.com", DateUtil.of(2014, OCTOBER),
                DateUtil.of(2016, JANUARY), "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike");
        saveOrganization(EXPERIENCE);
        saveOrganizationSection(RESUME_1, EXPERIENCE);

        createOrganization("СПНИУИТМО", "", DateUtil.of(1993, JANUARY),
                DateUtil.of(1996, JANUARY), "программист С, С++", "Аспирантура");
        saveOrganization(EDUCATION);
        createSameOrganization(DateUtil.of(1987, JANUARY), DateUtil.of(1993, JANUARY), "Инженер",
                "(программист Fortran, C)");
        saveOrganization(EDUCATION);
        saveOrganizationSection(RESUME_1, EDUCATION);

        createContact(MAIL, "java@u-rise.com");
        saveContact(RESUME_1);
        createContact(SKYPE, "grigory.kislin");
        saveContact(RESUME_1);
        createContact(GITHUB, "https://github.com/gkislin/");
        saveContact(RESUME_1);

        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1, "newName");
        storage.update(newResume);
        assertTrue(newResume == storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAll() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}