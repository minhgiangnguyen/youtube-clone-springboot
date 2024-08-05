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
        Pair<Integer, List<VideoElastic>> pair = service.searchVideo(title,1,12);
        assertThat(pair.getValue()).isNotEmpty();
        for (VideoElastic v : pair.getValue()){
            System.out.println(v.getTitle());
        }
        System.out.println(pair.getKey());
    }

}
