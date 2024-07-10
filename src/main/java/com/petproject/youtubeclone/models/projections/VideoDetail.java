package com.petproject.youtubeclone.models.projections;

import java.time.LocalDateTime;

public interface VideoDetail {
//    String getVideoId();
    String getTitle();
    String getThumbnail();
    String getDesc();
    String getVideoUrl();
    LocalDateTime getCreateAt();
    String getVideosPath();
//    UserAvatarPath getUser();
}
