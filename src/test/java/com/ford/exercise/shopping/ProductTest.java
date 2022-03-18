package com.ford.exercise.shopping;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductTest {

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionIsThrownIfValueNotInEnum(){
        Product.of("XYZ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionIsThrownIfIDNotInEnum(){
        Product.from(5);
    }

    @Test
    public void testProductIsReturnedSuccessfully(){
        assertEquals(Product.APPLES,Product.of("apples"));
    }

    @Test
    public void testProductIsReturnedSuccessfullyForID(){
        assertEquals(Product.SOUP,Product.from(1));
    }



}