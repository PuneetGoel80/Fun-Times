package com.ford.exercise.shopping;

import org.apache.commons.lang3.BooleanUtils;

import java.time.LocalDate;
import java.util.Scanner;

public class SuperMarket {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DiscountRepository discountRepository = new DiscountRepository();
        System.out.println("Please enter the date of shopping in YYYY-mm-dd format");
        String shoppingDate = scanner.next();
        Basket basket = new Basket(LocalDate.parse(shoppingDate));
        boolean addMoreProducts = true;
        while(addMoreProducts) {
            System.out.println(Product.getAllProducts());

            int productNumber = scanner.nextInt();
            System.out.println("Please enter quantity ");
            int quantity = scanner.nextInt();
            basket.add(Product.from(productNumber),quantity);
            System.out.println("Do you want to add more products Y/N");
            addMoreProducts = BooleanUtils.toBoolean(scanner.next());
        }
        System.out.println("Your total cost after applying all discounts is  = "+basket.getTotalCost(discountRepository.getAllDiscounts()));
        System.out.println("discounts applied to your shopping are -");
        basket.getDiscountApplied().forEach(discount -> System.out.println(discount.getDiscountDescription()));

    }


}
