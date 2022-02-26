package de.cwansart.dbabstractiontest.repository.neo4j;

import de.cwansart.dbabstractiontest.dto.GeneType;
import de.cwansart.dbabstractiontest.dto.GeneVariant;
import de.cwansart.dbabstractiontest.repository.Repository;
import org.neo4j.driver.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Neo4jRepository implements Repository {

    private final Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "FgEjyEoSEG3GyPxVpJ!ofxez"));

    public Neo4jRepository() {
        if (getAll().size() == 0) {
            try(Session session = driver.session()) {
                session.writeTransaction(transaction -> transaction.run("CREATE (CYP51A1:GeneVariant {title:'CYP51A1', type:'CYP'})\n" +
                        "CREATE (CYP2W1:GeneVariant {title:'CYP2W1', type:'CYP'})\n" +
                        "CREATE (CYP26A1:GeneVariant {title:'CYP26A1', type:'CYP'})").consume());
            }
        }
    }

    @Override
    public List<GeneVariant> getAll() {
        try (Session session = driver.session()) {
            return session.readTransaction(transaction -> transaction.run("MATCH (n) RETURN n")
                            .list(record -> record.asMap(Neo4jRepository::convert)))
                    .stream().map(stringObjectMap -> stringObjectMap.get("n"))
                    .map(map -> {
                        Map<String, String> row = (Map<String, String>) map;
                        String title = row.get("name");
                        String type = row.get("type");
                        return new GeneVariant(title, GeneType.valueOf(type));
                    })
                    .collect(Collectors.toList());
        }

    }

    private static Object convert(Value value) {
        switch (value.type().name()) {
            case "PATH":
                return value.asList(Neo4jRepository::convert);
            case "NODE":
            case "RELATIONSHIP":
                return value.asMap();
        }
        return value.asObject();
    }

    @Override
    public void close() {
        driver.close();
    }
}
