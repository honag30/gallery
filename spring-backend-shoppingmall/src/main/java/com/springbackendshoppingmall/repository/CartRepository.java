package com.springbackendshoppingmall.repository;

import com.springbackendshoppingmall.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByMemberId(int memberId);

    Cart findByMemberIdAndItemId(int memberId, int itemId);


}
