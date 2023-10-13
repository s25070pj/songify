package com.songify.song.domain.Service;

import com.songify.song.domain.Model.Song;
import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
public class SongAdder {

    private final SongRepository songRepository;

    public SongAdder(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song addSong(Song song) {
        log.info("adding new song" + song);
        songRepository.saveToDatabase(song);

        return song;
    }





}
