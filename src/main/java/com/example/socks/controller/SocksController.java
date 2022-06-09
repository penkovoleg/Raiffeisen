package com.example.socks.controller;

import com.example.socks.model.Socks;
import com.example.socks.repository.SocksRepository;
import com.example.socks.service.SocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socks")
public class SocksController {

    private SocksService socksService;

    @Autowired
    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Socks>> getAllSocks() {
        return socksService.getAllSocks();
    }

    @GetMapping
    public int getSocks(@RequestParam("color") String color,
                           @RequestParam("operation") String operation,
                           @RequestParam("cottonPart") int cottonPart) {
        int count = 0;
        if (color == null && cottonPart >= 0 || cottonPart <= 100) {
            count = socksService.getSocksOnRequest(color, operation, cottonPart);
        }
            return count;
    }

    @PostMapping("/income")
    public ResponseEntity addSocks(@RequestBody Socks socks) {
        if (socks.getColor() == null
                || socks.getCottonPart() < 0
                || socks.getCottonPart() > 100
                || socks.getQuantity() < 0) {
            return new ResponseEntity<>("Параметры запроса отсутствуют или имеют некорректный формат!",
                    HttpStatus.BAD_REQUEST);
        }
        return socksService.addSocks(socks);
    }

    @PostMapping("/outcome")
    public String deleteSocks(@RequestBody Socks socks) {
        if (socks.getColor() == null
                || socks.getCottonPart() < 0
                || socks.getCottonPart() > 100
                || socks.getQuantity() < 0) {
            return "Error added socks - bad request!";
        }
        return socksService.removeSocks(socks);
    }
}
