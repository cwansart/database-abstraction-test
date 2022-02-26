package de.cwansart.dbabstractiontest;

import de.cwansart.dbabstractiontest.dto.GeneVariant;
import de.cwansart.dbabstractiontest.repository.Repository;
import de.cwansart.dbabstractiontest.repository.derby.DerbyRepository;
import de.cwansart.dbabstractiontest.repository.neo4j.Neo4jRepository;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        try (Repository repository = new DerbyRepository()) {
            List<GeneVariant> allGeneVariants = repository.getAll();
            System.out.println(allGeneVariants);
        }

        try (Repository repository = new Neo4jRepository()) {
            List<GeneVariant> allGeneVariants = repository.getAll();
            System.out.println(allGeneVariants);
        }
    }
}
