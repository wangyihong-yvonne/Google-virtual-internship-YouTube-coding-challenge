package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;

  private Boolean isFlagged;
  private String flagReason;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);

    isFlagged = false;
    flagReason = null;
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  /**
   * Used to flag a video with no given reason
   */
  public void flag(){
    isFlagged = true;
    this.flagReason = "Not supplied";
  }

  /**
   * Used to flag a video with a given reason
   * @param flagReason
   */
  public void flag(String flagReason){
    isFlagged = true;
    this.flagReason = flagReason;
  }

  /**
   * Removes a video's flag and flag reason
   */
  public void clearFlag(){
    isFlagged = false;
    flagReason = null;
  }

  public Boolean isFlagged() {
    return isFlagged;
  }

  public String getFlagReason() {
    return flagReason;
  }

  /**
   * Overrides the toString method to show videos in the format:
   * title (video_id) [tags]
   * Used in the SHOW_ALL_VIDEOS command
   * @return
   */
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(title + " ");
    stringBuilder.append("(" + videoId + ") ");

    if(tags.size() > 0) {
      stringBuilder.append("[");
      for (String tag : tags) {
        stringBuilder.append(tag + " ");
      }
      stringBuilder.deleteCharAt(stringBuilder.length() - 1);
      stringBuilder.append("]");
    }
    else{
      stringBuilder.append("[]");
    }

    if(isFlagged){
      stringBuilder.append(" - FLAGGED (reason: " +
              flagReason + ")");
    }

    return stringBuilder.toString();
  }
}
