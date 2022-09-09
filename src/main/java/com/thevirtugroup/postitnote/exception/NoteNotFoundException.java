package com.thevirtugroup.postitnote.exception;

public class NoteNotFoundException extends RuntimeException{

    public NoteNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
