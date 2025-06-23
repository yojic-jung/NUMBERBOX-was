//package com.kamcci.numberbox.infra.orm.jpa.adapter.config
//
//import com.example.mytv.common.MessageTopics
//import com.example.mytv.domain.message.NewVideoMessage
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.redis.connection.RedisConnectionFactory
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.data.redis.listener.ChannelTopic
//import org.springframework.data.redis.listener.RedisMessageListenerContainer
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
//import org.springframework.data.redis.serializer.StringRedisSerializer
//
//@Configuration
//class RedisMessageConfig {
//    @Autowired
//    private val redisConnectionFactory: RedisConnectionFactory? = null
//
//    @Autowired
//    private val objectMapper: ObjectMapper? = null
//
//
//    @Bean
//    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory?): RedisTemplate<String, Any> {
//        val redisTemplate = RedisTemplate<String, Any>()
//        redisTemplate.connectionFactory = redisConnectionFactory
//        redisTemplate.keySerializer = StringRedisSerializer()
//        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer<Any?>(NewVideoMessage::class.java)
//        return redisTemplate
//    }
//
//    @Bean
//    fun redisContainer(): RedisMessageListenerContainer {
//        val container = RedisMessageListenerContainer()
//        container.setConnectionFactory(redisConnectionFactory!!)
//        container.addMessageListener(newVideoListener(), ChannelTopic(MessageTopics.NEW_VIDEO))
//        return container
//    }
//
//    @Bean
//    fun newVideoListener(): MessageListenerAdapter {
//        return MessageListenerAdapter(redisNewVideoMessageListener)
//    }
//}