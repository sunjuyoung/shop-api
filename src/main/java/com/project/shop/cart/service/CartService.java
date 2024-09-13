package com.project.shop.cart.service;

import com.project.shop.cart.entity.Cart;
import com.project.shop.cartItem.dto.request.CartItemDTO;
import com.project.shop.cartItem.dto.response.CartItemListDTO;
import com.project.shop.cartItem.entity.CartItem;
import com.project.shop.cartItem.repository.CartItemRepository;
import com.project.shop.cartItem.repository.CartRepository;
import com.project.shop.customer.entity.Customer;
import com.project.shop.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    //장바구니 아이템 추가 , 변경
    public List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO){

        String email = cartItemDTO.getEmail();
        Long userId = cartItemDTO.getCustomerId();
        int qty = cartItemDTO.getQuantity();
        Long cartItemId = cartItemDTO.getCartItemId();
        Long productId = cartItemDTO.getProductId();

        if(cartItemId != null){
            CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
            cartItem.changeQty(qty);
            return getCartItemsByUserId(userId);

        }

        Cart cart = getCart(userId);
        CartItem cartItem  = null;

        cartItem = cartItemRepository.getCartItemByProductAndEmail(email,productId);
        if(cartItem == null){
            Product product = Product.builder().id(productId).build();
            cartItem = CartItem.builder().product(product).quantity(qty).cart(cart).build();
        }else {
            cartItem.changeQty(qty);
        }
        cartItemRepository.save(cartItem);

        return getCartItemsByUserId(userId);


    }

    private Cart getCart(Long userId){
        Cart cart = null;
        Optional<Cart> result = cartRepository.getCartByCustomer(userId);
        if(result.isEmpty()){
            Customer customer = Customer.builder().id(userId).build();
            Cart cart1 = Cart.builder().customer(customer).build();
            cart = cartRepository.save(cart1);
        }else {
            cart = result.get();
        }
        return cart;
    }

    //장바구니 아이템 목록
    public List<CartItemListDTO> getCartItemsByUserId(Long userId){
        return cartItemRepository.getCartItemsById(userId);
    }

    //아이템 삭제
    public List<CartItemListDTO> remove(Long cartItemId){
        Long cartId = cartItemRepository.getCartFromItem(cartItemId);
        cartItemRepository.deleteById(cartItemId);
        return cartItemRepository.getCartItemsByCartId(cartId);
    }

    public List<CartItemListDTO> getCartItemsByEmail(String email) {
        return cartItemRepository.getCartItemsByEmail(email);
    }
}
