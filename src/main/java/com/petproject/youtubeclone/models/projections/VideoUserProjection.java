package com.petproject.youtubeclone.models.projections;

import java.time.LocalDateTime;

public interface VideoUserProjection {

    String getVideoId();
    String getTitle();
    String getChannelName();
    String getThumbnail();
    int getUserId();
    String getPhotoUrl();
}
