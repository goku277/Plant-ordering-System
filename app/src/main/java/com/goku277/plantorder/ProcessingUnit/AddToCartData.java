package com.goku277.plantorder.ProcessingUnit;

import java.util.*;
public class AddToCartData {
  /*  public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String data="username: Simi Roy usermobile: 8812055712 Name: Calendula or pot narigold Detail: Pot marigold, as it’s commonly called, is an absolute must-have winter flower.  Price: 149 Category: Winter Plants imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Winter%20Plants%20Calendula%20or%20pot%20narigold%201a1e507f-7aa2-4068-b426-d146ad450e63?alt=media&token=c07c183f-dd46-4a2f-ab3b-13420dda3b3b username: Simi Roy usermobile: 8812055712 Name: Calendula or pot narigold Detail: Pot marigold, as it’s commonly called, is an absolute must-have winter flower.  Price: 149 Category: Winter Plants imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Winter%20Plants%20Calendula%20or%20pot%20narigold%201a1e507f-7aa2-4068-b426-d146ad450e63?alt=media&token=c07c183f-dd46-4a2f-ab3b-13420dda3b3b username: Simi Roy usermobile: 8812055712 Name: Lemon grass Detail: Lemongrass has been reported to have innumerable therapeutic and other health benefits. Price: 249 Category: Medicinal Plants imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Medicinal%20Plants%20Lemon%20grass%20501be81c-cde1-4ecf-82ce-59bfc637f9ea?alt=media&token=8eb82391-518f-433c-9e21-ee2a037f27ba username: Simi Roy usermobile: 8812055712 Name: Calendula or pot narigold Detail: Pot marigold, as it’s commonly called, is an absolute must-have winter flower.  Price: 149 Category: Winter Plants imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Winter%20Plants%20Calendula%20or%20pot%20narigold%201a1e507f-7aa2-4068-b426-d146ad450e63?alt=media&token=c07c183f-dd46-4a2f-ab3b-13420dda3b3b username: Simi Roy usermobile: 8812055712 Name: Pentas Detail: Pentas develop small, star flowers in bright,bold hues for summer long blooms. Price: 80 Category: Summer Plants imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Summer%20Plants%20Pentas%20bcbe8ecb-7669-4f76-955a-c51a312a1590?alt=media&token=e6228b90-6cde-48a9-b8e0-bbd77268e5e2 username: Simi Roy usermobile: 8812055712 Name: Calendula or pot narigold Detail: Pot marigold, as it’s commonly called, is an absolute must-have winter flower.  Price: 149 Category: Winter Plants imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Winter%20Plants%20Calendula%20or%20pot%20narigold%201a1e507f-7aa2-4068-b426-d146ad450e63?alt=media&token=c07c183f-dd46-4a2f-ab3b-13420dda3b3b username: Simi Roy usermobile: 8812055712 Name: Lemon grass Detail: Lemongrass has been reported to have innumerable therapeutic and other health benefits. Price: 249 Category: Medicinal Plants imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Medicinal%20Plants%20Lemon%20grass%20501be81c-cde1-4ecf-82ce-59bfc637f9ea?alt=media&token=8eb82391-518f-433c-9e21-ee2a037f27ba";
        String username= "Simi Roy";
        String usermobile= "8812055712";
        Set<Set<String>> a1= init(data, username, usermobile);
        System.out.println("From main() a1: " + a1);
    }   */

    public Set<Set<String>> init(String data, String username, String usermobile) {
        String split[]= data.split("username:");
        Set<Set<String>> detailsSet= new LinkedHashSet<>();
        Set<String> set1= null;
        for (String s: split) {
            String name="", price="", imageUri="";
            set1= new LinkedHashSet<>();
            if (s.contains(username) && s.contains(usermobile)) {
                if (s.contains("Name:") && s.contains("Detail:")) {
                    name= s.substring(s.indexOf("Name:"), s.indexOf("Detail:")).replace("Name:", "Name:\t");
                    set1.add(name.trim());
                }
                if (s.contains("Price:") && s.contains("imageUri:")) {
                    price= s.substring(s.indexOf("Price:"), s.indexOf("Category:")).replace("Price:", "Price:\t");
                    set1.add(price.trim());
                }
                if (s.contains("imageUri:")) {
                    imageUri= s.substring(s.indexOf("imageUri:")).trim();
                    set1.add(imageUri);
                }
                detailsSet.add(set1);
            }
        }
        return detailsSet;
    }
}