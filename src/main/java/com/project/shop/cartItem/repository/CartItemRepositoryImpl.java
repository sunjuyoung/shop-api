package com.project.shop.cartItem.repository;

import com.project.shop.cart.entity.QCart;
import com.project.shop.cartItem.dto.response.CartItemListDTO;
import com.project.shop.cartItem.dto.response.QCartItemListDTO;
import com.project.shop.cartItem.entity.CartItem;
import com.project.shop.cartItem.entity.QCartItem;
import com.project.shop.global.domain.QImages;
import com.project.shop.product.dto.response.ProductListDTO;
import com.project.shop.product.entity.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.shop.cart.entity.QCart.*;
import static com.project.shop.cartItem.entity.QCartItem.*;
import static com.project.shop.global.domain.QImages.*;
import static com.project.shop.product.entity.QProduct.*;

public class CartItemRepositoryImpl implements CustomCartItemRepository{



    private final JPAQueryFactory queryFactory;

    public CartItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //사용자 아이디를 통해서 해당 사용자의 모든 장바구니 아이템을 조회
    // - 로그인했을때 사용자가 담은 모든 장바구니 아이템 조회한다

    @Override
    public List<CartItemListDTO> getCartItemsById(Long userId) {
     //   JPAQuery<CartItemListDTO> cartItemList = getCartItemList();

        List<CartItemListDTO> fetch = queryFactory.select(
                        Projections.constructor(
                                CartItemListDTO.class,
                                cartItem.id, Expressions.list(images.fileName.max()),
                                product.id,
                                product.name,
                                product.price,
                                cartItem.quantity


                        )
                )
                .from(cartItem)
                .innerJoin(cartItem.cart, cart)
                .innerJoin(cartItem.product, product)
                .leftJoin(product.productImages, images)
                .where(cart.customer.id.eq(userId))
                .groupBy(cart, product, cartItem)
                .fetch();

        // List<CartItemListDTO> collect = fetch.stream().map(CartItemListDTO::new).collect(Collectors.toList());



        //cartItemList.where(cart.customer.id.eq(id));
       // List<CartItemListDTO> fetch = cartItemList.fetch();
        return fetch;
    }

    //특정 장바구니아이디 만으로 해당 장바구니의 모든 아이템을 조회
    // - 특정한 장바구니아이템을 삭제한 후에 해당 장바구니 아이템을 모두 조회할떄
    @Override
    public List<CartItemListDTO> getCartItemsByCartId(Long cartId) {

//        JPAQuery<CartItemListDTO> cartItemList = getCartItemList();
//        cartItemList.where(cart.id.eq(cartId));
//        List<CartItemListDTO> fetch = cartItemList.fetch();


        List<CartItemListDTO> fetch = queryFactory.select(
                        Projections.constructor(
                                CartItemListDTO.class,
                                cartItem.id, Expressions.list(images.fileName.max()),
                                product.id,
                                product.name,
                                product.price,
                                cartItem.quantity


                        )
                )
                .from(cartItem)
                .innerJoin(cartItem.cart, cart)
                .innerJoin(cartItem.product, product)
                .leftJoin(product.productImages, images)
                .where(cart.id.eq(cartId))
                .groupBy(cart, product, cartItem)
                .fetch();

        return fetch;
    }

    //사용자 아이디와 상품번호로 해당 장바구니 아이템을 알아낸다
    // - 새로운 상품을 장바구니에 담을때 기존 아이템인지 확인
    @Override
    public CartItem getCartItemByProductAndEmail(String email, Long product_id) {

        CartItem cartItem1 = queryFactory
                .select(cartItem)
                .from(cartItem)
                .innerJoin(cartItem.cart, cart)
                .where(cart.customer.email.eq(email), cartItem.product.id.eq(product_id))
                .fetchOne();
        return cartItem1;
    }

    //장바구니 아이템이 속한 장바구니 번호를 알아낸다
    // -해당 아이템을 삭제한 후 해당 아이템이 속해있는 장바구니의 모든 아이템을 알아내기위해
    @Override
    public Long getCartFromItem(Long cartItemId) {
        Long cartId = queryFactory
                .select(cart.id)
                .from(cart)
                .innerJoin(cartItem).on(cartItem.cart.eq(cart))
                .where(cartItem.id.eq(cartItemId))
                .fetchOne();
        ;
        return cartId;
    }

    @Override
    public List<CartItemListDTO> getCartItemsByEmail(String email) {


        List<CartItemListDTO> fetch = queryFactory.select(
                        Projections.constructor(
                                CartItemListDTO.class,
                                cartItem.id, Expressions.list(images.fileName.max()),
                                product.id,
                                product.name,
                                product.price,
                                cartItem.quantity


                        )
                )
                .from(cartItem)
                .innerJoin(cartItem.cart, cart)
                .innerJoin(cartItem.product, product)
                .leftJoin(product.productImages, images)
                .where(cart.customer.email.eq(email))
                .groupBy(cart, product, cartItem)
                .fetch();
        return fetch;
    }

    private  JPAQuery<CartItemListDTO> getCartItemList() {
         return queryFactory.select(new QCartItemListDTO(
                                cartItem.id,
                                product.id,
                                product.name,
                                product.price,
                                product.quantity,
                                images.fileUrl

                        )


                )
                .from(cartItem)
                .innerJoin(cartItem.cart, cart)
                .leftJoin(cartItem.product, product)
                .leftJoin(product.productImages, images);
    }


}
