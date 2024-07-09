package com.petproject.youtubeclone.models.projections;

public interface VideoThumbPath {
    String getVideoId();
    String getThumbnail();
    UserAvatarPath getUser();
}
