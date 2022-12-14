package org.example.web.dto;
import javax.validation.constraints.NotBlank;

public class BookAuthorToRemove {

    @NotBlank
    String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
