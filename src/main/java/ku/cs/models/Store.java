package ku.cs.models;

public class Store {
    private String user;
    private String nameStore;

    public Store(String user, String nameStore) {
        this.user = user;
        this.nameStore = nameStore;
    }

    public String getNameStore() {
        return nameStore;
    }
}
