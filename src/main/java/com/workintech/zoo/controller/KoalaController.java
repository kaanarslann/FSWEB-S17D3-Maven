package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {
    public Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        this.koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> gelAll() {
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala getKoala(@PathVariable("id") int id) {
        return this.koalas.get(id);
    }

    @PostMapping
    public Koala addKoala(@RequestBody Koala koala) {
        if(koala.getId() <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        this.koalas.put(koala.getId(), koala);
        return koalas.get(koala.getId());
    }

    @PutMapping("/{id}")
    public Koala updateKoala(@PathVariable("id") int id, @RequestBody Koala newKoala) {
        this.koalas.replace(id, newKoala);
        return this.koalas.get(id);
    }

    @DeleteMapping("/{id}")
    public void deleteKoala(@PathVariable("id") int id) {
        this.koalas.remove(id);
    }
}
