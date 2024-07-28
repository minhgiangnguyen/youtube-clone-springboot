package com.petproject.youtubeclone.models;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "videos")
public class VideoElastic {
    @Id
    @Field(type = FieldType.Keyword,name = "video_id")
    private String videoId;
    @Field(type = FieldType.Text,name = "title")
    private String title;
    @Field(type = FieldType.Text,name = "description")
    private String description;
    @Field(type = FieldType.Text,name = "thumbnail")
    private String thumbnail;
//    @Field(type = FieldType.Text,name = "video_url")
//    private String videoUrl;

    @Field(type = FieldType.Date,name = "create_at", format = DateFormat.strict_date_optional_time_nanos)
    private LocalDateTime createAt;
    @Field(type = FieldType.Date,name = "update_at", format = DateFormat.strict_date_optional_time_nanos)
    private LocalDateTime updateAt;

    @Field(type = FieldType.Integer,name = "user_id")
    private int userId;
    @Field(type = FieldType.Text,name = "channel_name")
    private String channelName;
    @Field(type = FieldType.Text,name = "photo_url")
    private String photoUrl;


    @Transient
    public String getThumbsPath() {

        if (thumbnail == null || userId == 0)
            return null;
        return "/user-videos/" + userId + "/" + thumbnail;
    }

    @Transient
    public String getPhotosImagePath() {

        if(photoUrl == null )
            return "/user-photos/defaultavatar.jpg";
        else
            return "/user-photos/" + userId + "/" + photoUrl;
    }
}
