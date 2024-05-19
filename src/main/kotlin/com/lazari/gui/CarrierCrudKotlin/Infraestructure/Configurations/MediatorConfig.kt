package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Configurations

import io.github.zhaord.jmediatr.config.JMediatRAutoConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MediatorConfig {
    @Bean
    fun mediator(): JMediatRAutoConfig{
        return JMediatRAutoConfig()
    }
}