package de.cwansart.dbabstractiontest;

import de.cwansart.dbabstractiontest.dto.GeneVariant;
import de.cwansart.dbabstractiontest.repository.Repository;
import de.cwansart.dbabstractiontest.repository.derby.DerbyRepository;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Repository repository = new DerbyRepository();

        List<GeneVariant> allGeneVariants = repository.getAll();
        System.out.println(allGeneVariants);
    }
}
