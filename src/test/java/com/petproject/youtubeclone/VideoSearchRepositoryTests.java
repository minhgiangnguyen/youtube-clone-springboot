package com.petproject.youtubeclone;

import com.petproject.youtubeclone.models.VideoElastic;
import com.petproject.youtubeclone.repositories.elasticsearch.VideoSearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class VideoSearchRepositoryTests {

    @Autowired
    private VideoSearchRepository repo;

    @Test
    public void testSearchVideo() {
        Pageable pageable = PageRequest.of(0, 1);
        String title = "old";
        List<VideoElastic> searchVideos = repo.findByTitleContaining(title);
        assertThat(searchVideos).isNotEmpty();
        for (VideoElastic v : searchVideos){
            System.out.println(v.getTimeAgo());
        }

    }
}
