package com.moves.movesCelebrity.datastore;

public class DBConnection {

    protected String host;
    protected int port;
    protected String db;
    protected String user;
    protected String password;

    protected DBConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static DBConnection create(String host, int port) {
        return new DBConnection(host, port);
    }

    public DBConnection user(String user) {
        this.user = user;
        return this;
    }

    public DBConnection password(String password) {
        this.password = password;
        return this;
    }

    public DBConnection db(String db) {
        this.db = db;
        return this;
    }

    public String getDb() {
        return db;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBConnection that = (DBConnection) o;

        if (port != that.port) return false;
        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (db != null ? !db.equals(that.db) : that.db != null) return false;
        return !(user != null ? !user.equals(that.user) : that.user != null);

    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + port;
        result = 31 * result + (db != null ? db.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

}
