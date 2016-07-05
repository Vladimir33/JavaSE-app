package com.urise.webapp;

import com.urise.webapp.model.Person;
import com.urise.webapp.model.Resume;

/**
 * Created by Vladimir on 15.06.2016.
 */
public class MainString {


    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");
    }


    public static void main(String[] args) {
        String[] strArray = new String[]{"1", "2", "3", "4", "5"};
//        String result = "";
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str).append(", ");
        }
        System.out.println(sb.toString());

        String str1 = "abc";
        String str3 = "c";
        String str2 = ("ab" + str3).intern();
        System.out.println(str1 == str2);





    }
}
