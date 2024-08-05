package com.petproject.youtubeclone.models;

import jakarta.persistence.Transient;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(indexName = "videos")
public class VideoElastic {
    @Id
    @Field(type = FieldType.Keyword,name = "video_id")
    @EqualsAndHashCode.Include
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
    @Transient
    public String getTimeAgo() {
        LocalDateTime now= LocalDateTime.now();
        Period period = Period.between(createAt.toLocalDate(),now.toLocalDate());
        int yearAgo = period.getYears();
        int monthAgo = period.getMonths();
        int dayAgo = period.getDays();


        if(yearAgo>0){
            return yearAgo==1 ?Integer.toString(yearAgo).replace("-","")+" year ago":
                    Integer.toString(yearAgo).replace("-","")+" years ago";
        }else if(monthAgo>0){
            return monthAgo==1 ?Integer.toString(monthAgo).replace("-","")+" month ago":
                    Integer.toString(monthAgo).replace("-","")+" months ago";
        }else if(dayAgo>0){
            int weekAgo = (int) Math.floor((double) dayAgo /7) ;
            if(weekAgo<=1) {
                return dayAgo==1 ?Integer.toString(dayAgo).replace("-","")+" day ago":
                        Integer.toString(dayAgo).replace("-","")+" days ago";
            }else {
                return Integer.toString(weekAgo).replace("-","")+" weeks ago";
            }
        }else {
            Duration duration = Duration.between(createAt, now);
            int hourAgo=(int) duration.toHours();
            int minuteAgo=(int)duration.toMinutes();
            if(hourAgo>0){
                return hourAgo==1 ?Integer.toString(hourAgo).replace("-","")+" hour ago":
                        Integer.toString(hourAgo).replace("-","")+" hours ago";
            }
            if(minuteAgo>0){
                return minuteAgo==1 ?Integer.toString(minuteAgo).replace("-","")+" minute ago":
                        Integer.toString(minuteAgo).replace("-","")+" minutes ago";
            }

            return "a moment ago";
        }

    }
}
