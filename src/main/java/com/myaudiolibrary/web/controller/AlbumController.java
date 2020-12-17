package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@CrossOrigin
@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;


    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Album createAlbum(@RequestBody Album album) {
        if(albumRepository.existsByTitle(album.getTitle()) == true){
            throw new EntityExistsException("L'album existe déjà !");
        }
        else if(album.getTitle() == null) {
            throw new IllegalStateException("L'album ne peut avoir un titre 'null' ou vide !");
        }
        else {
            return albumRepository.save(album);
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)//204: rien n'est renvoyé mais c'est normal
    public void deleteAlbum (@PathVariable("id") Long id){
        if(!albumRepository.existsById(id)) {
            throw new EntityNotFoundException("L'album avec l'id : " + id + ", n'existe pas !");
        }

        albumRepository.deleteById(id);
    }

}
