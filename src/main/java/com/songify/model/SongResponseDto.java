package com.songify.model;

import java.util.List;
import java.util.Map;

public record SongResponseDto(Map<Integer, String> songs) {
}
