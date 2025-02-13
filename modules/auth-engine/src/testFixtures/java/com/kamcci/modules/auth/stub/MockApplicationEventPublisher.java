package com.kamcci.modules.auth.stub;

import org.springframework.context.ApplicationEventPublisher;

public class MockApplicationEventPublisher implements ApplicationEventPublisher {
    // 실행횟수
    public int executeCnt;

    @Override
    public void publishEvent(Object event) {
        executeCnt++;
    }
}
