package com.userrole.config;

import java.util.*;

public class demo {

    public static void main(String[] args) {
       int a =10;
       a = a++ + ++a + a++ + ++a;

       10 + 12 + 12 + 14
        System.out.println(a);

    }
}
     /*   String name = "Hevinhevin";

        Map<Character, Integer> map = new HashMap<>();
        Integer count = 1;
        char[] charArray = name.toCharArray();
        for (int i = 0; i < charArray.length-1; i++) {
            if (charArray[i] == charArray[i + 1]) {
                map.put(charArray[i], map.get(charArray[i]) + 1);

            } else {
                map.put(charArray[i], count);
            }
        }

        map.forEach((character, integer) -> System.out.println("charater : " + character + " : get value : " + map.get(character)));
        
        
       *//* String[] words = name.split(" ");
        for (String word : words) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, count);
            }
        }*//*

//        StringBuilder name1 = new StringBuilder();

//        for (int i = name.length() - 1; i >= 0; i--){
//            name1.append(name.charAt(i));
//        }
//
//        StringBuilder str = new StringBuilder(name);
//        StringBuilder reverse = str.reverse();
//        System.out.println(name1);
//        System.out.println(reverse.toString());
       *//* long count1 = name.chars().filter(i -> i == 'M').count();
        System.out.println(count1);



        for (String s : map.keySet()) {
            System.out.println("word : " + s + " : count :" + map.get(s));
        }*//*


        int[] array = {8, 1, 4, 5};
        int[] array2 = {8, 1, 4, 5, 8};

        int smallest = array[0];

        int secondSmallest = array[1];

        for (int j : array) {
            System.out.println(j);
            if (j < smallest) {
                secondSmallest = smallest;
                smallest = j;
            } else if (j > smallest && j < secondSmallest) {
                secondSmallest = j;
            }
        }
        System.out.println("smallest:" + smallest);
        System.out.println("secondSmallest :" + secondSmallest);


       *//* for (int i = 0; i < array2.length; i++) {
            if (array2[i] == array2[i + 1]) {
                System.out.println("duplicate: " + array[i]);
            }
        }*//*


        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the String");
        String s = sc1.nextLine();
        char[] ch = s.toCharArray();
        int val = 0;
        String s1 = "";
        for (int i = 0; i < ch.length; i++) {
            val = ch[i] + 1;
            char c = (char) val;
            s1 = s1 + c;
        }
        System.out.println(s1);
    }*/



