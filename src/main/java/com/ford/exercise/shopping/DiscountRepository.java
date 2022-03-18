package com.ford.exercise.shopping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.time.temporal.ChronoUnit.DAYS;

@SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
public class DiscountRepository {


    public List<Discount> getAllDiscounts() {
        return List.of(getDiscountsOnApples(),getDiscountOnSoup());
    }

    private Discount getDiscountOnSoup() {
        LocalDate today = LocalDate.now();
       return new Discount(today.minus(1, DAYS),
                today.plus(7, DAYS),
                getSoupDiscountValidation(),
                getSoupDiscountApplication(),
                "Buy 2 tins of soup and get a loaf of bread half price"
        );
    }

    //take cost of bread , take half price , and apply on minimum of bread quantity vs soup quantity /2
    private Function<Basket, BigDecimal> getSoupDiscountApplication() {
        return basket -> Product.BREAD.getCost()
                .divide(BigDecimal.valueOf(2))
                .multiply(BigDecimal.valueOf(Math.min(basket.getQuantity(Product.SOUP)/2, basket.getQuantity(Product.BREAD))));
    }

    //if basket contains 2 soups and a bread loaf
    private Predicate<Basket> getSoupDiscountValidation() {
        return basket -> basket.contains(Product.SOUP) && basket.getQuantity(Product.SOUP)>=2 && basket.contains(Product.BREAD);
    }

    private Discount getDiscountsOnApples() {

        LocalDate today = LocalDate.now();
        LocalDate validFromDate = today.plus(3, DAYS);
        LocalDate validToDate = today.plus(validFromDate.lengthOfMonth(), DAYS);

     return new Discount(validFromDate,
                validToDate,
                basket1 -> basket1.contains(Product.APPLES),
                basket1 -> Product.APPLES.getCost().multiply(BigDecimal.valueOf(basket1.getQuantity(Product.APPLES))).multiply(BigDecimal.valueOf(0.1d)),
                "10 % discount on Apples"
        );
    }

}
