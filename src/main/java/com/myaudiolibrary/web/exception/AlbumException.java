package com.myaudiolibrary.web.exception;

import com.myaudiolibrary.web.model.Album;


public class AlbumException extends Throwable {

    public static final String ID = "L'identifiant passé ne correspond pas à l'identifiant de l'artiste : ";

    public AlbumException(String message, Album album, Object valeurIncorrecte) {
        super(message + valeurIncorrecte + ", album : " + album.toString());
        System.out.println(this.getMessage());
    }

    public AlbumException(String message) {
        super(message);
        System.out.println(this.getMessage());
    }

}
