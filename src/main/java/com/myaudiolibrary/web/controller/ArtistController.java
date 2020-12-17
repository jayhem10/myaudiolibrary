package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;


    @RequestMapping(method = RequestMethod.GET,
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Artist> getArtist(@PathVariable(value = "id") Long id){
        Optional<Artist> optionalArtist = artistRepository.findById(id);
        if(optionalArtist.isEmpty()){
            //Erreur 404
            throw new EntityNotFoundException("L'artiste d'identifiant " + id + " n'a pas été trouvé !");
        }
        return optionalArtist;
    }

    @RequestMapping(
            method = RequestMethod.GET, //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
            produces = MediaType.APPLICATION_JSON_VALUE, //Type MIME des données fournies dans la réponse//
            params = {"name"}
    )
    public List<Artist> searchByName(@RequestParam("name") String name){
       List<Artist> artistByName = artistRepository.findByNameContaining(name);
        if (artistByName == null){
            throw new EntityNotFoundException("L'artiste nommé " + name + " n'existe pas");
        }

        return artistByName;
    }

    @RequestMapping(
            method = RequestMethod.GET, //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
            produces = MediaType.APPLICATION_JSON_VALUE //Type MIME des données fournies dans la réponse//

    )
    public Page<Artist> listArtist(@RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestParam(defaultValue = "name") String sortProperty,
                                      @RequestParam(defaultValue = "ASC") String sortDirection){
        if (page<0){
            throw new IllegalArgumentException("La paramètre page doit être positif ou nul !");
        }
        if (size<=0 || size>50){
            throw new IllegalArgumentException("La valeur doit être comprise entre 1 et 50.");
        }
        if (!"ASC".equalsIgnoreCase(sortDirection) && !"DESC".equalsIgnoreCase(sortDirection)){
            throw new IllegalArgumentException("Le paramètre doit avoir pour valeur ASC ou DESC !");
        }

        return artistRepository.findAll(PageRequest.of(page,size, Sort.Direction.fromString(sortDirection), sortProperty));
    }

    @RequestMapping(
            method = RequestMethod.POST, //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE //Type MIME des données fournies dans la réponse//
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody Artist artist){
        //Si l'artiste avec ce nom existe alors erreur 409
        if (artistRepository.findByName(artist.getName()) !=null) {
            //400
            throw new EntityExistsException("Un artiste avec ce nom existe déjà.");
        }
        return artistRepository.save(artist);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT, //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE //Type MIME des données fournies dans la réponse//
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Artist updateArtist(@PathVariable Long id,@RequestBody Artist artist){
        if(artistRepository.existsById(id)) {
            return artistRepository.save(artist);
        }
        else{
            throw new EntityNotFoundException("L'artiste avec l'id "+ id +" n'a pas été trouvé !");
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)//204: rien n'est renvoyé mais c'est normal
    public void deleteArtist(@PathVariable Long id){
        if(artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException("L'artiste avec l'id " + id + " n'a pas été trouvé !");
        }


    }

}
