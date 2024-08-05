package com.petproject.youtubeclone;

import com.petproject.youtubeclone.models.VideoElastic;
import com.petproject.youtubeclone.services.VideoSearchService;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class VideoSearchServiceTests {
    @Autowired
    private VideoSearchService service;

    @Test
    public void testSearchVideoService() {
        String title = "Zirkzee Ten Hag";
        String desc = "Zirkzee Ten Hag";
        Page<VideoElastic> page = service.searchVideo(title,1,12);
        assertThat(page.getContent()).isNotEmpty();
        for (VideoElastic v : page.getContent()){
            System.out.println(v.getTitle());
        }
    }

}
