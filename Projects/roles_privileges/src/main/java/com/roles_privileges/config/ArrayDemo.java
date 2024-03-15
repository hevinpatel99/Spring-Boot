package com.roles_privileges.config;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayDemo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Text Do you want to know");
        String input = sc.nextLine();

        String[] arrayList = {"movie", "political"};
        for (String context : arrayList) {
            if (input.contains(context)) {
                System.out.println("Give Error :" + context);
            }
        };
    }
}
