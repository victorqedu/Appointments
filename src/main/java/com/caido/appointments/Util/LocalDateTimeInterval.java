package com.caido.appointments.Util;

import java.time.LocalDateTime;

public class LocalDateTimeInterval {
    private LocalDateTime start,stop;
    private String description;
    
    public LocalDateTimeInterval(LocalDateTime start , LocalDateTime stop) {
        this.start = start;
        this.stop = stop;
    }
    public LocalDateTimeInterval(LocalDateTime start , LocalDateTime stop, String description) {
        this.start = start;
        this.stop = stop;
        this.description = description;
    }
    
    public LocalDateTimeInterval clone(LocalDateTimeInterval ldti) {
        return new LocalDateTimeInterval(ldti.start, ldti.stop, ldti.description);
    }
    
    public LocalDateTime getStart() {
        return start;
    }
    
    public LocalDateTime getStop() {
        return stop;
    }
    
    public String getDescription() {
        return description;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    public void setStop(LocalDateTime stop) {
        this.stop = stop;
    }    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "LocalDateTimeInterval with start: "+start+" stop: "+stop+" description: "+description;
    }
}

