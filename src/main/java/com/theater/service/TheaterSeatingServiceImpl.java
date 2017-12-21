package com.theater.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.theater.model.TheaterSeatingLayout;
import com.theater.model.TheaterSeatingRequest;
import com.theater.model.TheaterSeatingSection;

public class TheaterSeatingServiceImpl implements TheaterSeatingService {

    public TheaterSeatingLayout getTheaterLayout(String rawLayout) throws NumberFormatException{
        
        TheaterSeatingLayout theaterLayout = new TheaterSeatingLayout();
        TheaterSeatingSection section;
        List<TheaterSeatingSection> sectionsList = new ArrayList<TheaterSeatingSection>();
        int totalCapacity = 0, value;
        String[] rows = rawLayout.split(System.lineSeparator());
        String[] sections;
        
        for(int i=0 ; i<rows.length ; i++){
            
            sections = rows[i].split(" ");
            
            for(int j=0 ; j<sections.length ; j++){
            
                try{
                
                    value = Integer.valueOf(sections[j]);
                    
                }catch(NumberFormatException nfe){
                    
                    throw new NumberFormatException("'" + sections[j] + "'" + " is invalid section capacity. Please correct it.");
                    
                }
                
                totalCapacity = totalCapacity + value;
                
                section = new TheaterSeatingSection();
                section.setRowNumber(i + 1);
                section.setSectionNumber(j + 1);
                section.setCapacity(value);
                section.setAvailableSeats(value);
                
                sectionsList.add(section);
                
            }

        }
        
        theaterLayout.setTotalCapacity(totalCapacity);
        theaterLayout.setAvailableSeats(totalCapacity);
        theaterLayout.setSections(sectionsList);
        
        return theaterLayout;
        
    }

    public List<TheaterSeatingRequest> getTicketRequests(String ticketRequests) throws NumberFormatException{
        
        List<TheaterSeatingRequest> requestsList = new ArrayList<TheaterSeatingRequest>();
        TheaterSeatingRequest request;
        
        String[] requests = ticketRequests.split(System.lineSeparator());
        
        for(String r : requests){
            
            String[] requestInfo = r.split(" ");
            
            request = new TheaterSeatingRequest();
            
            request.setName(requestInfo[0]);
            
            try{
            
                request.setNoOfTickets(Integer.valueOf(requestInfo[1]));
                
            }catch(NumberFormatException nfe){
                
                throw new NumberFormatException("'" + requestInfo[1] + "'" + " is invalid ticket request. Please correct it.");
            }
            request.setCompleted(false);
            
            requestsList.add(request);
            
        }
        
        return requestsList;
        
    }
    
  
    private int findComplementRequest(List<TheaterSeatingRequest> requests, int complementSeats, int currentRequestIndex){
        
        int requestNo = -1;

        for(int i=currentRequestIndex+1 ; i<requests.size() ; i++){
            
            TheaterSeatingRequest request = requests.get(i);
            
            if(!request.isCompleted() && request.getNoOfTickets() == complementSeats){
                
                requestNo = i;
                break;
                
            }
            
        }
        
        return requestNo;
    }
    
    
    /*
     * Find available seats by sections
     * 
     */
    private int findSectionByAvailableSeats(List<TheaterSeatingSection> sections, int availableSeats){
        
        int i=0;
        TheaterSeatingSection section = new TheaterSeatingSection();
        section.setAvailableSeats(availableSeats);
        
        Collections.sort(sections);
        
        Comparator<TheaterSeatingSection> byAvailableSeats = new Comparator<TheaterSeatingSection>() {
            
            public int compare(TheaterSeatingSection o1, TheaterSeatingSection o2) {
                
                return o1.getAvailableSeats() - o2.getAvailableSeats();
                
            }
        };
        
        int sectionNo = Collections.binarySearch(sections, section, byAvailableSeats);
        

        if(sectionNo > 0){
            
            for(i=sectionNo-1 ; i>=0 ; i--){
                
                TheaterSeatingSection s = sections.get(i);
                
                if(s.getAvailableSeats() != availableSeats) break;
                
            }
            
            sectionNo = i + 1;
            
        }
        
        return sectionNo;
    }
    
    
    public void processTicketRequests(TheaterSeatingLayout layout, List<TheaterSeatingRequest> requests) {
        
        for(int i=0 ; i<requests.size() ; i++){
            
            TheaterSeatingRequest request = requests.get(i);
            if(request.isCompleted())   continue;
            
            //Check to verify if tickets requested were more than capacity.
            if(request.getNoOfTickets() > layout.getAvailableSeats()){
                
                request.setRowNumber(-2);
                request.setSectionNumber(-2);
                continue;
                
            }
            
            List<TheaterSeatingSection> sections = layout.getSections();
            
            for(TheaterSeatingSection section:sections){		
                if(request.getNoOfTickets() == section.getAvailableSeats()){
                    
                    request.setRowNumber(section.getRowNumber());
                    request.setSectionNumber(section.getSectionNumber());
                    section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
                    layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                    request.setCompleted(true);
                    break;
                    
                }else if(request.getNoOfTickets() < section.getAvailableSeats()){
                    
                    int requestNo = findComplementRequest(requests, section.getAvailableSeats() - request.getNoOfTickets(), i);
                    
                    if(requestNo != -1){
                        
                        request.setRowNumber(section.getRowNumber());
                        request.setSectionNumber(section.getSectionNumber());
                        section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
                        layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                        request.setCompleted(true);
                        
                        TheaterSeatingRequest complementRequest = requests.get(requestNo);
                        
                        complementRequest.setRowNumber(section.getRowNumber());
                        complementRequest.setSectionNumber(section.getSectionNumber());
                        section.setAvailableSeats(section.getAvailableSeats() - complementRequest.getNoOfTickets());
                        layout.setAvailableSeats(layout.getAvailableSeats() - complementRequest.getNoOfTickets());
                        complementRequest.setCompleted(true);
                        
                        break;
                        
                    }else{
                        
                        int sectionNo = findSectionByAvailableSeats(sections, request.getNoOfTickets());
                        
                        if(sectionNo >= 0){
                            
                            TheaterSeatingSection perferctSection = sections.get(sectionNo);
                            
                            request.setRowNumber(perferctSection.getRowNumber());
                            request.setSectionNumber(perferctSection.getSectionNumber());
                            perferctSection.setAvailableSeats(perferctSection.getAvailableSeats() - request.getNoOfTickets());
                            layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                            request.setCompleted(true);
                            break;
                            
                        }else{
                            
                            request.setRowNumber(section.getRowNumber());
                            request.setSectionNumber(section.getSectionNumber());
                            section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
                            layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                            request.setCompleted(true);
                            break;
                            
                        }
                        
                    }
                    
                }
                
            }
            
            /*
             * Check to split the party
             */
            if(!request.isCompleted()){
                
                request.setRowNumber(-1);
                request.setSectionNumber(-1);
                
            }
            
        }
        
        System.out.println("Seats Allocation.\n");
        
        for(TheaterSeatingRequest request : requests){
            
            System.out.println(request.getStatus());
            
        }
        
    }

}
