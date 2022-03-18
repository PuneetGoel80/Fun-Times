package com.ford.exercise.shopping;

import java.math.BigDecimal;
import java.util.Arrays;

public enum Product {


    SOUP(BigDecimal.valueOf(0.65D),1),
    BREAD(BigDecimal.valueOf(0.80D),2),
    MILK(BigDecimal.valueOf(1.30D),3),
    APPLES(BigDecimal.valueOf(0.10D),4);


    Product(BigDecimal cost , int id){
        this.cost = cost;
        this.id = id;
    }

    private final BigDecimal cost;
    private final int id;


    public BigDecimal getCost() {
        return cost;
    }



    public static Product of(String product){
        return Arrays.stream(Product.values())
                .filter(p-> p.name().equalsIgnoreCase(product))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Product from(int id){
        return Arrays.stream(Product.values())
                .filter(p-> id ==p.id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static String getAllProducts(){
        return Arrays.stream(Product.values())
                .map(p-> (p.id + ": " +p.name())).reduce("",(a,b)-> a + b + "\n" );
    }

}
