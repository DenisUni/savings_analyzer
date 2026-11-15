package org.domain;

public class DistributionListElement {

    private String name;
    private Double percent;

    public DistributionListElement(String name, Double percent){
        this.name = name;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public Double getPercent() {
        return percent;
    }

    @Override
    public String toString() {
        return "DistributionListElement{" +
                "name='" + name + '\'' +
                ", percent=" + percent +
                '}';
    }
}
