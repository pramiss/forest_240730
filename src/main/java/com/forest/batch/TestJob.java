package com.forest.batch;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// @EnableScheduling -- 스케줄링 활성화
@Slf4j
@Component
public class TestJob {

	// @Scheduled(cron = "0 */1 * * * *") -- 스케줄링 단위
	public void task() { // Job의 하위개념
		log.info("###$$$ task batch 수행!!!");
	}
}
