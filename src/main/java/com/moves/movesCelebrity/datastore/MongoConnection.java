package com.moves.movesCelebrity.datastore;

public class MongoConnection extends DBConnection {

    private MongoConnection(String host, int port, String db) {
        super(host, port);
        this.db = db;
    }

    public static MongoConnection create(String host, int port, String db) {
        return new MongoConnection(host, port, db);
    }
}
