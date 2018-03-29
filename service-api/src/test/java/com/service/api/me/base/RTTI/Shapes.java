package com.service.api.me.base.RTTI;

import java.util.Arrays;
import java.util.List;

abstract class Shape {

    void draw(){
        System.out.println(this +" .draw()");
    }

    abstract public String toString();

    abstract  public int printInt();
}


class Circle extends Shape{

    @Override
    public String toString(){
        return "Circle";
    }

    @Override
    public int printInt() {
        System.out.println("Circle printInt");
        return 1;
    }
}

class Square extends  Shape{

    @Override
    public String toString(){
        return "Square";
    }

    @Override
    public int printInt() {
        return 2;
    }
}

class Triangle extends Shape{

    @Override
    public String toString(){
        return "Triangle";
    }

    @Override
    public int printInt() {
        return 3;
    }
}

public class Shapes{

    public static void main( String sck[] ){
        List<Shape> shapeList = Arrays.asList(
                new Circle(),new Square(),new Triangle());

        for( Shape shape : shapeList){
            shape.draw();
        }
    }

}