package com.build.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class ToJson {

    public static void main( String sck[] ){

       Map<String,List<Level1>> map =  new HashMap<String,List<Level1>>();
       /**level1  初始化**/
        Level1 level1 = new Level1();
        level1.setName("app管理");
        level1.setLink("/app/index");
        level1.setType("1");


        Level3 level31 =  new Level3();
        level31.setName("物业管理");
        level31.setLink("/wuye/index");
        level31.setType("3");
    /*    Level3 level32 =  new Level3();
        Level3 level33 =  new Level3();*/

        List<Level3> level31List =  new LinkedList<Level3>();
        level31List.add(level31);
       /* level31List.add(level32);
        level31List.add(level33);*/
     /*   Map<String,List<Level3>>  level31Map = new HashMap<String,List<Level3>>();
        level31Map.put("level3",level31List);*/

        Level2 level21 =  new Level2();
        level21.setName("消息管理");
        level21.setLink("/mes/index");
        level21.setType("2");

        level21.setLevle3(level31List);

/*        Level2 level22 =  new Level2();
        Level2 level23 =  new Level2();*/

        List<Level2> level2List =  new LinkedList<Level2>();
        level2List.add( level21);
      /*  level2List.add( level22);
        level2List.add( level23);*/

      /*  Map<String,List<Level2>> level2Map = new HashMap<String,List<Level2>>();
        level2Map.put("level2",level2List);*/


       level1.setLevel2(level2List);

        List<Level1> level1List =  new ArrayList<Level1>();
        level1List.add(level1);
        map.put("data",level1List);


//        JsonObject returnData = new JsonParser().parse(out.toString()).getAsJsonObject();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        System.out.println(gson.toJson(map));


    }

    public static class Level1{

        private String name;

        private String link;

        private String type;

        private List<Level2>  level2;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Level2> getLevel2() {
            return level2;
        }

        public void setLevel2(List<Level2> level2) {
            this.level2 = level2;
        }
    }


    public static class Level2{
        private String name;

        private String link;

        private String type;

        private List<Level3>  levle3;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Level3> getLevle3() {
            return levle3;
        }

        public void setLevle3(List<Level3> levle3) {
            this.levle3 = levle3;
        }
    }

    public static class Level3{
        private String name;

        private String link;

        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
