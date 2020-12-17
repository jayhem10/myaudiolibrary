package com.myaudiolibrary.web.repository;

import com.myaudiolibrary.web.model.Album;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends PagingAndSortingRepository<Album, Long> {
    Boolean existsByTitle(String title);

    void deleteByArtistId(Long artist);
}
