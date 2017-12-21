package com.theater.service;

import java.util.List;

import com.theater.model.TheaterSeatingLayout;
import com.theater.model.TheaterSeatingRequest;

public interface TheaterSeatingService {
    
    TheaterSeatingLayout getTheaterLayout(String rawLayout);
    
    List<TheaterSeatingRequest> getTicketRequests(String ticketRequests);
    
    void processTicketRequests(TheaterSeatingLayout layout, List<TheaterSeatingRequest> requests);

}
