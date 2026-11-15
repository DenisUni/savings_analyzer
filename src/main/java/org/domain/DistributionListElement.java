package org.domain;

public class DistributionListElement {

    private String name;
    private Double percent;

    public DistributionListElement(String name, Double percent){
        this.name = name;
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "DistributionListElement{" +
                "name='" + name + '\'' +
                ", percent=" + percent +
                '}';
    }
}
