package com.petproject.youtubeclone.repositories.elasticsearch;

import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.VideoElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoSearchRepository extends ElasticsearchRepository<VideoElastic,String> {


    List<VideoElastic> findByTitleContaining(String title);

}
