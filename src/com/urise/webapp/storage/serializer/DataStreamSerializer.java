package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import java.io.*;
import java.util.*;
import static com.urise.webapp.util.DataSectionAdapter.*;

/**
 * Created by Vladimir on 24.08.2016.
 */
public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();

            dos.writeInt(sections.size());

            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {

                if (isTextSection(entry)) {
                    writeTextSection(entry, dos);
                } else if (isListSection(entry)) {
                    writeListSection(entry, dos);
                } else if (isOrganizationSection(entry)) {
                    writeOrganizationSection(entry, dos);
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {

        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                resume.addContact(contactType, value);
            }

            int mapSize = dis.readInt();

            for (int i = 0; i < mapSize; i++) {

                SectionType sectionType = SectionType.valueOf(dis.readUTF());

                if (isPersonalOrObjective(sectionType)) {
                    resume.addSection(sectionType, new TextSection(dis.readUTF()));

                } else if (isAchievementOrQualifications(sectionType)) {
                    resume.addSection(sectionType, new ListSection(readListSection(dis)));

                } else if (isEducationOrExperience(sectionType)) {
                    resume.addSection(sectionType, new OrganizationSection(createOrganizationList(dis)));
                }
            }
            return resume;
        }
    }
}
