package com.test.service;

import com.test.entity.GalleryEntity;
import com.test.repository.GalleryEntityRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Log4j2
public class GalleryServiceTest {
    @Autowired
    private GalleryService galleryService;
    @Autowired
    private GalleryEntityRepository galleryRepository;

    @Test
    public void testFetchAndSaveGalleryEntities() {
        // API로부터 50개의 데이터를 가져와서 저장하는 메서드
        galleryService.fetchAndSaveGalleryEntities();
    }

    @Test
    public void testCreate() {
        // 엔티티 생성 테스트
        GalleryEntity galleryEntity = GalleryEntity.builder()
                .galContentId("121212")
                .galTitle("테스트 엔티티")
                .galWebImageUrl("http://test.com/createTest.jpg")
                .build();

        GalleryEntity createdEntity = galleryService.create(galleryEntity);
        assertNotNull(createdEntity.getId()); // ID가 생성되었는지 확인
        log.info("엔티티 생성 테스트 완료: {}", createdEntity);
    }

    @Test
    public void testUpdate() {
        Long Id = 5L; // 업데이트할 ID

        // 기존 엔티티 가져오기
        GalleryEntity existingEntity = galleryService.findById(Id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));

        // 엔티티 내용 업데이트
        existingEntity.setGalTitle("업데이트된 엔티티");
        existingEntity.setGalWebImageUrl("http://test.com/updated.jpg");

        // 업데이트 수행
        GalleryEntity updatedEntity = galleryService.update(existingEntity);
        assertEquals("업데이트된 엔티티", updatedEntity.getGalTitle()); // 제목 확인

        log.info("업데이트 완료: {}", updatedEntity);
    }

    @Test
    public void testFindById() {
        Long existingId = 5L; // 조회할 기존 ID

        // ID로 엔티티 조회
        GalleryEntity foundEntity = galleryService.findById(existingId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 엔티티입니다."));

        // 조회한 엔티티의 모든 정보를 확인
        assertNotNull(foundEntity);
        log.info("엔티티 조회 결과: {}", foundEntity);
        assertEquals(existingId, foundEntity.getId());
    }

    @Test
    public void testDeleteById() {
        Long existingId = 1L; // 삭제할 ID

        // 삭제
        galleryService.deleteById(existingId);
        log.info("{} ID의 엔티티 삭제 완료", existingId);

        // 삭제 후 조회 시 Optional.empty() 반환 확인
        Optional<GalleryEntity> deletedEntity = galleryRepository.findById(existingId);
        assertEquals(Optional.empty(), deletedEntity);
        log.info("삭제된 엔티티 조회 결과: {}", deletedEntity);
    }
}
