package org.example.web.dto;

import javax.validation.constraints.NotNull;

public class BookSizeToRemove {

    @NotNull
    Integer size;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
