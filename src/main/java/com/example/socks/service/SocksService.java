package com.example.socks.service;

import com.example.socks.model.Socks;
import com.example.socks.repository.SocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocksService {

    private final SocksRepository socksRepository;

    @Autowired
    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    public ResponseEntity<List<Socks>> getAllSocks() {
        List<Socks> socks = socksRepository.findAll();
        return new ResponseEntity<>(socks, HttpStatus.OK);
    }

    public ResponseEntity<String> addSocks(final Socks socksIncome) {
        Socks socksInStore = socksRepository.findByColorAndCottonPart(socksIncome.getColor(),
                socksIncome.getCottonPart());
        if (socksInStore != null) {
            socksInStore.setQuantity(socksInStore.getQuantity() + socksIncome.getQuantity());
            socksRepository.save(socksInStore);
        } else {
            socksRepository.save(socksIncome);
        }
        return new ResponseEntity<>("Удалось добавить приход!", HttpStatus.OK);
    }

    public ResponseEntity<String> removeSocks(final Socks socksIncome) {
        Socks socksInStore = socksRepository.findByColorAndCottonPart(socksIncome.getColor(), socksIncome.getCottonPart());
        if (socksInStore != null) {
            socksInStore.setQuantity(socksInStore.getQuantity() - socksIncome.getQuantity());
            if (socksInStore.getQuantity() > 0) {
                socksRepository.save(socksInStore);
            } else {
                socksRepository.delete(socksInStore);
            }
        } else {
            return new ResponseEntity<>("Данных носков на складе нет!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Отпуск носков со склада выполнен!", HttpStatus.OK);
    }

    public ResponseEntity<String> getSocksOnRequest(final String color,
                                                     final String operation,
                                                     final int cottonPart) {
        List<Socks> socksInStore;
        int count = 0;
        switch (operation) {
            case "moreThan":
                socksInStore = socksRepository.findByColorAndCottonPartGreaterThan(color, cottonPart);
                break;
            case "lessThan":
                socksInStore = socksRepository.findByColorAndCottonPartLessThan(color, cottonPart);
                break;
            case "equal":
                socksInStore = socksRepository.findByColorAndCottonPartEquals(color, cottonPart);
                break;
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (socksInStore == null) {
            return new ResponseEntity<>("Данных носков на складе нет!", HttpStatus.OK);
        } else {
            for (Socks socks : socksInStore) {
                count += socks.getQuantity();
            }
        }
        return new ResponseEntity<>(String.valueOf(count), HttpStatus.OK);
    }
}
