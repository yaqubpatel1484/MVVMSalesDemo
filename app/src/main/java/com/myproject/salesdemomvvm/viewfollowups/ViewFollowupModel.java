package com.myproject.salesdemomvvm.viewfollowups;

public class ViewFollowupModel {
    private String tid, date, status, description, by_name, next_followup_date, pdf_url;

    public ViewFollowupModel(String tid, String date, String status, String description, String by_name, String next_followup_date, String pdf_url) {
        this.tid = tid;
        this.date = date;
        this.status = status;
        this.description = description;
        this.by_name = by_name;
        this.next_followup_date = next_followup_date;
        this.pdf_url = pdf_url;
    }

    public String getTid() {
        return tid;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getBy_name() {
        return by_name;
    }

    public String getNext_followup_date() {
        return next_followup_date;
    }

    public String getPdf_url() {
        return pdf_url;
    }
}
