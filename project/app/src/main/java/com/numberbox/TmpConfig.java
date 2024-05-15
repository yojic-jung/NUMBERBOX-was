package com.numberbox;

import com.querydsl.core.annotations.Config;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Config
@EnableJpaRepositories(basePackages = {"com.numberbox"})
public class TmpConfig {
}
