package com.ciklum.config;

import com.ciklum.controller.GameController;
import com.ciklum.model.game.ServerMemory;
import com.ciklum.service.GameService;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    
    @Bean
    public GameController getGameController() {
        return new GameController();
    }

    @Bean
    public GameService getGameService() {
        return new GameService();
    }

    @Bean
    public ServerMemory getServerMemory() {
        return new ServerMemory();
    }
}
