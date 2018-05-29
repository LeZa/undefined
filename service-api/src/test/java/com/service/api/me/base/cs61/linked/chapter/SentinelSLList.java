package com.service.api.me.base.cs61.linked.chapter;

public class SentinelSLList {


    public IntNode sentinel;

    private int size = 0;

    public SentinelSLList(){
        sentinel = new IntNode(63,null);
    }

    public SentinelSLList(int x ){
        sentinel = new IntNode( 63,null);
        sentinel = new IntNode( x,null );
    }

    public void addFirst( int x ){
        size += 1;
        sentinel.next = new IntNode(x,sentinel.next);
    }

    public int getFirst(){
        return sentinel.next.item;
    }

    public void addLast( int x ){

        size += 1;
        IntNode p = sentinel;

        while ( p.next != null ){
            p = p.next;
        }

        p.next = new IntNode(x,null);
    }


    private static int size( IntNode p ){

        if( p.next == null ){
            return 1;
        }

        return 1 + size( p.next );
    }

    public int size(){
         return size;
//        return size( sentinel );
    }


    public static void main( String[] args ){

        SLList L = new SLList(15);

        L.addFirst(10);
        L.addFirst(5);
        L.addLast(20);
//        System.out.println( L.getFirst() );

        System.out.println( L.size() );

    }

}
