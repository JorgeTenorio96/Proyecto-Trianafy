package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.dto.CreateSongDTO;
import com.salesianostriana.dam.trianafy.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongRepository songrepo;

    private final SongDtoConverter dtoConverter;

    private final ArtistRepository artrepo;

    private final SongService songService;

    private final ArtistRepository artistRepository;

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
    @PostMapping("/song/")
    public ResponseEntity<Song> createSong(@RequestBody CreateSongDTO dto){
        if (dto.getArtistId() == null){
            return ResponseEntity.badRequest().build();
        }

        Song newSong = dtoConverter.createSongDtotoSong(dto);

        Artist artist = artrepo.findById(dto.getArtistId()).orElse(null);

        newSong.setArtist(artist);

        return ResponseEntity.status(HttpStatus.CREATED).body(songrepo.save(newSong));
    }
    @PutMapping("/song/{id}")
    public ResponseEntity<Song> edit(@RequestBody Song s, @PathVariable Long id){
        return null;
    }







}
