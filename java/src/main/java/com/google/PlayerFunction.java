package com.google;

import java.util.ArrayList;

public class PlayerFunction {
    private ArrayList<Playlist> playlists;

    public PlayerFunction() {
        playlists = new ArrayList<>();
    }

    /**
     *
     * @param playlistName
     * @return true if playlist successfully added, false otherwise
     */
    public Boolean addPlaylist(String playlistName){
        if(playlistNameAvailable(playlistName)){
            playlists.add(new Playlist(playlistName));
            return true;
        }
        return false;
    }

    /**
     * @param playlistName
     * @return true if playlist name is available (not in use),
     * false otherwise
     */
    private Boolean playlistNameAvailable(String playlistName) {
        for(Playlist playlist: playlists){
            if(playlist.getName().equalsIgnoreCase(playlistName)){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param playlistName
     * @return the specified Playlist object if found, null otherwise
     */
    public Playlist getPlaylist(String playlistName) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equalsIgnoreCase(playlistName)) {
                return playlist;
            }
        }
        return null;
    }

    /**
     * Delete a playlist
     * @param playlist
     */
    public void deletePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
}
