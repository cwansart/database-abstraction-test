package de.cwansart.dbabstractiontest.repository;

import de.cwansart.dbabstractiontest.dto.GeneVariant;

import java.util.List;

public interface Repository extends AutoCloseable {

    List<GeneVariant> getAll();
}
