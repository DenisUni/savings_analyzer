package org.domain;

public class IncomeSourceInputElement {
    private String name;
    private double amount;
    private String description;
    private boolean isActive;

    public IncomeSourceInputElement(String name, double amount, String description, boolean isActive) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }
}
