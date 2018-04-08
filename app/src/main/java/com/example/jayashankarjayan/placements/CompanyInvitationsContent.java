package com.example.jayashankarjayan.placements;

/**
 * Created by Jayashankar Jayan on 12/3/2017.
 */

public class CompanyInvitationsContent {

    private String companyName, dateOfSelection,id,accepted;


    public CompanyInvitationsContent(String id, String companyName, String dateOfSelection,String accepted) {
        this.id = id;
        this.companyName = companyName;
        this.dateOfSelection = dateOfSelection;
        this.accepted = accepted;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDateOfSelection() {
        return dateOfSelection;
    }

    public void setDateOfSelection(String dateOfSelection) {
        this.dateOfSelection = dateOfSelection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }
}
