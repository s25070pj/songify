package com.songify.song.domain.repository;

import com.songify.song.domain.Model.Song;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {

    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("Mockingbird", "Eminem"),
            2, new Song("hello kity", "grochu"),
            3, new Song("patotrap", "lukasz"),
            4, new Song("hot sixteen challenge", "marek")

    ));

    public Song saveToDatabase(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }

    public Map<Integer, Song> findAll(){

        return database;
    }
}
