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

    private SocksRepository socksRepository;

    @Autowired
    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    public ResponseEntity<List<Socks>> getAllSocks() {
        List<Socks> socks = socksRepository.findAll();
        if (socks == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(socks, HttpStatus.OK);
    }

    public ResponseEntity addSocks(Socks socks) {
        Socks sock = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (sock != null) {
            sock.setQuantity(sock.getQuantity() + socks.getQuantity());
            socksRepository.saveAndFlush(sock);
            return new ResponseEntity<>("Удалось добавить приход", HttpStatus.OK);
        }
        socksRepository.save(socks);
        return new ResponseEntity<>("Удалось добавить приход", HttpStatus.OK);
    }

    public String removeSocks(Socks socks) {
        Socks sock = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (sock != null) {
            sock.setQuantity(sock.getQuantity() - socks.getQuantity());
            if (sock.getQuantity() > 0) {
                socksRepository.saveAndFlush(sock);
                return "-";
            } else socksRepository.delete(sock);
        }
        return "-";
    }

    public int getSocksOnRequest(String color, String operation, int cottonPart) {
        List<Socks> socks = null;
        int count = 0;
        if (operation.equals("moreThan")) {
            socks = socksRepository.findByColorAndCottonPartGreaterThan(color, cottonPart);
        } else if (operation.equals("lessThan")) {
            socks = socksRepository.findByColorAndCottonPartLessThan(color, cottonPart);
        } else if (operation.equals("equal")) {
            socks = socksRepository.findByColorAndCottonPartEquals(color, cottonPart);
        }
        for (Socks socks1 : socks) {
            count += socks1.getQuantity();
        }
        return count;
    }
}
