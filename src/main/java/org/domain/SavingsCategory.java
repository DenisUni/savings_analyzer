package org.domain;

import java.util.UUID;

public class SavingsCategory {

    private String category_id;
    private String save_id;
    private String id;
    private String name;
    private double percent;
    private String description;


    public SavingsCategory(String save_id, String id, String name, double percent, String description) {
        this.save_id = save_id;
        this.id = id;
        this.category_id = UUID.randomUUID().toString();
        this.name = name;
        this.percent = percent;
        this.description = description;
    }

    public SavingsCategory(String category_id,String save_id, String id,  String name, double percent, String description) {
        this.save_id = save_id;
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.percent = percent;
        this.description = description;
    }

    public String getSave_id() {
        return save_id;
    }

    public void setSave_id(String save_id) {
        this.save_id = save_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SavingsCategory{" +
                "save_id='" + save_id + '\'' +
                ", id='" + id + '\'' +
                ", category_id='" + category_id + '\'' +
                ", name='" + name + '\'' +
                ", percent=" + percent +
                ", description='" + description + '\'' +
                '}';
    }
}
