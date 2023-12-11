
package com.lmts.model;

public class Association {
    private int id;
    private int ticketId;
    private int categoryId;

    // Constructors, getters, and setters...

    public Association() {
    }

    public Association(int ticketId, int categoryId) {
        this.ticketId = ticketId;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}

