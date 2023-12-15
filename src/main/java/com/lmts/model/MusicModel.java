
package com.lmts.model;


public class MusicModel {
    private int music_id;
    private String music_name;
    private String music_description;
    private String date;
    private String time;
    private int available_seat;
    private int show_time_id;
    
    
       public MusicModel(int music_id, String music_name, String music_description,
                 String date, String time, int available_seat, int show_time_id) {
        this.music_id = music_id;
        this.music_name = music_name;
        this.music_description = music_description;
        this.date = date;
        this.time = time;
        this.available_seat = available_seat;
        this.show_time_id = show_time_id;
    }

    // Getter and Setter methods
    public int getMusicId() {
        return music_id;
    }

    public void setMusicId(int music_id) {
        this.music_id = music_id;
    }

    public String getMusicName() {
        return music_name;
    }

    public void setMusicName(String music_name) {
        this.music_name = music_name;
    }

    public String getMusicDescription() {
        return music_description;
    }

    public void setMusicDescription(String music_description) {
        this.music_description = music_description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAvailableSeat() {
        return available_seat;
    }

    public void setAvailableSeat(int available_seat) {
        this.available_seat = available_seat;
    }

    public int getShowTimeId() {
        return show_time_id;
    }

    public void setShowTimeId(int show_time_id) {
        this.show_time_id = show_time_id;
    
}
    
}
