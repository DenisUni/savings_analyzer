package org.domain;

import java.util.UUID;

public class IncomeSource {

    private String save_id;
    private String  id;
    private String income_id;
    private String name;
    private double amount;
    private String description;
    private boolean isActive;

    public IncomeSource(String save_id, String id, String name, double amount, String description, boolean isActive) {
        this.save_id = save_id;
        this.id = id;
        this.income_id = UUID.randomUUID().toString();
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.isActive = isActive;
    }

    public IncomeSource(String save_id, String id, String income_id, String name, double amount, String description, boolean isActive) {
        this.save_id = save_id;
        this.id = id;
        this.income_id = income_id;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.isActive = isActive;
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

    public String getIncome_id() {
        return income_id;
    }

    public void setIncome_id(String income_id) {
        this.income_id = income_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public String toString() {
        return "IncomeSource{" +
                "save_id='" + save_id + '\'' +
                ", id='" + id + '\'' +
                ", income_id='" + income_id + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
