package com.petproject.youtubeclone.utils;

public class YoutubeUtil {

    public static String subTitle(String title,int numSub) {
        String[] arrTitle = title.split(" ");
        StringBuilder newTitle= new StringBuilder();
        int newLength=0;
        for(int i=0;i<arrTitle.length;i++){
            newLength+=arrTitle[i].length();
            if(i!=arrTitle.length-1 && arrTitle[i+1].length()+newLength>numSub-3) {
                newTitle.append("...");
                break;
            }
            newTitle.append(" ").append(arrTitle[i]);
        }
        return newTitle.toString();
    }
}
