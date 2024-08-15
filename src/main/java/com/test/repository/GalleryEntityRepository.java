package com.test.repository;

import com.test.entity.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryEntityRepository extends JpaRepository<GalleryEntity, Long> {
}
