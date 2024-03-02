package com.springbackendshoppingmall.controller;

import com.springbackendshoppingmall.entity.Cart;

import com.springbackendshoppingmall.entity.Item;
import com.springbackendshoppingmall.repository.CartRepository;
import com.springbackendshoppingmall.repository.ItemRepository;
import com.springbackendshoppingmall.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartRepository cartRepo;

    private final ItemRepository itemRepo;

    private final JwtService jwtService;

    @Autowired
    public CartController(
            final CartRepository cartRepo,
            final ItemRepository itemRepo,
            final JwtService jwtService
    ) {
        this.cartRepo = cartRepo;
        this.itemRepo = itemRepo;
        this.jwtService = jwtService;
    }

    @GetMapping("/items")
    public ResponseEntity<?> getCartItems(@CookieValue(name = "token", required = false) String token) {
//        System.out.println("token (get): " + token);

        if (jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } else {
            int memberId = jwtService.getId(token);

            List<Cart> carts = cartRepo.findByMemberId(memberId);
            List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList();
            List<Item> cartItems = itemRepo.findByIdIn(itemIds);

            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        }
    }

    @PostMapping("/item/{itemId}")
    public ResponseEntity<?> pushCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {

        if (jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepo.findByMemberIdAndItemId(memberId, itemId);

        if (cart == null) {

            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);
            cartRepo.save(newCart);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/item/deleteAll")
    public ResponseEntity<?> removeAllItem(
            @CookieValue(name = "token", required = false) String token) {
        if (token == null || jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepo.findByMemberId(memberId);
        if (carts != null) {
            cartRepo.deleteAll(carts);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/item/{itemId}/delete")
    public ResponseEntity<?> removeCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(name = "token", required = false) String token
    ) {
        if (token == null || jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);
        Cart cart = cartRepo.findByMemberIdAndItemId(memberId, itemId);
        if (cart != null) {
            cartRepo.delete(cart);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
