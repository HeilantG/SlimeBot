package com.handcraft.features.programmerCalendar;

public enum SpecialsEnum {
    SPECIALS(2014, "bad", "待在男（女）友身边", "脱团火葬场，入团保平安。");

    private Integer date;
    private String type;
    private String name;
    private String description;

    SpecialsEnum(Integer date, String type, String name, String description) {
        this.setDate(date);
        this.setType(type);
        this.setName(name);
        this.setDescription(description);
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
