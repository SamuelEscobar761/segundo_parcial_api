package models;

public class Project {
    private final int id;
    private final String content;

    public Project(int id, String content) {
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
