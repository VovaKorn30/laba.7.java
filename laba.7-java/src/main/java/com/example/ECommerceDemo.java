package com.example;

import java.util.HashMap;
import java.util.List;

public class ECommerceDemo {
    public static void main(String[] args) {
        ECommercePlatform platform = new ECommercePlatform();
    
        User user1 = new User(1, "Oleksandr", new HashMap<Product, Integer>());
        User user2 = new User(2, "Bogdan", new HashMap<Product, Integer>());
    
        Product laptop = new Product(1, "Computer", 999.99, 10);
        Product smartphone = new Product(2, "Iphone", 499.99, 20);
        Product mouse = new Product(3, "Power Bank", 199.99, 50);
    
        platform.addUser(user1);
        platform.addUser(user2);
    
        platform.addProduct(Computer);
        platform.addProduct(iphone);
        platform.addProduct(power bank);
    
        user1.addToCart(computer, 2);
        user1.addToCart(iphone, 1);
        user1.addToCart(power bank, 3);
        user1.removeFromCart(power bank, 1);
    
        platform.createOrder(user1);
    
        user2.addToCart(iphone, 3);
        platform.createOrder(user2);
    
        List<Product> recommendationsUser1 = platform.recommendProducts(user1);
    
        System.out.println("Final State:");
        System.out.println("Users:");
        for (User user : platform.getUsers()) {
            System.out.println(user);
        }
    
        System.out.println("Products:");
        for (Product product : platform.getAvailableProducts()) {
            System.out.println(product);
        }
    
        System.out.println("Orders:");
        for (Order order : platform.getOrders()) {
            System.out.println(order);
        }
    
        System.out.println("Recommendations for User 1:");
        for (Product recommendedProduct : recommendationsUser1) {
            System.out.println(recommendedProduct);
        }
    }
}
