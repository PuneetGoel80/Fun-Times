package com.ford.exercise.shopping;


import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.Assert.assertEquals;

public class BasketTest {

    private final DiscountRepository repository = new DiscountRepository();

    @Test
    public void canAddProductsToShoppingBasket(){
        Basket basket = new Basket(LocalDate.now());
        basket.add(Product.APPLES,2);
        assertEquals(2, basket.getQuantity(Product.APPLES));
    }


    @Test
    public void canGetTheCorrectTotalOfProducts(){
        Basket basket = new Basket(LocalDate.now());
        basket.add(Product.APPLES,2);
        basket.add(Product.BREAD,1);
        basket.add(Product.MILK,1);
        basket.add(Product.SOUP,2);
        assertEquals(0,BigDecimal.valueOf(3.60).compareTo(basket.getTotalCost(List.of())));
    }

    @Test
    public void canGetTheCorrectTotalOfProductsEvenIfAddedTwice(){
        Basket basket = new Basket(LocalDate.now());
        basket.add(Product.APPLES,2);
        basket.add(Product.APPLES,1);
        basket.add(Product.MILK,1);
        basket.add(Product.SOUP,2);
        assertEquals(0,BigDecimal.valueOf(2.90).compareTo(basket.getTotalCost(List.of())));
    }

    @Test
    public void testDiscountIsAppliedToShoppingFor3Apples(){
        Basket basket = new Basket(LocalDate.now().plus(5,DAYS));
        List<Discount> discount = repository.getAllDiscounts();
        basket.add(Product.APPLES,2);
        basket.add(Product.APPLES,1);

        assertEquals(0,BigDecimal.valueOf(0.27).compareTo(basket.getTotalCost(discount)));
    }

    @Test
    public void testDiscountIsAvailableOnAppleForEndOfMonthOnly(){
        Basket basket = new Basket(LocalDate.now().plus(32,DAYS));
        List<Discount> discount = repository.getAllDiscounts();
        basket.add(Product.APPLES,2);
        basket.add(Product.APPLES,1);

        assertEquals(0,BigDecimal.valueOf(0.30).compareTo(basket.getTotalCost(discount)));
    }




    @Test
    public void test2TinSOupAppliesCorrectDiscountToBread(){
        Basket basket = new Basket(LocalDate.now());

        basket.add(Product.SOUP,2);
        basket.add(Product.BREAD,1);

        assertEquals(0,BigDecimal.valueOf(1.70).compareTo(basket.getTotalCost(repository.getAllDiscounts())));
    }

    @Test
    public void test3TinSoupANd2BreadLoavesProvidesCorrectResult(){
        Basket basket = new Basket(LocalDate.now());

        basket.add(Product.SOUP,3);
        basket.add(Product.BREAD,2);

        assertEquals(0,BigDecimal.valueOf(3.15).compareTo(basket.getTotalCost(repository.getAllDiscounts())));
    }

    @Test
    public void test3TinSoupANd2BreadLoavesProvidesCorrectResultForYesterday(){
        Basket basket = new Basket(LocalDate.now().minus(1,DAYS));

        basket.add(Product.SOUP,3);
        basket.add(Product.BREAD,2);

        assertEquals(0,BigDecimal.valueOf(3.15).compareTo(basket.getTotalCost(repository.getAllDiscounts())));
    }

    @Test
    public void test6ApplesAndMilkBottleBoughtToday(){
        Basket basket = new Basket(LocalDate.now());

        basket.add(Product.APPLES,6);
        basket.add(Product.MILK,1);

        assertEquals(0,BigDecimal.valueOf(1.90).compareTo(basket.getTotalCost(repository.getAllDiscounts())));
    }

    @Test
    public void test6ApplesAndMilkBottleBoughtIn5Days(){
        Basket basket = new Basket(LocalDate.now().plus(5,DAYS));

        basket.add(Product.APPLES,6);
        basket.add(Product.MILK,1);

        assertEquals(0,BigDecimal.valueOf(1.84).compareTo(basket.getTotalCost(repository.getAllDiscounts())));
    }

    @Test
    public void test3Apples2SoupTinsAndABreadLoaveBoughtIn5Days(){
        Basket basket = new Basket(LocalDate.now().plus(5,DAYS));

        basket.add(Product.APPLES,3);
        basket.add(Product.SOUP,2);
        basket.add(Product.BREAD,1);

        assertEquals(0,BigDecimal.valueOf(1.97).compareTo(basket.getTotalCost(repository.getAllDiscounts())));
    }




}