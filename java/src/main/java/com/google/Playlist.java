package com.google;

import java.util.ArrayList;

public class Playlist {

    private final String name;
    private ArrayList<Video> videos;

    public Playlist(String name) {
        this.name = name;
        videos = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    /**
     *
     * @param video
     * @return trus is video added sucessfully,
     * false otherwise (video already exists)
     */
    public Boolean addVideo(Video video) {
        if(playlistContainsVideo(video)){
            return false;
        }
        videos.add(video);
        return true;
    }

    /**
     * Removes a Video object from the playlist
     * @param video
     */
    public void removeVideo(Video video) {
        videos.remove(video);
    }

    /**
     *
     * @param video
     * @return true if the playlist already contains specified video,
     * false otherwise
     */
    private Boolean playlistContainsVideo(Video video){
        for(Video v: videos){
            if(v.equals(video)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param videoId
     * @return the specified Video in the playlist,
     * null if video is not in the playlist
     */
    public Video getVideo(String videoId) {
        for(Video video: videos){
            if(video.getVideoId().equalsIgnoreCase(videoId)){
                return video;
            }
        }
        return null;
    }
}
