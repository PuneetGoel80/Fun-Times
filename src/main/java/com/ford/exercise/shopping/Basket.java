package com.ford.exercise.shopping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Basket {

    private final LocalDate shoppingDate;
    private final Map<Product, Integer> allItems;
    private List<Discount> discountApplied;

    public Basket(LocalDate shoppingDate) {
        this.shoppingDate = shoppingDate;
        allItems = new HashMap<>();

    }

    public void add(Product product, int orderedQuantity) {
        Integer quantity = allItems.getOrDefault(product, 0);
        quantity = quantity + orderedQuantity;
        allItems.put(product, quantity);
    }

    public int getQuantity(Product product) {
        return allItems.getOrDefault(product, 0);
    }

    public BigDecimal getTotalCost(List<Discount> availableDiscounts) {

        var allProducts = allItems.keySet();
        BigDecimal totalCost = calculateTotalCostOf(allProducts);

        BigDecimal totalDiscount = calculateApplicableDiscounts(availableDiscounts);
        return totalCost.subtract(totalDiscount);

    }

    private BigDecimal calculateApplicableDiscounts(List<Discount> availableDiscounts) {
        filterApplicableDiscounts(availableDiscounts);
        return discountApplied.stream().map(d -> d.applyDiscountTo(this)).reduce(BigDecimal.ZERO, (discount, total) -> total.add(discount));
    }

    private void filterApplicableDiscounts(List<Discount> availableDiscounts) {
        discountApplied = availableDiscounts.stream()
                .filter(d -> d.canDiscountBeAppliedTo(this)).collect(Collectors.toList());
    }

    private BigDecimal calculateTotalCostOf(Set<Product> allProducts) {
        return allProducts.stream()
                .map(p -> p.getCost().multiply(BigDecimal.valueOf(allItems.get(p))))
                .reduce(BigDecimal.ZERO, (cost, total) -> total.add(cost));
    }

    public LocalDate getShoppingDate() {
        return shoppingDate;
    }

    public boolean contains(Product product) {
        return allItems.containsKey(product);
    }

    //TODO : Defensive copy is needed
    public List<Discount> getDiscountApplied() {
        return discountApplied;
    }
}
