package com.tantely.station.config;

import com.tantely.station.exceptions.DatabaseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConnection {
    private final DatabaseSettings databaseSettings;

    public DatabaseConnection(DatabaseSettings databaseSettings) {
        this.databaseSettings = databaseSettings;
    }

    @Bean
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    databaseSettings.getUrl(),
                    databaseSettings.getUsername(),
                    databaseSettings.getPassword()
            );
        } catch (SQLException e) {
            throw new DatabaseException("Error connecting to database",e);
        }
    }
}
