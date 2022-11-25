package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongDtoConverter {

    public Song createSongDtotoSong(CreateSongDTO c) {
        return new Song(
                c.getTitle(),
                c.getYear(),
                c.getAlbum()
        );
    }
    public GetSongDTO songToGetSongDTO(Song s){
        return GetSongDTO
                .builder()
                .title(s.getTitle())
                .album(s.getAlbum())
                .year(s.getYear())
                .artistName(s.getArtist().getName())
                .build();

    }
}
