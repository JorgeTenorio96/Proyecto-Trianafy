package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.dto.CreateSongDTO;
import com.salesianostriana.dam.trianafy.dto.GetSongDTO;
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
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongRepository songrepo;

    private final SongDtoConverter dtoConverter;

    private final ArtistRepository artrepo;

    private final SongService songService;

    private final ArtistRepository artistRepository;

    @GetMapping("/song/")
    public ResponseEntity<List<GetSongDTO>> findAllSongs() {
        List<Song> data = songrepo.findAll();
        if(data.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            List<GetSongDTO> result = data.stream()
                    .map(GetSongDTO::of)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }


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
    public ResponseEntity<GetSongDTO> createSong(@RequestBody CreateSongDTO dto){
        if (dto.getArtistId() == null){
            return ResponseEntity.badRequest().build();
        }

        Song newSong = dtoConverter.createSongDtotoSong(dto);

        Artist artist = artrepo.findById(dto.getArtistId()).orElse(null);

        newSong.setArtist(artist);
        songrepo.save(newSong);

        GetSongDTO getSongDTO = dtoConverter.songToGetSongDTO(newSong);

        return ResponseEntity.status(HttpStatus.CREATED).body(getSongDTO);
    }
    @PutMapping("/song/{id}")
    public ResponseEntity<GetSongDTO> editSong(@RequestBody CreateSongDTO dto, @PathVariable Long id){
        if(dto.getArtistId() == null){
            return ResponseEntity.badRequest().build();
        }

        Artist artist = artrepo.findById(dto.getArtistId()).orElse(null);

        Song song = songrepo.findById(id).map(old -> {
            old.setTitle(dto.getTitle());
            old.setAlbum(dto.getAlbum());
            old.setYear(dto.getYear());
            old.setArtist(artist);
            return (songrepo.save(old));
        }).orElse(null);

        if(song == null){
            return ResponseEntity.notFound().build();
        }

        GetSongDTO getSongDTO = dtoConverter.songToGetSongDTO(song);


        return ResponseEntity.ok(getSongDTO);
    }







}
