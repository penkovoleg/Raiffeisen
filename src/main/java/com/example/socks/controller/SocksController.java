package com.example.socks.controller;

import com.example.socks.model.Socks;
import com.example.socks.service.SocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socks")
public class SocksController {

    private final SocksService socksService;

    @Autowired
    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Socks>> getAllSocks() {
        return socksService.getAllSocks();
    }

    @GetMapping
    public ResponseEntity<String> getSocks(@RequestParam("color") String color,
                                           @RequestParam("operation") String operation,
                                           @RequestParam("cottonPart") int cottonPart) {
        if (color == null
                || (!operation.equals("moreThan")
                && !operation.equals("lessThan")
                && !operation.equals("equal"))
                || cottonPart < 0
                || cottonPart > 100) {
            return new ResponseEntity<>("Параметры запроса отсутствуют или имеют некорректный формат!",
                    HttpStatus.BAD_REQUEST);
        }
        return socksService.getSocksOnRequest(color, operation, cottonPart);
    }

    @PostMapping("/income")
    public ResponseEntity<String> addSocks(@RequestBody Socks socks) {
        if (socks.getColor() == null
                || socks.getCottonPart() < 0
                || socks.getCottonPart() > 100
                || socks.getQuantity() <= 0) {
            return new ResponseEntity<>("Параметры запроса отсутствуют или имеют некорректный формат!",
                    HttpStatus.BAD_REQUEST);
        }
        return socksService.addSocks(socks);
    }

    @PostMapping("/outcome")
    public ResponseEntity<String> deleteSocks(@RequestBody Socks socks) {
        if (socks.getColor() == null
                || socks.getCottonPart() < 0
                || socks.getCottonPart() > 100
                || socks.getQuantity() <= 0) {
            return new ResponseEntity<>("Параметры запроса отсутствуют или имеют некорректный формат!",
                    HttpStatus.BAD_REQUEST);
        }
        return socksService.removeSocks(socks);
    }

    private String checkingData(final Socks socks) {
        if (socks.getColor() == null
                || socks.getCottonPart() < 0
                || socks.getCottonPart() > 100
                || socks.getQuantity() <= 0) {
           return "Параметры запроса отсутствуют или имеют некорректный формат!";
        }
        return null;
    }
}
