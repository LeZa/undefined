package com.build.thinking.in.java.exceptions;

import static com.build.thinking.in.java.net.mindview.util.Print.print;
import static com.build.thinking.in.java.net.mindview.util.Print.printnb;

class VeryImportantException extends Exception {
    public String toString() {
        return "A very important exception!";
    }
}

class HoHumException extends Exception {
    public String toString() {
        return "A trivial exception";
    }
}

public class LostMessage {

    void f() throws VeryImportantException {
        print("F method()");
        throw new VeryImportantException();
    }

    void dispose() throws HoHumException {
        print("Dispose method()");
        throw new HoHumException();
    }

    public static void main(String[] args) {
        /**
         * @Description  Only get finally exception
         */
        try {
            LostMessage lm = new LostMessage();
            try {
                lm.f();
            }finally {
                lm.dispose();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    /*    *//**
         * @Description Get all exception
         *//*
        LostMessage lm2 = new LostMessage();
        try {
            lm2.f();
        } catch( Exception e){
            print( e );
        }finally {
            try {
                lm2.dispose();
            }catch( Exception e ){
                printnb( e );
            }
        }*/

    }
} /* Output:
A trivial exception
*///:~
