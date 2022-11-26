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
    public GetPlaylistDTO playlistToGetPlaylistDTO(Playlist playlist){
        int numberOfSongs;
        if(playlist.getSongs()==null){
            numberOfSongs = 0;
        }else{
            numberOfSongs = playlist.getSongs().size();
        }
        return GetPlaylistDTO
                .builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .numberOfSongs(playlist.getSongs().size())
                .build();
    }
}
