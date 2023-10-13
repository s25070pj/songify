package com.songify.song.domain.Model;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(String message) {
        super(message);


    }
}
