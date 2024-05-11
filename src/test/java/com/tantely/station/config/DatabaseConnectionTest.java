package com.tantely.station.config;

import com.tantely.station.exceptions.DatabaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private DatabaseSettings databaseSettings;

    @Test
    public void testGetConnectionSuccess() {
        var dbCon = new DatabaseConnection(databaseSettings);
        assertThat(dbCon.getConnection()).isNotNull();
    }

    @Test
    public void testGetConnectionFailure() {
        var notValidDbSettings = new DatabaseSettings();
        notValidDbSettings.setUrl("not exist url");
        var dbCon = new DatabaseConnection(notValidDbSettings);
        assertThatThrownBy(dbCon::getConnection)
        .isInstanceOf(DatabaseException.class)
        .hasMessageContaining("Error connecting to database");
    }

}