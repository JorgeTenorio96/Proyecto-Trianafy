package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongRepository songrepo;

    @GetMapping("/song/")
    public ResponseEntity<List<Song>> findAllSongs() {
        return ResponseEntity.ok(songrepo.findAll());
    }
    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findSongById(@PathVariable Long id){
        return ResponseEntity.of(songrepo.findById(id));
    }
    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> deleteSong (@PathVariable Long id) {
        if (songrepo.existsById(id))
            songrepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
