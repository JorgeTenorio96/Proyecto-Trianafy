package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository artrepo;
    private final SongRepository songrepo;

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
        List<Song> songList = songrepo.findAll();
        Artist artist  = artrepo.findById(id).orElse(null);

        if (artrepo.existsById(id)){
            for(int i = 0; i < songList.size(); i++){
                if(songList.get(i).getArtist() == artist){
                    songList.get(i).setArtist(null);
                }
            }
        }
            artrepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
