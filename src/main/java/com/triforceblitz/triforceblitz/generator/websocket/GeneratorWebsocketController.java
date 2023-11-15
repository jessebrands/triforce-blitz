package com.triforceblitz.triforceblitz.generator.websocket;

import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.generator.GeneratorService;
import com.triforceblitz.triforceblitz.seeds.converters.StringToSeasonConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class GeneratorWebsocketController {
    private final GeneratorService generatorService;
    private final StringToSeasonConverter converter;

    public GeneratorWebsocketController(GeneratorService generatorService, StringToSeasonConverter converter) {
        this.generatorService = generatorService;
        this.converter = converter;
    }

    @MessageMapping("/generator/generate")
    @SendToUser("/topic/generator/status")
    public GeneratorStatusMessage generateSeed(GenerateSeedMessage message) throws Exception {
        var version = Version.from(message.getVersion());
        var season = converter.convert(message.getSeason());
        var seed = UUID.randomUUID().toString();
        generatorService.generateSeed(version, season, seed);
        return new GeneratorStatusMessage("hello world!!!");
    }
}
