package com.ford.exercise.shopping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Predicate;

public class Discount {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Predicate<Basket> validationPredicate;
    private final Function<Basket,BigDecimal> application;
    private final String description;



    public Discount(LocalDate startDate, LocalDate endDate, Predicate<Basket> validation, Function<Basket,BigDecimal> application,String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.validationPredicate = validation;
        this.application = application;
        this.description = description;
    }



    public boolean canDiscountBeAppliedTo(Basket basket){
        //check shopping date is equal to discount start or end date or within the range
        if((basket.getShoppingDate().isAfter(this.startDate) || basket.getShoppingDate().isEqual(this.startDate) )
                &&  basket.getShoppingDate().isBefore(this.endDate) || basket.getShoppingDate().isEqual(this.endDate)){
            return validationPredicate.test(basket);
        }
        return false;
    }

    public BigDecimal applyDiscountTo(Basket basket){
        return application.apply(basket);
    }


    public String getDiscountDescription() {
        return description + " starting from = "+startDate + " until = "+endDate;
    }
}
