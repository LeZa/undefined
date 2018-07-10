package com.build.thinking.in.java.reusing;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

class Game {
  Game(int i) {
    print("Game constructor");
  }
}

class BoardGame extends Game {
  BoardGame(int i) {
    super(i);
    print("BoardGame constructor");
  }
}	

public class Chess extends BoardGame {

  Chess() {
    super(11);
    print("Chess constructor");
  }

  public static void main(String[] args) {
    Chess x = new Chess();
  }
} /* Output:
Game constructor
BoardGame constructor
Chess constructor
*///:~
