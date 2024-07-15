package com.petproject.youtubeclone.models.projections;

import java.time.LocalDateTime;

public interface ChannelProjection {
    int getUserId();
    String getChannelName();
    String getPhotoUrl();
    LocalDateTime getCreateAt();
    int getTotalVideo();
    default String getPhotosImagePath(){
        if(getPhotoUrl() == null )
            return "/user-photos/defaultavatar.jpg";
        else
            return "/user-photos/" + getUserId() + "/" + getPhotoUrl();
    };
}
