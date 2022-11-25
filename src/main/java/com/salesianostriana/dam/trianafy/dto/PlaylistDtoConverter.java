package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import org.springframework.stereotype.Component;

@Component
public class PlaylistDtoConverter {

    public Playlist createPlaylistDtotoPlaylist(CreatePlaylistDTO c) {
        return new Playlist(
                c.getName(),
                c.getDescription()
        );
    }
    public GetPlaylistDTO playlistToGetPlaylistDTO(Playlist p){
        return GetPlaylistDTO
                .builder()
                .name(p.getName())
                .build();

    }
}
