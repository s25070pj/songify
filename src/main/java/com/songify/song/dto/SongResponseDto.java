package com.songify.song.dto;

import java.util.List;
import java.util.Map;

public record SongResponseDto(Map<Integer, String> songs) {
}
