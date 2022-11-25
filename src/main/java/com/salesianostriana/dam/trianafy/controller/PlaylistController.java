package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistRepository playrepo;



    @GetMapping("/list/")
    public ResponseEntity<List<Playlist>> findAllPlaylist() {
        return ResponseEntity.ok(playrepo.findAll());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Playlist> findPlaylistById(@PathVariable Long id){
        return ResponseEntity.of(playrepo.findById(id));
    }
    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> deletePlaylist (@PathVariable Long id){
        if (playrepo.existsById(id))
            playrepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
