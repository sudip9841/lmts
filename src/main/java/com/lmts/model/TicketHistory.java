
package com.lmts.model;


public class TicketHistory {
    private int id;
    private String musicName;
    private int quantity;
    private double totalPrice;
    private String date;
    private String time;

    // Constructors

    public TicketHistory() {
    }

    public TicketHistory(int id, String musicName, int quantity, double totalPrice, String date, String time) {
        this.id = id;
        this.musicName = musicName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
        this.time = time;
    }

    // Getter methods

    public int getId() {
        return id;
    }

    public String getMusicName() {
        return musicName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    // Setter methods

    public void setId(int id) {
        this.id = id;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

