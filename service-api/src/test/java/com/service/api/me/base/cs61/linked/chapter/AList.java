package com.service.api.me.base.cs61.linked.chapter;

public class AList {

    private int[] items;

    private int size;

    public AList(){

    }

    public void addLast( int x ){
        items[ size ] = x;
        size = size + 1;
    }

    public int getLast(){
        return items[size-1];
    }

    public int get( int i ){
        return items[i];
    }

    public int size(){
        return size;
    }

 /*   public int removeLast(){

    }*/
}
