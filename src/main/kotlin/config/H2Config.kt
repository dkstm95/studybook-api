package com.seungilahn.config

import org.h2.tools.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class H2Config {

    @Bean
    fun h2TcpServer(): Server {
        return Server.createTcpServer().start()
    }

}