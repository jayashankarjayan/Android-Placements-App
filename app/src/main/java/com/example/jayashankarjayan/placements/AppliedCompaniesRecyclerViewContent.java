package com.example.jayashankarjayan.placements;

/**
 * Created by Jayashankar Jayan on 11/28/2017.
 */

public class AppliedCompaniesRecyclerViewContent {
    private String title, jobName, tableName;

    public AppliedCompaniesRecyclerViewContent() {
    }

    public AppliedCompaniesRecyclerViewContent(String title, String jobName, String tableName) {
        this.title = title;
        this.jobName = jobName;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}