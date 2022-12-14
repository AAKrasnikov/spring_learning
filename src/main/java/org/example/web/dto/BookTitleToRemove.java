package org.example.web.dto;

import javax.validation.constraints.NotBlank;

public class BookTitleToRemove {

    @NotBlank
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
