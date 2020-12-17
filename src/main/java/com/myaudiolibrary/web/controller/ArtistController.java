package com.myaudiolibrary.web.controller;


import com.myaudiolibrary.web.model.Artist;

import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;


    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String getArtistById(@PathVariable Long id, final ModelMap model){

        Optional<Artist> artistOptional = artistRepository.findById(id);
        //Ici il faudrait gérer l'erreur 404 !
        if(artistOptional.isEmpty()){
            throw new EntityNotFoundException("L'artiste d'identifiant " + id + " n'a pas été trouvé !");
        }
        model.put("artist", artistOptional.get());
        return "detailArtist";
    }

    @RequestMapping(method = RequestMethod.GET, value="", params = "name")
    public String searchArtistByName(@RequestParam String name, final ModelMap model ){
        List<Artist> artists = artistRepository.findByNameContaining(name);
        //Ici il faudrait gérer l'erreur 404 !
        model.put("artists", artists);

        return "listeArtists";
    }


    @RequestMapping(method = RequestMethod.GET, value = "")
    public String listArtists(final ModelMap model,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "10") Integer size,
                               @RequestParam(defaultValue = "ASC") String sortDirection,
                               @RequestParam(defaultValue = "name") String sortProperty){
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Artist> pageArtist = artistRepository.findAll(pageRequest);
        boolean paginate = true;
        model.put("artists", pageArtist);
        model.put("pageNumber", page + 1);
        model.put("previousPage", page - 1);
        model.put("nextPage", page + 1);
        model.put("start", page * size + 1);
        model.put("end", (page) * size + pageArtist.getNumberOfElements());
        model.put("paginate", paginate);
        return "listeArtists";
    }

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView saveArtist(Artist artist, final ModelMap model){
        artistRepository.save(artist);


        return new RedirectView("/artists/" + artist.getId());
    }

    @RequestMapping(method = RequestMethod.POST, value ="/update/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView updateArtist(@PathVariable Long id, Artist artist, final ModelMap model){
        if(artistRepository.existsById(id)) {
            artistRepository.save(artist);
        }
        return new RedirectView("/artists/" + artist.getId());
    }

    @RequestMapping(value ="/delete/{id}", method = RequestMethod.GET)
    public RedirectView deleteArtist(@PathVariable Long id){
        if(!artistRepository.existsById(id)){
            throw new EntityNotFoundException("L'employé d'identifiant " + id + " n'a pas été trouvé !");
        }
        artistRepository.deleteById(id);

        return new RedirectView("/artists");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String newArtist(final ModelMap model){


        return "detailArtist";
    }


}
