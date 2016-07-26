package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vladimir on 24.07.2016.
 */
public class SectionUtil {

    private static TextSection textSection;
    private static ListSection listSection;
    private static Organization organization;
    private static List<Organization> organizations = new ArrayList<>();
    private static Map.Entry<ContactType, String> mapContact;

    public static TextSection createTextSection(String text) {
        return textSection = new TextSection(text);
    }

    public static void saveTextSection(Resume resume, SectionType type) {
        resume.setSection(type, textSection);
    }


    public static ListSection createListSection(List<String> items) {
        return listSection = new ListSection(items);
    }

    public static void saveListSection(Resume resume, SectionType type) {
        resume.setSection(type, listSection);
    }

    public static Organization createOrganization(String name, String url, LocalDate startDate,
                                                  LocalDate endDate, String title, String description) {
        return organization = new Organization(name, url, startDate, endDate, title, description);
    }

    public static Organization.SameOrganization createSameOrganization(LocalDate startDate, LocalDate endDate,
                                                                       String title, String description) {
        return organization.new SameOrganization(startDate, endDate, title, description);
    }

    public static void saveOrganization(){
        organizations.add(organization);
    }

    public static void saveOrganizationSection(Resume resume, SectionType type){
        resume.setSection(type, new OrganizationSection(organizations));
    }

    public static Map.Entry<ContactType, String> createContact(ContactType type, String contact) {

        return mapContact = new AbstractMap.SimpleEntry<>(type, contact);
    }

    public static void saveContact(Resume resume) {
        resume.setContact(mapContact);
    }
}
