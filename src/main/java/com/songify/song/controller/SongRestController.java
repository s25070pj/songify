package com.songify.song.controller;

import com.songify.song.dto.DeleteSongResponseDto;
import com.songify.song.dto.SingleSongResponseDto;
import com.songify.song.dto.SongRequestDto;
import com.songify.song.dto.SongResponseDto;
import com.songify.song.error.SongNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Log4j2
@RestController

public class SongRestController {

    Map<Integer, String> database = new HashMap<>(Map.of(
            1, "shawnmendes song1",
            2, "ariana grande song2",
            3, "arfqfq2",
            4, "arirety34hy42"
    ));

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) throws RuntimeException {

        if (limit != null) {
            Map<Integer, String> limitedMap = database.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            SongResponseDto response = new SongResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        SongResponseDto response = new SongResponseDto(database);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) throws RuntimeException {
        log.info(requestId);
        String song = database.get(id);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> addSong(@RequestBody @Valid SongRequestDto request) {
        String songName = request.songName();
        log.info("adding new song" + songName);
        database.put(database.size() + 1, songName);
        return ResponseEntity.ok(new SingleSongResponseDto(songName));

    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable @Valid Integer id) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
            //  return ResponseEntity.status(HttpStatus.NOT_FOUND)
            //            .body(new ErrorDeleteSongResponseDto("Song with id:" + id + " not found", HttpStatus.NOT_FOUND));

        }
        database.remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("you deleted song with id: " + id, HttpStatus.OK));
    }


}
