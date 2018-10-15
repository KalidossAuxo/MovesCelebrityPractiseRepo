package com.moves.movesCelebrity.datastore;

import com.moves.movesCelebrity.services.db.DBService;

import static com.moves.movesCelebrity.configuration.MovesAppConfiguration.COLLECTIONS_ACCESS_TOKEN;

public class BaseDao {

    protected static DBService mongoDBService;

    public static void initialize(MongoConnection connection) {
        mongoDBService = new DBService(connection);
        mongoDBService.createTimeIndex(COLLECTIONS_ACCESS_TOKEN);
    }

    public static void close() {
        mongoDBService.close();
    }
}
