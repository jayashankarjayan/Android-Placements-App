package com.example.jayashankarjayan.placements;

/**
 * Created by Jayashankar Jayan on 11/19/2017.
 */
public class Recycler_View_Content {
    private String title, jobName, buttonType, tableName;

    public Recycler_View_Content() {
    }

    public Recycler_View_Content(String title, String jobName, String buttonType, String tableName) {
        this.title = title;
        this.jobName = jobName;
        this.buttonType = buttonType;
        this.tableName = tableName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getJob() {
        return jobName;
    }

    public void setJob(String genre) {
        this.jobName = jobName;
    }

    public String getButtonType() {
        return buttonType;
    }

    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}