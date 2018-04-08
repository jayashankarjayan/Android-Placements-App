package com.example.jayashankarjayan.placements;

/**
 * Created by Jayashankar Jayan on 11/27/2017.
 */

public class SortingRecyclerViewContent {

    private String title;

    public SortingRecyclerViewContent() {
    }

    public SortingRecyclerViewContent(String title) {
        this.title = title;
    }

    public String getSortingItemTitle() {
        return title;
    }

    public void setSortingItemTitle(String title) {
        this.title= title;
    }
}
