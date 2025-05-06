package com.fatec.techcollie.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

@Configuration
@Profile({"dev", "prod"})
public class SchemaInitializerConfig {

    @Autowired
    private DataSource dataSource;

    @Value("${spring.profiles.active}")
    private String currentProfile;

    @PostConstruct
    public void init() {
        try (Connection connection = dataSource.getConnection()) {
            Statement st = connection.createStatement();
            st.execute(getSqlFromFile());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSqlFromFile() throws Exception {
        try {
            StringBuilder result = new StringBuilder();

            File file = new File("src/main/resources/" + currentProfile + "/schema/script.sql");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
                result.append("\n");
            }

            return result.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}