package com.songify.song.domain.Service;

import com.songify.song.domain.Model.Song;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import org.springframework.http.HttpStatus;
import java.util.Map;

public class SongMapper {

    public static Song mapFromCreateSongRequestDtoToSong(CreateSongRequestDto request) {
        Song song = new Song(request.songName(), request.artistName());
        return song;
    }

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(Song song) {
        CreateSongResponseDto body = new CreateSongResponseDto(song);
        return body;
    }



    public static GetSongResponseDto mapFromSongToGetSongResponseDto(Song song) {
        GetSongResponseDto response = new GetSongResponseDto(song);
        return response;
    }

    public static DeleteSongResponseDto MapFromSongToDeleteSongResponseDto(Integer id) {
        DeleteSongResponseDto response = new DeleteSongResponseDto("you deleted song with id: " + id, HttpStatus.OK);
        return response;
    }


    public static UpdateSongReponseDto mapFromSongToUpdateSongResponseDto(Song newSong) {
        UpdateSongReponseDto update = new UpdateSongReponseDto(newSong);
        return update;
    }

    public static Song mapFromUpdateSongRequestDtoToSong(String songNameToUpdate, String newArtistName) {
        Song newSong = new Song(songNameToUpdate, newArtistName);
        return newSong;
    }

    public static Song mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artistName());
    }

    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(Song updatedSong) {
        return new PartiallyUpdateSongResponseDto(updatedSong);
    }

    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(Map<Integer, Song> database) {
        return new GetAllSongsResponseDto(database);
    }







}
