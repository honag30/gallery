package com.springbackendshoppingmall.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "items")
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String img_path;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int discount_per;
}
