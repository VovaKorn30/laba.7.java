package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ECommercePlatform {
    private final Map<Integer, User> users;
    private final Map<Integer, Product> products;
    private final Map<Integer, Order> orders;

    public ECommercePlatform() {
        this.users = new HashMap<>();
        this.products = new HashMap<>();
        this.orders = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void createOrder(User user) {
        Map<Product, Integer> orderDetails = new HashMap<>(user.getCart());

        orderDetails.forEach((product, quantity) -> {
            int newStock = product.getStock() - quantity;
            product.setStock(newStock);
        });

        Order order = new Order(orders.size() + 1, user.getId(), orderDetails);
        orders.put(order.getId(), order);

        user.getCart().clear();
    }

    public List<Product> getAvailableProducts() {
        return new ArrayList<>(products.values());
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders.values());
    }

    public List<Product> getProductsSortedByPrice() {
        return products.values().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Product> getProductsSortedByName() {
        return products.values().stream()
                .sorted(new ProductNameComparator())
                .collect(Collectors.toList());
    }

    public List<Product> getProductsSortedByStock() {
        return products.values().stream()
                .sorted(new ProductStockComparator())
                .collect(Collectors.toList());
    }

    public List<Product> filterProductsByStock(int minStock) {
        return products.values().stream()
                .filter(product -> product.getStock() >= minStock)
                .collect(Collectors.toList());
    }

    public List<Product> recommendProducts(User user) {
        List<Product> recommendedProducts = new ArrayList<>();

        user.getCart().keySet().forEach(product -> recommendedProducts.addAll(findSimilarProducts(product)));
        getOrdersByUser(user).forEach(order ->
                order.getOrderDetails().keySet().forEach(product -> recommendedProducts.addAll(findSimilarProducts(product)))
        );

        return recommendedProducts.stream().distinct().collect(Collectors.toList());
    }

    private List<Product> findSimilarProducts(Product targetProduct) {
        return getAvailableProducts().stream()
                .filter(product -> !product.equals(targetProduct))
                .limit(2)
                .collect(Collectors.toList());
    }

    private List<Order> getOrdersByUser(User user) {
        return getOrders().stream()
                .filter(order -> order.getUserId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}
