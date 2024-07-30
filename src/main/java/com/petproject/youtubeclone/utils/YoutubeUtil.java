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

    public static String subDesc(String desc,int numSub) {
        String regex = "<[^>]*>";
        String contentDesc = desc.replaceAll(regex,"").trim( );
        String[] wordDesc = contentDesc.split(" ");
        StringBuilder newDesc= new StringBuilder();
        int newLength=0;
        for(int i=0;i<wordDesc.length;i++){
            newLength+=wordDesc[i].length();
            if(i!=wordDesc.length-1 && wordDesc[i+1].length()+newLength>numSub-3) {
                newDesc.append("...");
                break;
            }
            newDesc.append(" ").append(wordDesc[i]);
        }
        return newDesc.toString();
    }
}
