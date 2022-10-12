package com.petermenice.Peter.Menice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="content")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pageName;

    private Object[] displayData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Object[] getDisplayData() {
        return displayData;
    }

    public void setDisplayData(Object[] displayData) {
        this.displayData = displayData;
    }
}
