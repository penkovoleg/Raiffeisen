package com.example.socks.controller;

import com.example.socks.model.Socks;
import com.example.socks.service.SocksService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/socks")
public class SocksController {

    private final SocksService socksService;

    @GetMapping("/all")
    public ResponseEntity<List<Socks>> getAllSocks() {
        return socksService.getAllSocks();
    }

    @GetMapping(params = {"color", "operation", "cottonPart"})
    public ResponseEntity<Long> getSocks(@RequestParam("color") @NotBlank String color,
                                         @RequestParam("operation") @NotBlank String operation,
                                         @RequestParam("cottonPart") @NotNull @Min(0) @Max(100) int cottonPart)
            throws MethodArgumentNotValidException {

        return socksService.getSocksOnRequest(color, operation, cottonPart);
    }

    @PostMapping("/income")
    public ResponseEntity<String> addSocks(@RequestBody @Valid Socks socks) {
        return socksService.addSocks(socks);
    }

    @PostMapping("/outcome")
    public ResponseEntity<String> deleteSocks(@RequestBody @Valid Socks socks) {
        return socksService.removeSocks(socks);
    }
}
