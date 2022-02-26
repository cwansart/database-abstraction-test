package de.cwansart.dbabstractiontest.repository.derby;

import de.cwansart.dbabstractiontest.dto.GeneType;
import de.cwansart.dbabstractiontest.dto.GeneVariant;
import de.cwansart.dbabstractiontest.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DerbyRepository implements Repository {

    private static final Logger LOGGER = Logger.getLogger(DerbyRepository.class.getName());

    private static final String connectionUrl = "jdbc:derby:MyTestDb;create=true";

    private Connection connection;

    public DerbyRepository() {
        connect();
        Init.init(connection);
    }

    @Override
    public List<GeneVariant> getAll() {
        List<GeneVariant> geneVariants = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM GENE_VARIANTS");
            while (resultSet.next()) {
                String title = resultSet.getString(1);
                String type = resultSet.getString(2);
                geneVariants.add(new GeneVariant(title, GeneType.valueOf(type)));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not get all gene variants", e);
            throw new RuntimeException(e);
        }

        return geneVariants;
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not connect to " + connectionUrl, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
