package de.cwansart.dbabstractiontest.dto;

public class GeneVariant {

    private final String title;

    private final GeneType type;

    public GeneVariant(String title, GeneType type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public GeneType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "GeneVariant{" +
                "title='" + title + '\'' +
                ", type=" + type +
                '}';
    }
}
