package com.myaudiolibrary.web.repository;

import com.myaudiolibrary.web.model.Artist;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends PagingAndSortingRepository<Artist,Long> {

    Artist findByName(String name);

    List<Artist> findByNameContaining(String name);

    @Override
    Optional<Artist> findById(Long id);




}
