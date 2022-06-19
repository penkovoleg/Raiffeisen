package com.example.socks.service;

import com.example.socks.model.Socks;
import com.example.socks.enam.Operations;
import com.example.socks.repository.SocksRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    public ResponseEntity<String> addSocks(Socks socksIncome) {
        Socks socksInStore = socksRepository
                .findByColorAndCottonPart(socksIncome.getColor(), socksIncome.getCottonPart());
        if (socksInStore != null) {
            socksIncome.setQuantity(socksInStore.getQuantity() + socksIncome.getQuantity());
        }
        socksRepository.save(socksIncome);
        return ResponseEntity.ok("Удалось добавить приход!");
    }

    public ResponseEntity<String> removeSocks(final Socks socksIncome) throws NoSuchElementException {
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

    public ResponseEntity<Long> getSocksOnRequest(String color, String operation, final int cottonPart)
            throws NoSuchElementException, MethodArgumentNotValidException {
        List<Socks> socksInStore;
        Operations operations = Operations.enumFromString(operation);
        switch (operations) {
            case MORE_THAN:
                socksInStore = socksRepository.findByColorAndCottonPartGreaterThan(color, cottonPart);
                break;
            case LESS_THAN:
                socksInStore = socksRepository.findByColorAndCottonPartLessThan(color, cottonPart);
                break;
            case EQUAL:
                socksInStore = socksRepository.findByColorAndCottonPartEquals(color, cottonPart);
                break;
            default:
                throw new MethodArgumentNotValidException(null, null);
        }
        if (socksInStore.isEmpty()) {
            throw new NoSuchElementException("Носков по данному запросу не найдено!");
        }
        return ResponseEntity.ok(socksInStore
                .stream()
                .mapToLong(Socks::getQuantity)
                .sum());
    }
}
