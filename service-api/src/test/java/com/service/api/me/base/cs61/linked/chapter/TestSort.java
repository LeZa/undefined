package com.service.api.me.base.cs61.linked.chapter;

public class TestSort {

    public static void testSort(){
        String[] input = {"I","have","an","egg"};
        String[] expected = {"an","egg","have","I"};

        Sort.sort(input);

        org.junit.Assert.assertArrayEquals( expected,input);

    }

    public static void main( String[] sck ){
        TestSort.testSort();;
    }
}
