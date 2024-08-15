package com.test.service;

import com.test.entity.GalleryEntity;
import com.test.repository.GalleryEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final RestTemplate restTemplate;
    private final GalleryEntityRepository galleryEntityRepository;

    private static final String API_URL = "https://apis.data.go.kr/B551011/PhotoGalleryService1/galleryList1?serviceKey=STJFknlSdzQwELb1ogCDbPjn8svMGFEON3QA5a%2BLnqh4g%2FCf%2BIDs1Iv%2FoLQ3V1Xk2954sqEaqDtYJ3Yd3G1Mwg%3D%3D&numOfRows=50&pageNo=1&MobileOS=ETC&MobileApp=TestApp&_type=json";

    @Override
    public GalleryEntity create(GalleryEntity galleryEntity) {
        return galleryEntityRepository.save(galleryEntity);
    }

    @Override
    public Optional<GalleryEntity> findById(Long id) {
        return galleryEntityRepository.findById(id);
    }

    @Override
    public GalleryEntity update(GalleryEntity galleryEntity) {
        return galleryEntityRepository.save(galleryEntity);
    }

    @Override
    public void deleteById(Long id) {
        galleryEntityRepository.deleteById(id);
    }

    @Override
    public void fetchAndSaveGalleryEntities() {
        log.info("API에서 갤러리 엔티티를 가져오는 중...");
        try {
            URI uri = new URI(API_URL);
            log.info("API 호출 URI: {}", uri);
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            log.info("API 응답: {}", response);
            Map<String, Object> body = (Map<String, Object>) response.get("response");
            Map<String, Object> items = (Map<String, Object>) body.get("body");
            Map<String, Object> itemObj = (Map<String, Object>) items.get("items");
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemObj.get("item");
            itemList.forEach(this::saveGalleryEntity);
            log.info("{}개의 갤러리 엔티티가 성공적으로 저장되었습니다.", itemList.size());
        } catch (URISyntaxException e) {
            log.error("URI 구문이 잘못되었습니다: {}", e.getMessage());
        } catch (ClassCastException e) {
            log.error("API 응답 형식이 잘못되었습니다: {}", e.getMessage());
        } catch (NullPointerException e) {
            log.error("API 응답에 필요한 데이터가 없습니다: {}", e.getMessage());
        } catch (Exception e) {
            log.error("갤러리 엔티티 저장 중 오류 발생: {}", e.getMessage());
        }
    }

    private void saveGalleryEntity(Map<String, Object> item) {
        try {
            GalleryEntity galleryEntity = new GalleryEntity();
            galleryEntity.setGalContentId((String) item.get("galContentId"));
            galleryEntity.setGalContentTypeId((String) item.get("galContentTypeId"));
            galleryEntity.setGalTitle((String) item.get("galTitle"));
            galleryEntity.setGalWebImageUrl((String) item.get("galWebImageUrl"));
            galleryEntity.setGalCreatedtime((String) item.get("galCreatedtime"));
            galleryEntity.setGalModifiedtime((String) item.get("galModifiedtime"));
            galleryEntity.setGalPhotographyMonth((String) item.get("galPhotographyMonth"));
            galleryEntity.setGalPhotographyLocation((String) item.get("galPhotographyLocation"));
            galleryEntity.setGalPhotographer((String) item.get("galPhotographer"));
            galleryEntity.setGalSearchKeyword((String) item.get("galSearchKeyword"));

            galleryEntityRepository.save(galleryEntity);
        } catch (Exception e) {
            log.error("갤러리 엔티티 저장 실패: {}", e.getMessage());
        }
    }
}
