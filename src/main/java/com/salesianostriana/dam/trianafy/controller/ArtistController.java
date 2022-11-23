package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository artrepo;

    @PostMapping("/artist/")
    public ResponseEntity<Artist> newArtist(@RequestBody Artist artist){
        return ResponseEntity.status(HttpStatus.CREATED).body(artrepo.save(artist));
    }
    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> findAllArtist() {
        return ResponseEntity.ok(artrepo.findAll());
    }
    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> findArtistById(@PathVariable Long id){
        return ResponseEntity.of(artrepo.findById(id));
    }
    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> editArtist(@RequestBody Artist artist, @PathVariable Long id){
        return ResponseEntity.of(artrepo.findById(id).map(old ->{
            old.setName(artist.getName());
            return (artrepo.save(old));
        }));
    }
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<?> deleteArtist (@PathVariable Long id){
        if (artrepo.existsById(id))
            artrepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
