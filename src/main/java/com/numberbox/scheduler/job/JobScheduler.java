package com.numberbox.scheduler.job;

import java.io.IOException;

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
	
	// 00시 00분 00초
	@Scheduled(cron = "00 00 00 * * *")
    public void initHwpDownCnt() {
		//사용자 일일 hwp 다운 카운트 초기화
		schedulerService.initHwpDownCnt();
		//만료된 리프레시 토큰 삭제
		schedulerService.deleteByTokenCreateDateLessThan(31);
    }
	
	// 6시간 마다 실행
	@Scheduled(cron = "0 0 0/6 * * *") 
	public void deleteOldFile() throws IOException {
		schedulerService.deleteOldFile();
	}
	
	
	//매년 1월 2일
	@Scheduled(cron = "00 00 00 02 01 *") 
	public void teenagersProfileReset() throws IOException {
		schedulerService.teenagersProfileToEtc();
	}
	
	//매일 03시 00분 00초
	@Scheduled(cron = "00 00 03 * * *")
    public void teenagersProfileToStudent() {
		//사용자 일일 hwp 다운 카운트 초기화
		schedulerService.teenagersProfileToStudent();
    }
	
	// 04시 00분 00초
	@Scheduled(cron = "00 00 04 * * *")
    public void mathDocsDel() {
		//임시 비밀번호 매일 새벽 06시에 새로운 비밀번호로 셋팅
		schedulerService.mathDocsDel();
    }
		
	
}
