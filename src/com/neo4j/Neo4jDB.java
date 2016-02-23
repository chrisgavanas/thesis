package com.neo4j;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4jDB {
    private GraphDatabaseService db;

    public void createDB(String path) {
        GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
        this.db = dbFactory.newEmbeddedDatabase(new File(path));
    }

    public void shutdownServer() {
        this.db.shutdown();
    }
}
