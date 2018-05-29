package com.service.api.me.base.cs61.linked.chapter;

public class SLList {


    public IntNode first;

    public SLList( int x ){
        first = new IntNode( x,null );
    }

    public void addFirst( int x ){
       first = new IntNode(x,first);
    }

    public int getFirst(){
        return first.item;
    }

    public void addLast( int x ){
        IntNode p = first;

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

        return size( first );
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
