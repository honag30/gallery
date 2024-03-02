package com.springbackendshoppingmall.controller;

import com.springbackendshoppingmall.entity.Item;
import com.springbackendshoppingmall.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {

    private final ItemRepository itemRepo;

    @Autowired
    public ItemController(final ItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    @GetMapping("/api/items")
    public List<Item> getItems() {
        return itemRepo.findAll();
    }

    @PostMapping("/api/add-item")
    public ResponseEntity<?> addItem(@RequestBody Item item) {
        if (item != null) {
            itemRepo.save(item);
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping("/api/remove-item/{id}")
    public ResponseEntity<?> removeItem(@PathVariable("id") int id) {
        Item item = itemRepo.findById(id);
        if (item != null) {
            return new ResponseEntity<>(itemRepo.deleteById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/update-item/{id}")
    public ResponseEntity<?> updateItem(@PathVariable int id) {
        Item item = itemRepo.findById(id);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/save-item/{id}")
    public ResponseEntity<?> saveItem(@PathVariable int id, @RequestBody Item exItem) {
        Item item = itemRepo.findById(id);
        if (item != null && exItem != null) {

            item.setName(exItem.getName());
            item.setImg_path(exItem.getImg_path());
            item.setPrice(exItem.getPrice());
            item.setDiscount_per(exItem.getDiscount_per());

            itemRepo.save(item);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

}
