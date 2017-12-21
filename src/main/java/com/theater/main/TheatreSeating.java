package com.theater.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.theater.model.TheaterSeatingLayout;
import com.theater.model.TheaterSeatingRequest;
import com.theater.service.TheaterSeatingService;
import com.theater.service.TheaterSeatingServiceImpl;


public class TheatreSeating {
	private static Logger log = Logger.getLogger(TheatreSeating.class);
	
    public static void main(String[] args) {
        try {
			FileReader freader = new FileReader("input.txt");
			BufferedReader br = new BufferedReader(freader);
			try {
		        seating(br);
				freader.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		}
    }
    
    public static void seating(BufferedReader input) throws IOException{

        String userInput;
        StringBuilder theatreLayout = new StringBuilder();
        StringBuilder ticketRequests = new StringBuilder();
        boolean isTheaterLayoutDone = false;
    	  while((userInput = input.readLine()) != null && !userInput.equals("ok")){
              
              if(userInput.length() == 0){
                  isTheaterLayoutDone = true;
                  continue;
              }
              
              if(!isTheaterLayoutDone){
                  
                  theatreLayout.append(userInput + System.lineSeparator());
                  
              }else{
                  
                  ticketRequests.append(userInput + System.lineSeparator());
                  
              }
              
          }
          
          input.close();
          
          TheaterSeatingService service = new TheaterSeatingServiceImpl();
          
          try{
          
              TheaterSeatingLayout theaterLayout = service.getTheaterLayout(theatreLayout.toString());
              
              List<TheaterSeatingRequest> requests = service.getTicketRequests(ticketRequests.toString());
              
              service.processTicketRequests(theaterLayout, requests);
              
          }catch(NumberFormatException ex){
              
              log.error(ex.getMessage());
              
          }catch(Exception e){
        	 
        	  log.error(e.getMessage());
              
          }
	
    }

}
