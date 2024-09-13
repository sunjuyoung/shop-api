package com.project.shop.cartItem.repository;

import com.project.shop.cartItem.dto.response.CartItemListDTO;
import com.project.shop.cartItem.entity.CartItem;

import java.util.List;

public interface CustomCartItemRepository {

    public List<CartItemListDTO> getCartItemsById(Long userId);

    public List<CartItemListDTO> getCartItemsByCartId(Long cartId);

    public CartItem getCartItemByProductAndEmail(String email, Long product_id);

    public Long getCartFromItem(Long cartItemId);

    public  List<CartItemListDTO> getCartItemsByEmail(String email);
}
