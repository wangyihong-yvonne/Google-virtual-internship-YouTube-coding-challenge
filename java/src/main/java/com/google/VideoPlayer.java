package com.google;

import java.util.*;

public class VideoPlayer {

    private final VideoLibrary videoLibrary;

    private String videoPlaying;
    private boolean videoPaused;

    private PlayerFunction playerFunction;

    private Library library;

    public VideoPlayer() {
        this.videoLibrary = new VideoLibrary();

        videoPlaying = "";
        videoPaused = false;

        playerFunction = new PlayerFunction();

        library = new Library();
    }

    public void numberOfVideos() {
        System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
    }

    /**
     * This command will list all available videos in the format: “title (video_id) [tags]”.
     * The videos should be shown in lexicographical order by title. If there are no tags available, display empty brackets.
     */
    public void showAllVideos() {
        List<Video> videos = videoLibrary.getVideos();
        if (videos.isEmpty()) {
            System.out.println("No videos available");
        } else {
            System.out.println("Here's a list of all available videos:");
            sortVideosByTitle(videos);
            for (Video video : videos) {
                System.out.println(video);
            }
        }
    }

    /**
     * Sorts a list of Video objects by title
     */
    public void sortVideosByTitle(List<Video> videos) {
        Collections.sort(videos, new Comparator<Video>() {
            @Override
            public int compare(Video o1, Video o2) {
                return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
            }
        });
    }

    /**
     * Play the specified video. If a video is currently playing, display a note that this video will be
     * stopped, even if the same video is already playing. If the video doesn’t exist, display a warning
     * message (and don’t stop the currently playing video).
     *
     * @param videoId the specified video
     */
    public void playVideo(String videoId) {
        Video video = videoLibrary.getVideo(videoId);
        if (video == null) {
            System.out.println("Cannot play video: Video does not exist");
        } else {
            if(video.isFlagged()){
                System.out.println("Cannot play video: Video is currently flagged (reason: " +
                        video.getFlagReason() + ")");
            }
            else {
                if (videoPlaying.length() > 0) {
                    stopVideo();
                }
                videoPlaying = video.getTitle();
                System.out.println("Playing video: " + videoPlaying);
            }
        }
    }

    /**
     * Stop the current playing video.
     * If no video is currently playing, display a warning message
     * “Cannot stop video: No video is currently playing” and do nothing.
     */
    public void stopVideo() {
        if (videoPlaying.length() == 0) {
            System.out.println("Cannot stop video: No video is currently playing");
        } else {
            System.out.println("Stopping video: " + videoPlaying);
            videoPlaying = "";
            videoPaused = false;
        }
    }

    /**
     * Play a random video. If a video is currently playing, display a note that this video will be stopped,
     * even if the same video is already playing.
     * If there are no videos available, print out “No videos available”
     */
    public void playRandomVideo() {
        ArrayList<Video> videos = (ArrayList<Video>) videoLibrary.getVideos();
        ArrayList<Video> playableVideos = new ArrayList<>();

        for(Video video: videos){
            if(!video.isFlagged()){
                playableVideos.add(video);
            }
        }

        if (playableVideos.isEmpty()) {
            System.out.println("No videos available");
        } else {
            Random rand = new Random();
            int randIndex = rand.nextInt(playableVideos.size());
            if (videoPlaying.length() > 0) {
                stopVideo();
            }
            playVideo(playableVideos.get(randIndex).getVideoId());
        }
    }

    /**
     * Pause the current playing video.
     * If a video is already paused, display a warning message and do nothing.
     * Equally, If no video is currently playing, display a warning message and do nothing.
     */
    public void pauseVideo() {
        if (videoPlaying.length() > 0) {
            if (videoPaused) {
                System.out.println("Video already paused: " + videoPlaying);
            } else {
                videoPaused = true;
                System.out.println("Pausing video: " + videoPlaying);
            }
        } else {
            System.out.println("Cannot pause video: No video is currently playing");
        }
    }

