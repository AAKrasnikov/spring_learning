package org.example.app.exceptions;

public class BookShelfUploadFileException extends Exception {
    private final String message;

    public BookShelfUploadFileException(String message){
       this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
