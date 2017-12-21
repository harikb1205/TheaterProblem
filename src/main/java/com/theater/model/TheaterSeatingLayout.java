package com.theater.model;

import java.util.List;

public class TheaterSeatingLayout {

    private int totalCapacity;
    private int availableSeats;
    private List<TheaterSeatingSection> sections;

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<TheaterSeatingSection> getSections() {
        return sections;
    }

    public void setSections(List<TheaterSeatingSection> sections) {
        this.sections = sections;
    }
    
}
