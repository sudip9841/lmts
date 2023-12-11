
package com.lmts.model;

public class Ticket {
    private int id;
    private int userId;
    private int showTimeId;
    private int quantity;
    private double totalPrice;

    // Constructors, getters, and setters...

    public Ticket() {
    }

    public Ticket(int userId, int showTimeId, int quantity, double totalPrice) {
        this.userId = userId;
        this.showTimeId = showTimeId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowTimeId() {
        return showTimeId;
    }

    public void setMusicId(int showTimeId) {
        this.showTimeId = showTimeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

