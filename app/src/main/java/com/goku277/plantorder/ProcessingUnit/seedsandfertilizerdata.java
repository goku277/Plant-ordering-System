package com.goku277.plantorder.ProcessingUnit;

import java.util.*;
public class seedsandfertilizerdata {
   /* public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String data= "name: Soyabean seeda price: 25 seedsqty: 10 kg detail: Soyabean seeds are useful to grow soyabeans. category: Soil and Fertilizer key: 10 kg Soyabean seeda 5f514b1d-7a73-45e0-ac6c-6c252716c24b imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/kg%20Soyabean%20seeda%20908009ab-ff95-4e35-8f9b-2923eaee19ea?alt=media&token=fdc8a3b7-e6a3-4135-b72e-5033d44c0763";
        init(data);
    }   */

    public Set<Set<String>> init(String data) {
        System.out.println("data: " + data);
        String split[]= data.split("name:");
        Set<Set<String>> details= new LinkedHashSet<>();
        Set<String> set1= null;
        for (String s: split) {
            String name="", price="", seedqty="", detail="", category="", imageUrl="";
            set1= new LinkedHashSet<>();
            if (!s.trim().isEmpty()) {
                s= s.trim();
                System.out.println("split: " + s);
                if (s.contains("price:")) {
                    name= "name: " + s.substring(s.indexOf(s.charAt(0)), s.indexOf("price:")).trim();
                    set1.add(name);
                }
                if (s.contains("price:") && s.contains("seedsqty:")) {
                    price= s.substring(s.indexOf("price:"), s.indexOf("seedsqty:")).trim();
                    set1.add(price);
                }
                if (s.contains("seedsqty:") && s.contains("detail:")) {
                    seedqty= s.substring(s.indexOf("seedsqty:"), s.indexOf("detail:")).trim();
                    set1.add(seedqty);
                }
                if (s.contains("detail:") && s.contains("category:")) {
                    detail= s.substring(s.indexOf("detail:"), s.indexOf("category:")).trim();
                    set1.add(detail);
                }
                if (s.contains("category:") && s.contains("key:")) {
                    category= s.substring(s.indexOf("category:"), s.indexOf("key:")).trim();
                    set1.add(category);
                }
                if (s.contains("imageUri:")) {
                    imageUrl= s.substring(s.indexOf("imageUri:")).trim();
                    set1.add(imageUrl);
                }
                details.add(set1);
            }
        }
        System.out.println("details: " + details);
        return details;
    }
}