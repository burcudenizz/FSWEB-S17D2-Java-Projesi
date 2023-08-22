package com.workintech.s17g2.mapping;

import com.workintech.s17g2.model.Developer;

public class DeveloperResponse {

    private Developer developer;
    private String message;
    private int status;

    public DeveloperResponse(Developer developer, String message, int status) {
        this.developer = developer;
        this.message = message;
        this.status = status;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}

//sadece message,status ve Developerdan oluşan bir class tanımlanır.
