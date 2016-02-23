package com.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.grails.Grails;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = null, propertyFile = null;
        final String path;
        BufferedReader input = null;
        final Grails grails;

        try {
            int labelingLevel;
            Properties prop = new Properties();

            propertyFile = new FileInputStream("src/resources/config.properties");
            prop.load(propertyFile);
            labelingLevel = Integer.parseInt(prop.getProperty("labeling_level"));
            path = prop.getProperty("dataset_path");
            inputStream = new FileInputStream(new File(path));
            input = new BufferedReader(new InputStreamReader(inputStream));
            grails = new Grails(labelingLevel);

            input.lines().forEach(line -> {
                String[] parts = line.split(" ");
                grails.prepareIndex(Long.parseUnsignedLong(parts[0]), Long.parseUnsignedLong(parts[1]));
            });

            grails.findSCCs();

            input.close();
            inputStream = new FileInputStream(new File(path));
            input = new BufferedReader(new InputStreamReader(inputStream));

            input.lines().forEach(line -> {
                String[] parts = line.split(" ");
                grails.matchSCCs(Long.parseUnsignedLong(parts[0]), Long.parseUnsignedLong(parts[1]));
            });
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Property file not found");
        }
        finally {
            try {
                if (input != null)
                    input.close();
                if (propertyFile != null)
                    propertyFile.close();
            } catch (IOException e) {
                System.out.println("Can't close file properly");
            }
        }
    }
}
