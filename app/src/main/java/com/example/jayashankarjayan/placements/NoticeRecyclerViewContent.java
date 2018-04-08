package com.example.jayashankarjayan.placements;

/**
 * Created by Jayashankar Jayan on 11/25/2017.
 */


public class NoticeRecyclerViewContent {
    private String notice_title, notice_content, adder_name, date_of_issue;

    public NoticeRecyclerViewContent() {
    }

    public NoticeRecyclerViewContent(String notice_title, String notice_content, String adder_name, String date_of_issue) {
        this.notice_title = notice_title;
        this.notice_content = notice_content;
        this.adder_name = adder_name;
        this.date_of_issue = date_of_issue;
    }

    public String getNoticeTitle() {
        return notice_title;
    }

    public void setNoticeTitle(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNoticeContent() {
        return notice_content;
    }

    public void setNoticeContent(String notice_content) {
        this.notice_content= notice_content;
    }

    public String getAdderName() {
        return adder_name;
    }

    public void setAdderName(String adder_name) {
        this.adder_name = adder_name;
    }

    public String getDate_of_issue() {
        return date_of_issue;
    }

    public void setDate_of_issue(String date_of_issue) {
        this.date_of_issue = date_of_issue;
    }
}