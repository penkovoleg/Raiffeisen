package com.example.socks.repository;

import com.example.socks.model.Socks;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {

    Socks findByColorAndCottonPart(String color, int cottonPart);

    List<Socks> findByColorAndCottonPartGreaterThan(String color, int cottonPart);

    List<Socks> findByColorAndCottonPartLessThan(String color, int cottonPart);

    List<Socks> findByColorAndCottonPartEquals(String color, int cottonPart);
}
