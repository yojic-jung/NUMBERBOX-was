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
	@Scheduled(cron = "00 00 06 * * *")
    public void tmpPassChange() {
		//임시 비밀번호 매일 새벽 06시에 새로운 비밀번호로 셋팅
		schedulerService.tmpPassChange();
    }
	
	
	// 05시 00분 00초
	@Scheduled(cron = "00 00 05 * * *")
    public void accountDrop() {
		//탈퇴 요청 회원 탈퇴처리
		schedulerService.dropAccount();
    }
	
}
