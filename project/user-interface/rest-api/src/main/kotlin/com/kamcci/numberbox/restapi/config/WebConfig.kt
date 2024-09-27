package com.kamcci.numberbox.restapi.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    @Qualifier("userDetail")
    private val handlerMethodArgumentResolver: HandlerMethodArgumentResolver,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(handlerMethodArgumentResolver)
        super.addArgumentResolvers(resolvers)
    }
}