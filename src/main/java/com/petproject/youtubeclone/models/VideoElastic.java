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
    @Transient
    public String getTimeAgo() {
        LocalDateTime now= LocalDateTime.now();
        int second = createAt.getSecond();
        int minute = createAt.getMinute();
        int hour = createAt.getHour();
        int day = createAt.getDayOfMonth();
        int month = createAt.getMonthValue();
        int year = createAt.getYear();
        if(year!=now.getYear()){
            int yearAgo = year-now.getYear();
            return yearAgo==1 ?Integer.toString(yearAgo).replace("-","")+" year ago":
                    Integer.toString(yearAgo).replace("-","")+" years ago";
        }else if(month!=now.getMonthValue()){
            int monthAgo = month-now.getMonthValue();
            return monthAgo==1 ?Integer.toString(monthAgo).replace("-","")+" month ago":
                    Integer.toString(monthAgo).replace("-","")+" months ago";
        }else if(day!=now.getDayOfMonth()){
            int dayAgo = day-now.getDayOfMonth();
            return dayAgo==1 ?Integer.toString(dayAgo).replace("-","")+" day ago":
                    Integer.toString(dayAgo).replace("-","")+" days ago";
        }else if(hour!=now.getHour()){
            int hourAgo = hour-now.getHour();
            return hourAgo==1 ?Integer.toString(hourAgo).replace("-","")+" hour ago":
                    Integer.toString(hourAgo).replace("-","")+" hours ago";
        }else if(minute!=now.getMinute()){
            int minuteAgo = minute-now.getMinute();
            return minuteAgo==1 ?Integer.toString(minuteAgo).replace("-","")+" minute ago":
                    Integer.toString(minuteAgo).replace("-","")+" minutes ago";
        }else
            return "a moment ago";


    }
}
