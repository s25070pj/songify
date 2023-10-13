package com.songify.song.domain.Model;

import lombok.Builder;

@Builder
public record Song(String songName, String artistName) {
}
