package com.service.api.me.base.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

public class CollectionAPI {

    private static List<Integer> unmodifiableList
                    = new ArrayList<Integer>();

    private static List<Integer> unmodifiableList1 = Collections
            .synchronizedList(new ArrayList<Integer>());

    @Test
    public void unModifiableList (){

        List<Integer> list = null;

        unmodifiableList.add(12);
        unmodifiableList.add(342);
        list = Collections.unmodifiableList( unmodifiableList );
        for(int i : list){
            print(i);
        }
        list.add(345);

        for(int i : list){
            print(i);
        }

    }
}
