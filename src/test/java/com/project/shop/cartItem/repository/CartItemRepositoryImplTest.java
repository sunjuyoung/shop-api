package com.project.shop.cartItem.repository;

import com.project.shop.cart.entity.Cart;
import com.project.shop.cartItem.dto.response.CartItemListDTO;
import com.project.shop.cartItem.entity.CartItem;
import com.project.shop.customer.entity.Customer;
import com.project.shop.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CartItemRepositoryImplTest {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartRepository cartRepository;


    @Test
    public void test(){
        cartItemRepository.getCartItemsById(1L);
    }


    @Test
    public void test1(){
        cartItemRepository.getCartItemsByCartId(1L);
    }


    @Test
    public void test2(){
        cartItemRepository.getCartItemByProductAndEmail("test@test.com",3L);
    }

    @Transactional
    @Commit
    @DisplayName("장바구니아이템추가")
    @Test
    public void testSave(){

        //현재 사용자의 장바구니에 해당 상품이 있는지 확인
        //새로운 장바구니아이템생성 or 존재한다면 수량만 변경,
        //장바구니 자체도 없을 수 있기때문에 장바구니 체크


        String email = "test@test.com";
        Long userId = 1L;
        Long productId = 5L;
        int quantity = 2;


        CartItem cartItem = cartItemRepository.getCartItemByProductAndEmail(email, productId);
        if(cartItem != null){
            log.info("cartItem is  exist!!!!!!!!!!");
            cartItem.changeQty(quantity);
            cartItemRepository.save(cartItem);

            return;
        }


        Optional<Cart> cartByCustomer = cartRepository.getCartByCustomer(userId);

        Cart cart =null;
        if(cartByCustomer.isEmpty()){
            log.info("cart is not exist!!!!!!!!!!!!!");
            Customer cus = Customer.builder().id(userId).email(email).build();
            Cart newCart = Cart.builder().customer(cus).build();
            cartRepository.save(newCart);
        }else{
            cart = cartByCustomer.get();
        }


        Product product = Product.builder().id(productId).build();
        CartItem cartItem1 = CartItem.builder().cart(cart).product(product).quantity(quantity).build();
        cartItemRepository.save(cartItem1);
    }

    @Test
    @DisplayName("장바구니 아이템수정")
    public void test3(){
        //장바구니 화면에서 수량만 조정하는 경우

        Long cartItemId = 2L;
        int qty = 3;

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
        cartItem.changeQty(qty);
        cartItemRepository.save(cartItem);
    }

    @Test
    public void deleteAndGet(){
        //삭제 후 모든 장바구니아이템 목록 반환
        Long cartItemId = 2L;

        //장바구니 아이디 확보
        Long cartId = cartItemRepository.getCartFromItem(cartItemId);

       // cartItemRepository.deleteById(cartItemId);

        List<CartItemListDTO> cartItemsByCartId = cartItemRepository.getCartItemsByCartId(cartId);

        for(CartItemListDTO dto : cartItemsByCartId){
            log.info(dto.getProductName());
        }
    }

    @Test
    public void listTest(){
        Long userId = 1L;

        List<CartItemListDTO> cartItemsById = cartItemRepository.getCartItemsById(userId);

        for(CartItemListDTO dto : cartItemsById){
            log.info(dto.getProductName());
        }

    }
}