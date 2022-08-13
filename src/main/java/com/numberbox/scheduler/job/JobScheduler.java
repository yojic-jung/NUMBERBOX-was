package com.numberbox.scheduler.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.numberbox.scheduler.service.SchedulerService;

@Component
public class JobScheduler {
	
	@Autowired
	SchedulerService schedulerService;

	// 06시 00분 00초
	@Scheduled(cron = "00 00 12 * * *")
    public void printDate () {
		schedulerService.tmpPassChange();
    }
	
}
