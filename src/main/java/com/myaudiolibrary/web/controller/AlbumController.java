package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;



    @RequestMapping(method = RequestMethod.POST, value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveAlbum(Album album, final ModelMap model, HttpServletRequest request){
        albumRepository.save(album);

        String referer = request.getHeader("Referer");

        return "redirect:"+ referer;
    }

    @RequestMapping(
            value = "/delete/{id}",
            method = RequestMethod.GET //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)//204: rien n'est renvoyé mais c'est normal
    public void deleteAlbum (@PathVariable("id") Long id){
        if(!albumRepository.existsById(id)) {
            throw new EntityNotFoundException("L'album avec l'id : " + id + ", n'existe pas !");
        }

        albumRepository.deleteById(id);

    }

}
