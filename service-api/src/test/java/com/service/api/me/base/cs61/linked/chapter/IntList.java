package com.service.api.me.base.cs61.linked.chapter;

public class IntList {

    public int first;

    private IntList rest;

    public IntList( int f,IntList r){
        this.first = f;
        this.rest = r;
    }

    public int size(){
        if( rest  ==  null ){
            return 1;
        }
        /**
         * a circle
         */
        int i = 1 + this.rest.size();
        return i;
    }

    public int interativeSize(){
        IntList p = this;
        int totalSize = 0;
        while( p != null ){
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }

    /**
     * @Descritpion 每次执行this都会发生变化
     * @param i
     * @return
     */
    public int get( int i ){
        if( i == 0 ){
            return first;
        }
        return rest.get( i-1 );
    }



    public static void main(String sck[] ){

  /*      IntList  L = new IntList();

        L.first = 5;
        L.rest  = null;

        L.rest = new IntList();

        L.rest.first = 10;

        L.rest.rest = new IntList();
        L.rest.rest.first = 15;*/

      IntList L = new IntList(15,null);
      L = new IntList(10,L);
              L = new IntList(5,L);

             /* System.out.println( L.size() );*/

              System.out.println( L.get(2) );

    }
}
