
package com.lmts.model;


public class TicketHistoryAdmin {
    private int ticketId;
    private String musicName;
    private String ticketType;
    private double totalPrice;
    private String date;
    private String time;

    // Constructor
    public TicketHistoryAdmin(int ticketId, String musicName, String ticketType, double totalPrice, String date, String time) {
        this.ticketId = ticketId;
        this.musicName = musicName;
        this.ticketType = ticketType;
        this.totalPrice = totalPrice;
        this.date = date;
        this.time = time;
    }

    // Getter and setter methods for ticketId
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    // Getter and setter methods for musicName
    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    // Getter and setter methods for ticketType
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    // Getter and setter methods for totalPrice
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Getter and setter methods for date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter and setter methods for time
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // You can also override the toString() method for easy printing
    @Override
    public String toString() {
        return "TicketHistoryAdmin{" +
                "ticketId=" + ticketId +
                ", musicName='" + musicName + '\'' +
                ", ticketType='" + ticketType + '\'' +
                ", totalPrice=" + totalPrice +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
