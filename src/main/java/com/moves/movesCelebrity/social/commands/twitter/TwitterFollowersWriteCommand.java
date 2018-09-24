package com.moves.movesCelebrity.social.commands.twitter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moves.movesCelebrity.configuration.MovesConfiguration;
import com.moves.movesCelebrity.dao.SocialMediaPostDao;
import com.moves.movesCelebrity.social.types.Command;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonDeserializer;
import com.moves.movesCelebrity.utils.serdesr.ObjectIDGsonSerializer;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class TwitterFollowersWriteCommand implements Command<Void, ArrayList<Document>> {

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonDeserializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIDGsonSerializer())
            .setPrettyPrinting().create();


    @Override
    public CompletableFuture<Void> execute(ArrayList<Document> documents) {
        return CompletableFuture.supplyAsync(() -> {
            insert(documents);
            return null;
        });

    }

    public void insert(ArrayList<Document> documents) {
//        s3Service.upload("doc1.json","dex.sm-scraper", documents.get(0));
        SocialMediaPostDao.insertMany(documents, MovesConfiguration.COLLECTION_FOLLOWERS_TWITTER);
    }
}
