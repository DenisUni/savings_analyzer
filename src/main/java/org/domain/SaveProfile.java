package org.domain;

import java.util.UUID;

public class SaveProfile {
    private String user_id;
    private String save_id;
    private String name;
    private Double saving_rate;

    public SaveProfile(String user_id, String name, Double saving_rate){
        this.save_id = UUID.randomUUID().toString();
        this.user_id = user_id;
        this.name = name;
        this.saving_rate = saving_rate;
    }

    public SaveProfile(String save_id,String user_id, String name, Double saving_rate){
        this.save_id = save_id;
        this.user_id = user_id;
        this.name = name;
        this.saving_rate = saving_rate;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSave_id() {
        return save_id;
    }

    public void setSave_id(String save_id) {
        this.save_id = save_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSaving_rate() {
        return saving_rate;
    }

    public void setSaving_rate(Double saving_rate) {
        this.saving_rate = saving_rate;
    }

    @Override
    public String toString() {
        return "SaveProfile{" +
                "user_id='" + user_id + '\'' +
                ", save_id='" + save_id + '\'' +
                ", name='" + name + '\'' +
                ", saving_rate=" + saving_rate +
                '}';
    }
}
