package com.test.service;


import com.test.entity.GalleryEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface GalleryService {

    GalleryEntity create(GalleryEntity galleryEntity);

    Optional<GalleryEntity> findById(Long id);

    GalleryEntity update(GalleryEntity galleryEntity);

    void deleteById(Long id);

    void fetchAndSaveGalleryEntities();
}
