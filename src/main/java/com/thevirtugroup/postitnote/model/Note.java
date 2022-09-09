package com.thevirtugroup.postitnote.model;

import javax.validation.constraints.Size;
import java.util.Date;

public class Note {

    private Long id;
    @Size(min = 2, max = 10)
    private String name;
    @Size(min = 1, max = 500)
    private String summary;
    private Date date;

    public Note(Long id, String name, String summary, Date date) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.date = date;
    }

    public Note() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", date=" + date +
                '}';
    }
}
