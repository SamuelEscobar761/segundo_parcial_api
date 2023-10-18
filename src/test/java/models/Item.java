package models;

public class Item {
    private final int id;
    private final String content;

    public Item(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
