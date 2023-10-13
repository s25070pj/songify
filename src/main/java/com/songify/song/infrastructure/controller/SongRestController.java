package com.songify.song.infrastructure.controller;

import com.songify.song.domain.Model.Song;
import com.songify.song.domain.Service.SongAdder;
import com.songify.song.domain.Service.SongMapper;
import com.songify.song.domain.Service.SongRetriever;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.domain.Model.SongNotFoundException;
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
@RequestMapping("/songs")


public class SongRestController {



    private final SongAdder songAdder;
    private final SongRetriever songRetriever;

    public SongRestController(SongAdder songAdder, SongRetriever songRetriever) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
    }

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) throws RuntimeException {

        if (limit != null) {
            Map<Integer, Song> limitedMap = songRetriever.findAllLimitedBy(limit);
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = SongMapper.mapFromSongToGetAllSongsResponseDto(songRetriever.findAll());
        return ResponseEntity.ok(response);

    }



    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) throws RuntimeException {
        log.info(requestId);
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        Song song = allSongs.get(id);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        GetSongResponseDto response = SongMapper.mapFromSongToGetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<CreateSongResponseDto> addSong(@RequestBody @Valid CreateSongRequestDto request) {
        Song song = SongMapper.mapFromCreateSongRequestDtoToSong(request);
        songAdder.addSong(song);
        CreateSongResponseDto body = SongMapper.mapFromSongToCreateSongResponseDto(song);
        return ResponseEntity.ok(body);


    }



    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable @Valid Integer id) {
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
            //  return ResponseEntity.status(HttpStatus.NOT_FOUND)
            //            .body(new ErrorDeleteSongResponseDto("Song with id:" + id + " not found", HttpStatus.NOT_FOUND));

        }
        allSongs.remove(id);
        DeleteSongResponseDto response = SongMapper.MapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongReponseDto> updateSong(@PathVariable Integer id, @RequestBody @Valid UpdateSongRequestDto reponseDto) {
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        String songNameToUpdate = reponseDto.songName();
        String newArtistName = reponseDto.artistName();
        Song newSong = SongMapper.mapFromUpdateSongRequestDtoToSong(songNameToUpdate, newArtistName);
        Song oldSong = allSongs.put(id, newSong);


        log.info("updated song with id " + id + " with oldSongName: " + oldSong.songName() + " and oldArtist name: " + oldSong.artistName() + " to new songName: " + newSong.songName() + " and newSongArtist: " + newSong.artistName());
        UpdateSongReponseDto update = SongMapper.mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(update);

    }


    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id, @RequestBody PartiallyUpdateSongRequestDto request) {
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }

        Song song = allSongs.get(id);
        Song updatedSong = SongMapper.mapFromPartiallyUpdateSongRequestDtoToSong(request);
        Song.SongBuilder builder = Song.builder();
        if (request.songName() != null) {
            builder.songName(request.songName());
            log.info("Partially updated songName");
        } else builder.songName(song.songName());
        if (request.artistName() != null) {
            builder.artistName(request.artistName());
            log.info("Partially updated artistName");
        } else builder.artistName(song.artistName());
        Song newSong = builder.build();

        allSongs.put(id, newSong);
        PartiallyUpdateSongResponseDto body = SongMapper.mapFromSongToPartiallyUpdateSongResponseDto(updatedSong);
        return ResponseEntity.ok(body);


    }

}
