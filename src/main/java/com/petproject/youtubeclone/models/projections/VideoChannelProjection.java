package com.petproject.youtubeclone.models.projections;

public interface VideoChannelProjection {
    String getVideoId();
    String getTitle();
    String getThumbnail();
    int getUserId();
    default String getThumbsPath(){
        if (getThumbnail() == null || getUserId() == 0)
            return null;
        return "/user-videos/" + getUserId() + "/" + getThumbnail();
    };
}
