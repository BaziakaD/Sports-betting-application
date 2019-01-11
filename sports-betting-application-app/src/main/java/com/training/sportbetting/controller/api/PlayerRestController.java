package com.training.sportbetting.controller.api;

import com.training.sportbetting.controller.api.dto.PlayerDTO;
import com.training.sportbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/api/player")
public class PlayerRestController {

    private UserService userService;

    @Autowired
    public PlayerRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PlayerDTO> getPlayerInfoByEmail(@RequestParam String email) {
        var player = userService.findPlayerByEmail(email);
        return ResponseEntity.ok().body(PlayerDTO.dtoFromPlayer(player));
    }
}
