package com.springbackendshoppingmall.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "cart_item")
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class CartItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int cart_id;

    @Column
    private int item_id;

    @Column
    private int quantity;
}
