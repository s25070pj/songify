package com.songify.song.infrastructure.controller.dto.response;

import com.songify.song.domain.Model.Song;

public record GetSongResponseDto(
        Song song
) {
}
