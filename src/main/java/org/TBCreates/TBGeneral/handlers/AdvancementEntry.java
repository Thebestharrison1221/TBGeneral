package org.TBCreates.TBGeneral.handlers;

public class AdvancementEntry {
    private String name;
    private String description;
    private int id;

    public AdvancementEntry(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}
