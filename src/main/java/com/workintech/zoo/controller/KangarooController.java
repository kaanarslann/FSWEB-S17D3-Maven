package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    public Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        this.kangaroos = new HashMap<>();
        kangaroos.put(1, new Kangaroo(1, "Jumpy", 150.5, 50.5, "Male", true));
    }

    @GetMapping
    public List<Kangaroo> gelAll() {
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo getKangaroo(@PathVariable("id") int id) {
        if(id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if(!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return this.kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo addKangaroo(@RequestBody Kangaroo kangaroo) {
        if(kangaroo.getId() <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        this.kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroos.get(kangaroo.getId());
    }

    @PutMapping("/{id}")
    public Kangaroo updateKangaroo(@PathVariable("id") int id, @RequestBody Kangaroo newKangaroo) {
        if(id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if(!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        this.kangaroos.replace(id, newKangaroo);
        return this.kangaroos.get(id);
    }

    @DeleteMapping("/{id}")
    public Kangaroo deleteKangaroo(@PathVariable("id") int id) {
        if(id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if(!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }
}
