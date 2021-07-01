package com.google;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private VideoLibrary videoLibrary;

    public Library() {
        videoLibrary = new VideoLibrary();
    }

    /**
     *
     * @param searchTerm
     * @return an ArrayList of Video objects that match the searchTerm
     */
    public ArrayList<Video> searchByTitle(String searchTerm){
        ArrayList<Video> videos = (ArrayList<Video>) videoLibrary.getVideos();
        ArrayList<Video> searchResults = new ArrayList<>();
        for(Video video: videos){
            if((video.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))){
                searchResults.add(video);
            }
        }
        return searchResults;
    }

    /**
     *
     * @param videoTag
     * @return an ArrayList of Video objects that match the searchTerm
     */
    public ArrayList<Video> searchByTag(String videoTag){
        ArrayList<Video> videos = (ArrayList<Video>) videoLibrary.getVideos();
        ArrayList<Video> searchResults = new ArrayList<>();
        Boolean tagsMatch;
        for(Video video: videos){
            tagsMatch = false;
            List<String> tags = video.getTags();
            for(String tag: tags) {
                if(tag.contains(videoTag.toLowerCase())){
                    tagsMatch = true;
                }
            }
            if(tagsMatch){
                searchResults.add(video);
            }
        }
        return searchResults;
    }

    /**
     * Removes all flagged videos from a search list of videos
     * @param videos
     * @return a list of videos, none are flagged
     */
    public ArrayList<Video> removedFlaggedVideos(ArrayList<Video> videos){
        ArrayList<Video> playableVideos = new ArrayList<>();

        for(Video video: videos){
            if(!video.isFlagged()){
                playableVideos.add(video);
            }
        }

        return playableVideos;
    }
}