    /**
     * Continues a currently paused video.
     * If the currently playing video is not paused, display a warning message and do nothing.
     * If no video is playing at all, also display a warning message and do nothing.
     */
    public void continueVideo() {
        if (videoPlaying.length() > 0) {
            if (videoPaused) {
                videoPaused = false;
                System.out.println("Continuing video: " + videoPlaying);
            } else {
                System.out.println("Cannot continue video: Video is not paused");
            }
        } else {
            System.out.println("Cannot continue video: No video is currently playing");
        }
    }

    /**
     * Displays the title, video_id, video tags and paused status of the video that is currently playing.
     * If no video is currently playing, display a message.
     */
    public void showPlaying() {
        if (videoPlaying.length() > 0) {
            Video video = null;
            List<Video> videos = videoLibrary.getVideos();
            for (Video v : videos) {
                if (v.getTitle().equalsIgnoreCase(videoPlaying)) {
                    video = v;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Currently playing: ");
            stringBuilder.append(video);
            if (videoPaused) {
                stringBuilder.append(" - PAUSED");
            }
            System.out.println(stringBuilder.toString());
        } else {
            System.out.println("No video is currently playing");
        }
    }

    /**
     * Create a new (empty) playlist with a unique name.
     * If a playlist with the same name already exists,
     * display a warning to the user and do nothing
     *
     * @param playlistName
     */
    public void createPlaylist(String playlistName) {
        if (playerFunction.addPlaylist(playlistName)) {
            System.out.println("Successfully created new playlist: " + playlistName);
        } else {
            System.out.println("Cannot create playlist: A playlist with the same name already exists");
        }
    }

    /**
     * Adds the specified video to a playlist.
     * If either the video or the playlist don’t exist, show a warning message.
     * If both don’t exist, display the warning message for the playlist first.
     * The playlist should not allow duplicate videos and display a warning message
     * if a video is already present in the playlist.
     * As the name is not case sensitive, always use the same case in the response as the user
     * entered in their command.
     *
     * @param playlistName
     * @param videoId
     */
    public void addVideoToPlaylist(String playlistName, String videoId) {
        Playlist playlist = playerFunction.getPlaylist(playlistName);
        if(playlist == null){
            System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
        }
        else{
            Video video = videoLibrary.getVideo(videoId);
            if(video == null){
                System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
            }
            else{
                if(video.isFlagged()){
                    System.out.println("Cannot add video to " + playlistName
                            + ": Video is currently flagged (reason: " +
                            video.getFlagReason() + ")");
                }
                else{
                    if(playlist.addVideo(video)){
                        System.out.println("Added video to " + playlistName + ": "
                                + video.getTitle());
                    }
                    else{
                        System.out.println("Cannot add video to " + playlistName
                                + ": Video already added");
                    }
                }
            }
        }
    }

    /**
     * Show all the available playlists (name only). I
     * f the list is empty, display the user friendly message “No playlists exist yet”.
     * The playlists should be shown in lexicographical order by playlist name.
     */
    public void showAllPlaylists() {
        ArrayList<Playlist> playlists = playerFunction.getPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("No playlists exist yet");
        } else {
            System.out.println("Showing all playlists:");
            sortPlaylistsByName(playlists);
            for (Playlist playlist : playlists) {
                System.out.println(playlist.getName());
            }
        }
    }

    /**
     * Sorts a list of Playlist objects by Name
     *
     * @param playlists an ArrayList of Playlist objects
     */
    public void sortPlaylistsByName(ArrayList<Playlist> playlists) {
        Collections.sort(playlists, new Comparator<Playlist>() {
            @Override
            public int compare(Playlist o1, Playlist o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
    }

    /**
     * Show all the videos in the specified playlist in the following format: “title (video_id) [tags]”.
     * If the playlist doesn’t exist, display a warning message.
     * If the playlist is empty, display “No videos here yet” instead of a video list.
     * The videos should be listed in the same order they were added.
     *
     * @param playlistName
     */
    public void showPlaylist(String playlistName) {
        Playlist playlist = playerFunction.getPlaylist(playlistName);
        if (playlist != null) {
            ArrayList<Video> videos = playlist.getVideos();
            System.out.println("Showing playlist: " + playlistName);
            if (videos.isEmpty()) {
                System.out.println("No videos here yet");
            } else {
                for (Video video : videos) {
                    System.out.println(video);
                }
            }
        } else {
            System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
        }
    }

    /**
     * Remove the specified video from the specified playlist.
     * If either does not exist, display a relevant warning message.
     * In all cases, check for the playlist existence first: This means if both don’t exist,
     * you’ll be printing the message that the playlist doesn’t exist.
     * If the video is not in the playlist to begin with, display a warning message and do nothing
     *
     * @param playlistName
     * @param videoId
     */
    public void removeFromPlaylist(String playlistName, String videoId) {
        Playlist playlist = playerFunction.getPlaylist(playlistName);
        if (playlist != null) {
            if (videoLibrary.getVideo(videoId) == null) {
                System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
            } else {
                Video video = playlist.getVideo(videoId);
                if (video != null) {
                    playlist.removeVideo(video);
                    System.out.println("Removed video from " + playlistName + ": " + video.getTitle());
                } else {
                    System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
                }
            }
        } else {
            System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
        }
    }

    /**
     * Removes all the videos from the playlist, but doesn’t delete the playlist itself.
     * If the playlist doesn’t exist, display a warning message.
     *
     * @param playlistName
     */
    public void clearPlaylist(String playlistName) {
        Playlist playlist = playerFunction.getPlaylist(playlistName);
        if (playlist != null) {
            playlist.getVideos().clear();
            System.out.println("Successfully removed all videos from " + playlistName);

        } else {
            System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
        }
    }

    /**
     * Delete the specified playlist.
     * Display a warning if the playlist doesn’t exist
     *
     * @param playlistName
     */
    public void deletePlaylist(String playlistName) {
        Playlist playlist = playerFunction.getPlaylist(playlistName);
        if (playlist != null) {
            playerFunction.deletePlaylist(playlist);
            System.out.println("Deleted playlist: " + playlistName);
        } else {
            System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
        }
    }

    /**
     * Display all videos in the library whose titles contain the specified search term.
     *
     * Display a list in lexicographical order (by title) and ask the user if they’d like to play one of the
     * videos.
     *
     * If there are no search results, display a nice message.
     * @param searchTerm
     */
    public void searchVideos(String searchTerm) {
        ArrayList<Video> videos = (ArrayList<Video>) videoLibrary.getVideos();

        videos = filterByTitle(videos, searchTerm);

        videos= filterByFlagged(videos);

        if(videos.isEmpty()) {
            System.out.println("No search results for " + searchTerm);
        }
        else{
            sortVideosByTitle(videos);
            System.out.println("Here are the results for " + searchTerm + ":");
            StringBuilder stringBuilder = new StringBuilder();;
            for(int i = 0; i < videos.size(); i++){
                stringBuilder.setLength(0);
                stringBuilder.append(i+1);
                stringBuilder.append(") ");
                stringBuilder.append(videos.get(i));
                System.out.println(stringBuilder.toString());
            }
            playFromSearch(videos);
        }
    }

    /**
     * Filter a list of videos based on if they are flagged
     * @param videos
     */
    private ArrayList<Video> filterByFlagged(ArrayList<Video> videos) {
        ArrayList<Video> filteredVideos = new ArrayList<>();

        for(Video video: videos){
            if(!video.isFlagged()){
                filteredVideos.add(video);
            }
        }

        return filteredVideos;
    }

    /**
     * Filter a list of videos based on if their titles match the search term
     * @param videos
     * @param searchTerm
     */
    private ArrayList<Video> filterByTitle(ArrayList<Video> videos, String searchTerm) {
        ArrayList<Video> filteredVideos = new ArrayList<>();

        for(Video video: videos){
            if(video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())){
                filteredVideos.add(video);
            }
        }

        return filteredVideos;
    }

    /**
     * Filter a list of videos based on if their tags match the search term
     *
     * @param videos
     * @param searchTerm
     */
    private ArrayList<Video> filterByTag(ArrayList<Video> videos, String searchTerm) {
        ArrayList<Video> filteredVideos = new ArrayList<>();

        Boolean tagsMatch;
        for (Video video : videos) {
            tagsMatch = false;
            List<String> tags = video.getTags();
            for (String tag : tags) {
                if (tag.toLowerCase().contains(searchTerm.toLowerCase())) {
                    tagsMatch = true;
                }
            }
            if (tagsMatch) {
                filteredVideos.add(video);
            }
        }


        return filteredVideos;
    }

    /**
     * Display all videos in the library whose titles contain the specified search term.
     *
     * Display a list in lexicographical order (by title) and ask the user if they’d like to play one of the
     * videos.
     *
     * If there are no search results, display a nice message.
     * @param videoTag
     */
    public void searchVideosWithTag(String videoTag) {
        ArrayList<Video> videos = (ArrayList<Video>) videoLibrary.getVideos();

        videos = filterByTag(videos, videoTag);

        videos= filterByFlagged(videos);

        if(videos.isEmpty() || !videoTag.contains("#")) {
            System.out.println("No search results for " + videoTag);
        }
        else{
            sortVideosByTitle(videos);
            System.out.println("Here are the results for " + videoTag + ":");
            StringBuilder stringBuilder = new StringBuilder();;
            for(int i = 0; i < videos.size(); i++){
                stringBuilder.setLength(0);
                stringBuilder.append(i+1);
                stringBuilder.append(") ");
                stringBuilder.append(videos.get(i));
                System.out.println(stringBuilder.toString());
            }
            playFromSearch(videos);
        }
    }

    /**
     * Gives the user a choice to play a video from a search result
     *
     * Read in the answer from the standard input, then play that video,
     * otherwise assume the answer is no and do nothing.
     * @param videos
     */
    public void playFromSearch(ArrayList<Video> videos){
        System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
        System.out.println("If your answer is not a valid number, we will assume it's a no.");
        ArrayList<Integer> validChoices = new ArrayList<>();
        for(int i = 0; i < videos.size(); i++) {
            validChoices.add(i + 1);
        }
        Scanner scanner = new Scanner(System.in);
        Integer videoNumber = null;
        try {
            videoNumber = Integer.valueOf(scanner.nextLine());
        } catch (NumberFormatException e) {
            return;
        }
        if(validChoices.contains(videoNumber)){
            Video chosenVideo = videos.get(videoNumber - 1);
            playVideo(chosenVideo.getVideoId());
        }
        else{
            //System.out.println("Nope!");
        }
    }

    /**
     * Used to flag a video when given no flag reason
     * @param videoId
     */
    public void flagVideo(String videoId) {
        Video video = videoLibrary.getVideo(videoId);
        if(video == null){
            System.out.println("Cannot flag video: Video does not exist");
        }
        else {
            if (video.isFlagged()) {
                System.out.println("Cannot flag video: Video is already flagged");
            } else {
                if(videoPlaying.equalsIgnoreCase(video.getTitle())) {
                    stopVideo();
                }
                video.flag();
                System.out.println("Successfully flagged video: " + video.getTitle() + " (reason: " + video.getFlagReason() + ")");
            }
        }
    }

    /**
     * Used to flag a video with a given flag reason
     * @param videoId
     * @param reason
     */
    public void flagVideo(String videoId, String reason) {
        Video video = videoLibrary.getVideo(videoId);
        if(video == null){
            System.out.println("Cannot flag video: Video does not exist");
        }
        else {
                if (video.isFlagged()) {
                    System.out.println("Cannot flag video: Video is already flagged");
                } else {
                    if(videoPlaying.equalsIgnoreCase(video.getTitle())) {
                        stopVideo();
                    }
                    video.flag(reason);
                    System.out.println("Successfully flagged video: " + video.getTitle() + " (reason: " + video.getFlagReason() + ")");
                }
        }
    }

    /**
     * Attempts to allow (un-flag) a video.
     * If a video doesn’t exist or is not currently flagged, display a warning message.
     * @param videoId
     */
    public void allowVideo(String videoId) {
        Video video = videoLibrary.getVideo(videoId);
        if(video == null){
            System.out.println("Cannot remove flag from video: Video does not exist");
        }
        else{
            if(video.isFlagged()){
                video.clearFlag();
                System.out.println("Successfully removed flag from video: "
                        + video.getTitle());
            }
            else{
                System.out.println("Cannot remove flag from video: Video is not flagged");
            }
        }
    }
}