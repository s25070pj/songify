package com.songify.controller;

import com.songify.model.SongResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SongsController {

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(){
        SongResponseDto response = new SongResponseDto(List.of("shawnmendes song1", "ariana grande song2"));
        return ResponseEntity.ok(response);
    }

}
