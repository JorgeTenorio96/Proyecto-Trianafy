package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.CreatePlaylistDTO;
import com.salesianostriana.dam.trianafy.dto.GetPlaylistDTO;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoConverter;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistRepository playrepo;
    private final PlaylistDtoConverter dtoConverter;

    private final SongRepository songrepo;



    @GetMapping("/list/")
    public ResponseEntity <List<GetPlaylistDTO>> findAllPlaylist(){
        List<Playlist> data = playrepo.findAll();
        if(data.isEmpty()){
            return ResponseEntity.notFound().build();
        }else  {
            List<GetPlaylistDTO> result = data.stream()
                    .map(GetPlaylistDTO::of)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }

    }

    @GetMapping("/list/{id}")
    public ResponseEntity <Playlist> findPlaylistById(@PathVariable Long id){

        return ResponseEntity.of(playrepo.findById(id));

    }


    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> deletePlaylist (@PathVariable Long id){
        if (playrepo.existsById(id))
            playrepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/list/")
    public ResponseEntity<CreatePlaylistDTO> createPlaylist(@RequestBody CreatePlaylistDTO dto){
        Playlist newPlaylist = dtoConverter.createPlaylistDtotoPlaylist(dto);

        playrepo.save(newPlaylist);

        CreatePlaylistDTO dtoCreateList = new CreatePlaylistDTO(newPlaylist.getId(), newPlaylist.getName(), newPlaylist.getDescription());

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoCreateList);
    }

    @PutMapping("/list/{id}")
    public ResponseEntity<GetPlaylistDTO> editPlaylist(@RequestBody CreatePlaylistDTO dto, @PathVariable Long id){
        Playlist playlist = playrepo.findById(id).map(old ->{
            old.setName(dto.getName());
            old.setDescription(dto.getDescription());
            return playrepo.save(old);
        }).orElse(null);

        if (playlist == null){
            return ResponseEntity.notFound().build();
        }
        GetPlaylistDTO getPlaylistDTO = dtoConverter.playlistToGetPlaylistDTO(playlist);
                return ResponseEntity.ok(getPlaylistDTO);
    }

    @PostMapping("/list/{idList}/song/{idSong}")
    public ResponseEntity<Playlist> addSongToPlaylist(@PathVariable("idList") Long idList, @PathVariable("idSong") Long idSong){
        if(!playrepo.existsById(idList) && !songrepo.existsById(idSong)){
            return ResponseEntity.notFound().build();
        }else{
        Playlist playlist = playrepo.findById(idList).orElse(null);
        Song song = songrepo.findById(idSong).orElse(null);
        playlist.addSong(song);
        playrepo.save(playlist);

        return ResponseEntity.ok(playlist);

    }}




}
