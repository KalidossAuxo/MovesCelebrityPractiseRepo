package com.moves.movesCelebrity.services.db;

import org.bson.Document;

import java.util.List;

public interface DBServiceInterface {
    Document fetch(List<Document> queryList, String collectionName);
    Object fetchOne(String collectionName, Object searchQuery, Object sortQuery);
    Document insert(String collectionName, Document document);
    Document insert(String collectionName, List<Document> documents);
    void update(String collectionName, Document queryDocument, Document updatedDocument);
    void delete(String id, String collectionName);
}
