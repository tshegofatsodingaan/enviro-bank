package com.enviro.envirobankingapp.services.impl;


import org.springframework.scheduling.annotation.Scheduled;



public class SchedulingService {

    @Scheduled(cron = "0/60 * * * * *")
    public void scheduledAmount(){
        System.out.println("here we go!!");
    }
}
