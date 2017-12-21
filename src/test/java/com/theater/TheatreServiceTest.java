package com.theater;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.theater.model.TheaterSeatingLayout;
import com.theater.model.TheaterSeatingRequest;
import com.theater.service.TheaterSeatingServiceImpl;

public class TheatreServiceTest {

	@Test
	public void testTheaterLayout(){
		TheaterSeatingServiceImpl thSeating = new TheaterSeatingServiceImpl();
		TheaterSeatingLayout thLayout = thSeating.getTheaterLayout("6 6\n3 5 5 3\n4 6 6 4\n2 8 8 2\n");
		assertEquals( 68, thLayout.getTotalCapacity());
		assertEquals(68, thLayout.getAvailableSeats());
	}
	
	@Test
	public void testTicketRequests(){
		TheaterSeatingServiceImpl thSeating = new TheaterSeatingServiceImpl();
		 List<TheaterSeatingRequest>  thRequests = thSeating.getTicketRequests("Sam 5\nTom 9\nWilson 100\nJohn 8\n");
		assertEquals( "Sam", thRequests.get(0).getName());
	}
	
	@Test
	public void testProcessTicketRequests(){
		TheaterSeatingServiceImpl thSeating = new TheaterSeatingServiceImpl();
		 List<TheaterSeatingRequest>  thRequests = thSeating.getTicketRequests("Sam 5\nTom 9\nWilson 100\nJohn 8\n");
		TheaterSeatingLayout thLayout = thSeating.getTheaterLayout("6 6\n3 5 5 3\n4 6 6 4\n2 8 8 2\n");
		thSeating.processTicketRequests(thLayout, thRequests);
	}
	
}
