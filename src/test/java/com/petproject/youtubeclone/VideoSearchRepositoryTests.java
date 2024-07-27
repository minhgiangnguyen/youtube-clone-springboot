package com.petproject.youtubeclone;

import com.petproject.youtubeclone.models.VideoElastic;
import com.petproject.youtubeclone.repositories.elasticsearch.VideoSearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VideoSearchRepositoryTests {

    @Autowired
    private VideoSearchRepository repo;

    @Test
    public void testSearchVideo() {
        Pageable pageable = PageRequest.of(0, 1);
        String title = "batman";
//        Page<Video> searchVideos = repo.findByTitle(title,pageable);
        List<VideoElastic> searchVideos = repo.findByTitleContaining(title);
        assertThat(searchVideos).isNotEmpty();

    }
}
