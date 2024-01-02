package com.leaguesync;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;

public class JsonReaderExample {

    public static void main(String[] args) {
        // Get the file from the resources folder
        InputStream is = JsonReaderExample.class.getClassLoader().getResourceAsStream("modified_champ_data.json");

        if (is != null) {
            try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(isr)) {

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found");
        }
    }
}
