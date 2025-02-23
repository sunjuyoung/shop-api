package com.project.shop.comment.data;

import com.project.shop.comment.entity.Comment;
import com.project.shop.customer.entity.Customer;
import com.project.shop.customer.repository.CustomerRepository;
import com.project.shop.product.entity.Product;
import com.project.shop.product.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DataInitializer {


}
