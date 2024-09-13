package com.project.shop.cart.controller;

import com.project.shop.cart.service.CartService;
import com.project.shop.cartItem.dto.request.CartItemDTO;
import com.project.shop.cartItem.dto.response.CartItemListDTO;
import com.project.shop.cartItem.entity.CartItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

   // @PreAuthorize("#cartItemDTO.email == authentication.name")
    @PostMapping("/change")
    public ResponseEntity<List<CartItemListDTO>> changeCart(@RequestBody CartItemDTO cartItemDTO){
        if(cartItemDTO.getQuantity() <= 0){
            List<CartItemListDTO> cartItemListDTOList = cartService.remove(cartItemDTO.getCartItemId());
            return ResponseEntity.ok(cartItemListDTOList);
        }

        List<CartItemListDTO> cartItemListDTOS = cartService.addOrModify(cartItemDTO);

        return ResponseEntity.ok(cartItemListDTOS);
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemListDTO>> getCartItems(Principal principal){
        String email = principal.getName();

        List<CartItemListDTO> cartItemListDTOS =  cartService.getCartItemsByEmail(email);
        return ResponseEntity.ok(cartItemListDTOS);
    }

    @DeleteMapping("/{cartItemId}")
    public  ResponseEntity<List<CartItemListDTO>> removeCartItem(@PathVariable("cartItemId")Long cartItemId){
        List<CartItemListDTO> cartItemListDTOS = cartService.remove(cartItemId);
        return ResponseEntity.ok(cartItemListDTOS);
    }

}
