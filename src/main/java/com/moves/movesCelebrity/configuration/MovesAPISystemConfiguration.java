package com.moves.movesCelebrity.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

public class MovesAPISystemConfiguration extends Configuration {
    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @NotEmpty
    private static String mongoHost;
    @NotEmpty
    private static int mongoPortNumber;
    @NotEmpty
    private static String mongoDatabaseName;
    @NotEmpty
    private static String documentFolder;
    @NotEmpty
    private static String baseUrl;

    public static String getMongoHost() {
        return mongoHost;
    }

    public void setMongoHost(String mongoHost) {
        MovesAPISystemConfiguration.mongoHost = mongoHost;
    }

    public static int getMongoPortNumber() {
        return mongoPortNumber;
    }

    public void setMongoPortNumber(int mongoPortNumber) {
        MovesAPISystemConfiguration.mongoPortNumber = mongoPortNumber;
    }

    public static String getMongoDatabaseName() {
        return mongoDatabaseName;
    }

    public void setMongoDatabaseName(String mongoDatabaseName) {
        MovesAPISystemConfiguration.mongoDatabaseName = mongoDatabaseName;
    }

    public static String getDocumentFolder() {
        return documentFolder;
    }

    public void setDocumentFolder(String documentFolder) {
        MovesAPISystemConfiguration.documentFolder = documentFolder;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        MovesAPISystemConfiguration.baseUrl = baseUrl;
    }
}
