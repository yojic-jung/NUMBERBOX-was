package com.kamcci.numberbox.infra.orm.jpa.adapter.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RedisToRdbScheduler {
    @Scheduled(cron = "0 0 */2 * * *")
    fun mathLikeBulkInsert() {
        // todo
    }


    @Scheduled(cron = "0 0 */2 * * *")
    fun mathRepoBulkInsert() {
        // todo
    }
}