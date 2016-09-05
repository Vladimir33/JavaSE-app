package com.urise.webapp.util;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.serializer.DataStreamSerializer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vladimir on 04.09.2016.
 */
public class DataSectionAdapter extends DataStreamSerializer {


    public static boolean isTextSection(Map.Entry<SectionType, Section> entry) {
        if (entry.getValue().getClass() == TextSection.class) {
            return true;
        }
        return false;
    }

    public static boolean isListSection(Map.Entry<SectionType, Section> entry) {
        if (entry.getValue().getClass() == ListSection.class) {
            return true;
        }
        return false;
    }

    public static boolean isOrganizationSection(Map.Entry<SectionType, Section> entry) {
        if (entry.getValue().getClass() == OrganizationSection.class) {
            return true;
        }
        return false;
    }

    public static boolean isPersonalOrObjective(SectionType sectionType){
        if (sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE) {
            return true;
        }
        return false;
    }

    public static boolean isAchievementOrQualifications(SectionType sectionType){
        if (sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS) {
            return true;
        }
        return false;
    }

    public static boolean isEducationOrExperience(SectionType sectionType){
        if (sectionType == SectionType.EDUCATION || sectionType == SectionType.EXPERIENCE){
            return true;
        }
        return false;
    }

    public static void writeTextSection(Map.Entry<SectionType, Section> entry, DataOutputStream dos) throws IOException {
        dos.writeUTF(entry.getKey().name());
        dos.writeUTF(entry.getValue().toString());
    }

    public static void writeListSection(Map.Entry<SectionType, Section> entry, DataOutputStream dos) throws IOException {
        dos.writeUTF(entry.getKey().name());
        ListSection listSection = (ListSection) entry.getValue();
        List<String> items = listSection.getItems();
        dos.writeInt(items.size());
        for (String content : items) {
            dos.writeUTF(content);
        }
    }

    public static void writeOrganizationSection(Map.Entry<SectionType, Section> entry, DataOutputStream dos) throws IOException {

        dos.writeUTF(entry.getKey().name());
        OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
        List<Organization> organizations = organizationSection.getOrganizations();

        dos.writeInt(organizations.size());

        for (Organization org : organizations) {
            dos.writeUTF(org.getHomePage().getName());
            dos.writeUTF(org.getHomePage().getUrl());
            dos.writeInt(org.getPositions().size());
            for (Organization.Position pos : org.getPositions()) {
                dos.writeUTF(pos.getStartDate().toString());
                dos.writeUTF(pos.getEndDate().toString());
                dos.writeUTF(pos.getTitle());
                dos.writeUTF(pos.getDescription());
            }
        }
    }

    public static List<String> readListSection(DataInputStream dis) throws IOException {

        int size = dis.readInt();
        List<String> items = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            items.add(dis.readUTF());
        }
        return items;
    }

    private static Link readOrganizationSectionLink(DataInputStream dis) throws IOException {

        String name = dis.readUTF();
        String url = dis.readUTF();

        return new Link(name, url);
    }

    private static List<Organization.Position> readOrganizationSectionPosition(DataInputStream dis) throws IOException {

        int size = dis.readInt();
        List<Organization.Position> positions = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            LocalDate startDate = LocalDate.parse(dis.readUTF());
            LocalDate endDate = LocalDate.parse(dis.readUTF());
            String title = dis.readUTF();
            String description = dis.readUTF();
            positions.add(new Organization.Position(startDate, endDate, title, description));
        }
        return positions;
    }

    public static List<Organization> createOrganizationList(DataInputStream dis) throws IOException {

        int size = dis.readInt();
        List<Organization> organizations = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            organizations.add(new Organization(readOrganizationSectionLink(dis), readOrganizationSectionPosition(dis)));
        }
        return organizations;
    }
}
