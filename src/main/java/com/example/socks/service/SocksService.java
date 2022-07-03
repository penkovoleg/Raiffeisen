package com.example.socks.service;

import com.example.socks.model.Socks;
import com.example.socks.model.Operation;
import com.example.socks.repository.SocksRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SocksService {

    private final SocksRepository socksRepository;

    public ResponseEntity<List<Socks>> getAllSocks() throws NoSuchElementException {
        List<Socks> socks = socksRepository.findAll();
        if (socks.isEmpty()) {
            throw new NoSuchElementException("Носков на складе не найдено!");
        }
        return ResponseEntity.ok(socks);
    }

    public ResponseEntity<String> addSocks(Socks socksInCome) {
        Socks socksInStore = socksRepository
                .findByColorAndCottonPart(socksInCome.getColor(), socksInCome.getCottonPart());
        if (socksInStore != null) {
            socksInStore.setQuantity(socksInStore.getQuantity() + socksInCome.getQuantity());
            socksRepository.save(socksInStore);
        } else {
            socksRepository.save(socksInCome);
        }
        return ResponseEntity.ok("Удалось добавить приход!");
    }

    public ResponseEntity<String> removeSocks(final Socks socksIncome)
            throws NoSuchElementException {
        Socks socksInStore = socksRepository
                .findByColorAndCottonPart(socksIncome.getColor(), socksIncome.getCottonPart());
        if (socksInStore != null) {
            socksInStore.setQuantity(socksInStore.getQuantity() - socksIncome.getQuantity());
            if (socksInStore.getQuantity() > 0) {
                socksRepository.save(socksInStore);
            } else {
                socksRepository.delete(socksInStore);
            }
        } else {
            throw new NoSuchElementException("Носков по данному запросу не найдено!");
        }
        return new ResponseEntity<>("Отпуск носков со склада выполнен!", HttpStatus.OK);
    }

    public ResponseEntity<String> getSocksOnRequest(String color, String operation, int cottonPart)
            throws IllegalArgumentException, NoSuchElementException {
        List<Socks> socksInStore = new ArrayList<>();
        Operation operations = Operation.enumFromString(operation);
        if (operations.equals(Operation.MORE_THAN)) {
            socksInStore = socksRepository.findByColorAndCottonPartGreaterThan(color, cottonPart);
        } else if (operations.equals(Operation.LESS_THAN)) {
            socksInStore = socksRepository.findByColorAndCottonPartLessThan(color, cottonPart);
        } else if (operations.equals(Operation.EQUAL)) {
            socksInStore = socksRepository.findByColorAndCottonPartEquals(color, cottonPart);
        }
        if (socksInStore.isEmpty()) {
            throw new NoSuchElementException("Носков по данному запросу не найдено!");
        }
        return ResponseEntity.ok(String.valueOf(socksInStore
                .stream()
                .mapToLong(Socks::getQuantity)
                .sum()
        ));
    }
}
