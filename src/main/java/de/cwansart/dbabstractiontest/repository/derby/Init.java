package de.cwansart.dbabstractiontest.repository.derby;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper class to create and initialize the database structure for {@link DerbyRepository}.
 */
class Init {

    private static final Logger LOGGER = Logger.getLogger(Init.class.getName());

    static void init(Connection connection) {
        if (!createTableIfNotExists(connection)) {
            // then
            initTable(connection);
        }
    }

    private static boolean createTableIfNotExists(Connection connection) {
        boolean tableExists = false;
        try (Statement statement = connection.createStatement()) {
            statement.execute("create table gene_variants (title VARCHAR(255) primary key, type VARCHAR(5))");
            LOGGER.log(Level.INFO, "Created table");
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                LOGGER.log(Level.INFO, "Table already exists");
                tableExists = true;
            } else {
                LOGGER.log(Level.SEVERE, "Could not create table", e);
                throw new RuntimeException(e);
            }
        }
        return tableExists;
    }

    private static void initTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO gene_variants VALUES\n" +
                    "('CYP51A1', 'CYP'),\n" +
                    "('CYP2W1', 'CYP'),\n" +
                    "('CYP26A1', 'CYP')"
            );
            LOGGER.log(Level.INFO, "Initialized database");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not initialize table", e);
            throw new RuntimeException(e);
        }
    }
}
