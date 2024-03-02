package com.springbackendshoppingmall.repository;

import com.springbackendshoppingmall.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByIdIn(List<Integer> ids);

    Item findById(int id);

    Item deleteById(int id);

}
