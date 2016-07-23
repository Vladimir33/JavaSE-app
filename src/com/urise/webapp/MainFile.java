package com.urise.webapp;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by Vladimir on 18.07.2016.
 */
public class MainFile {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\Vladimir\\Desktop\\JavaSE-app\\.gitignore");
        InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        fis.close();

        File file = new File("C:\\Users\\Vladimir\\Desktop\\JavaSE-app\\.gitignore");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.canWrite());


        File dir = new File("C:\\Users\\Vladimir\\Desktop\\JavaSE-app\\src\\com\\urise\\webapp");

        getAllFiles(dir);
    }

    public static String getAllFiles(File dir) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                } else if (file.isDirectory())
                    getAllFiles(file);
            }
        }
        return null;
    }
}
