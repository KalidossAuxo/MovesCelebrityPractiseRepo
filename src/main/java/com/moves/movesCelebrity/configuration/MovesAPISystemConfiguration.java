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
    @NotEmpty
    private static Long authExpiry;

    public void setAuthExpiry(Long authExpiry) {
        MovesAPISystemConfiguration.authExpiry = authExpiry;
    }


    public static String database;
    public static String host;
    public static int port;
    public static String user;
    public static String password;
    public static String mailerAddress;
    public static String mailerPassword;
    public static String mailServiceHost;
    public static String mailServicePort;
    public static String mailServiceFactoryClass;
    public static String mailServiceAuth;
    public static int defaultStorePort;
    public static String defaultStoreHost;
    public static String defaultStoreType;


    public static String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public static String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public static int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        MovesAPISystemConfiguration.password = password;
    }

    public static String getUser() {
        return user;
    }

    public void setUser(String user) {
        MovesAPISystemConfiguration.user = user;
    }

    public static long getAuthExpiry() {
        return authExpiry;
    }

    public void setAuthExpiry(long authExpiry) {
        MovesAPISystemConfiguration.authExpiry = authExpiry;
    }

    public static String getMailerAddress() {
        return mailerAddress;
    }

    public  void setMailerAddress(String mailerAddress) {
        MovesAPISystemConfiguration.mailerAddress = mailerAddress;
    }

    public static String getMailerPassword() {
        return mailerPassword;
    }

    public  void setMailerPassword(String mailerPassword) {
        MovesAPISystemConfiguration.mailerPassword = mailerPassword;
    }

    public static String getMailServiceHost() {
        return mailServiceHost;
    }

    public  void setMailServiceHost(String mailServiceHost) {
        MovesAPISystemConfiguration.mailServiceHost = mailServiceHost;
    }

    public static String getMailServicePort() {
        return mailServicePort;
    }

    public  void setMailServicePort(String mailServicePort) {
        MovesAPISystemConfiguration.mailServicePort = mailServicePort;
    }

    public static String getMailServiceFactoryClass() {
        return mailServiceFactoryClass;
    }

    public  void setMailServiceFactoryClass(String mailServiceFactoryClass) {
        MovesAPISystemConfiguration.mailServiceFactoryClass = mailServiceFactoryClass;
    }

    public static String getMailServiceAuth() {
        return mailServiceAuth;
    }

    public  void setMailServiceAuth(String mailServiceAuth) {
        MovesAPISystemConfiguration.mailServiceAuth = mailServiceAuth;
    }

    public static int getDefaultStorePort() {
        return defaultStorePort;
    }

    public  void setDefaultStorePort(int defaultStorePort) {
        MovesAPISystemConfiguration.defaultStorePort = defaultStorePort;
    }

    public static String getDefaultStoreHost() {
        return defaultStoreHost;
    }

    public  void setDefaultStoreHost(String defaultStoreHost) {
        MovesAPISystemConfiguration.defaultStoreHost = defaultStoreHost;
    }

    public static String getDefaultStoreType() {
        return defaultStoreType;
    }

    public void setDefaultStoreType(String defaultStoreType) {
        MovesAPISystemConfiguration.defaultStoreType = defaultStoreType;
    }

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

