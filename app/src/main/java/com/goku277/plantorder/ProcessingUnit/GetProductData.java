package com.goku277.plantorder.ProcessingUnit;
import java.util.*;
public class GetProductData {
  /*  public static void main(String[] args) {
        String data="name: Sunflower price: 25 detail: Sunflower is a beutiful flower that grows during winter season category: Winter Plants key: Winter Plants Sunflower 1cf541a5-eb35-4414-9cca-797917e52f51 imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Winter%20Plants%20Sunflower%20809b32c1-deff-4513-ba6c-a20d7b4633bf?alt=media&token=b44cbf83-a01a-4fad-9a0c-55b4bf270f5d name: Rose Plant price: 100 detail: Roses are best known as ornamental plants. category: Please choose any product items key: Please choose any product items Rose Plant b68d156e-c170-4291-a79d-a7599fe5ce73 imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Please%20choose%20any%20product%20items%20Rose%20Plant%20d9b3a0ba-e49f-49a1-8c33-3c74cd44b14a?alt=media&token=a7474d08-8d2f-4fc0-be34-579f318e4d39 name: Lily price: 25 detail: Lily are found during summer category: Summer Plants key: Summer Plants Lily cbe17802-764b-43b3-bb19-e46787bf1ffa imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Summer%20Plants%20Lily%20e6591c4e-273c-48dd-ba5f-5d2410b1c1e3?alt=media&token=2437cded-a646-4a57-b76e-01b2a2d305b3 name: Sunflower price: 25 detail: Sunflower is a beutiful flower that grows during winter season category: Winter Plants key: Winter Plants Sunflower 1cf541a5-eb35-4414-9cca-797917e52f51 imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Winter%20Plants%20Sunflower%20809b32c1-deff-4513-ba6c-a20d7b4633bf?alt=media&token=b44cbf83-a01a-4fad-9a0c-55b4bf270f5d name: Lily price: 25 detail: Lily are found during summer category: Summer Plants key: Summer Plants Lily cbe17802-764b-43b3-bb19-e46787bf1ffa imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Summer%20Plants%20Lily%20e6591c4e-273c-48dd-ba5f-5d2410b1c1e3?alt=media&token=2437cded-a646-4a57-b76e-01b2a2d305b3 name: Sunflower price: 25 detail: Sunflower is a beutiful flower that grows during winter season category: Winter Plants key: Winter Plants Sunflower 1cf541a5-eb35-4414-9cca-797917e52f51 imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Winter%20Plants%20Sunflower%20809b32c1-deff-4513-ba6c-a20d7b4633bf?alt=media&token=b44cbf83-a01a-4fad-9a0c-55b4bf270f5d name: Lily price: 25 detail: Lily are found during summer category: Summer Plants key: Summer Plants Lily cbe17802-764b-43b3-bb19-e46787bf1ffa imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Summer%20Plants%20Lily%20e6591c4e-273c-48dd-ba5f-5d2410b1c1e3?alt=media&token=2437cded-a646-4a57-b76e-01b2a2d305b3 name: Papaya price: 25 detail: Papaya are good and tasty to eat category: Vegetable key: Vegetable Papaya 4e8241f8-3abd-413c-af8d-ce11e5773aaf imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Vegetable%20Papaya%20ad3d5373-eea8-4bdb-9e7a-2af880bba261?alt=media&token=4948a833-a233-4fcd-bc46-5622352df226";
        Map<String, Set<Set<String>>> a1= init(data);
        System.out.println("From main() ai is: " + a1);
    }    */

    public Map<String, Set<Set<String>>> init(String data) {
        Set<String> categories= new LinkedHashSet<>();
        String split[]= data.split("name:");
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                if (s.contains("category:") && s.contains("key:")) {
                    categories.add(s.substring(s.indexOf("category:"), s.indexOf("key:")).replace("category:","").trim());
                }
            }
        }
        Map<String, Set<Set<String>>> mapCategoriesTodetails= new LinkedHashMap<>();
        Set<Set<String>> a1= null;
        Set<String> setExtractDetails= null;
        for (String s: categories) {
            a1= new LinkedHashSet<>();
            for (String s1: split) {
                setExtractDetails= new LinkedHashSet<>();
                String category1= "";
                if (s1.contains("category:") && s1.contains("key:")) {
                    category1= s1.substring(s1.indexOf("category:"), s1.indexOf("key:")).replace("category:","").trim();
                }
                if (category1.equals(s)) {
                    if (s1.contains("price:")) {
                        setExtractDetails.add("name:" + s1.substring(s1.indexOf(s1.charAt(0)), s1.indexOf("price:")).trim());
                    }
                    if (s1.contains("price:") && s1.contains("detail:")) {
                        setExtractDetails.add(s1.substring(s1.indexOf("price:"), s1.indexOf("detail:")).trim());
                    }
                    if (s1.contains("detail:") && s1.contains("category:")) {
                        setExtractDetails.add(s1.substring(s1.indexOf("detail:"), s1.indexOf("category:")).trim());
                    }
                    if (s1.contains("category:") && s1.contains("key:")) {
                        setExtractDetails.add(s1.substring(s1.indexOf("category:"), s1.indexOf("key:")).trim());
                    }
                    if (s1.contains("key:") && s1.contains("imageUri:")) {
                        setExtractDetails.add(s1.substring(s1.indexOf("key:"), s1.indexOf("imageUri:")).trim());
                    }
                    if (s1.contains("imageUri:")) {
                        setExtractDetails.add(s1.substring(s1.indexOf("imageUri:")).trim());
                    }
                    a1.add(setExtractDetails);
                    System.out.println("From GetProductData a1: " + a1);
                }
                mapCategoriesTodetails.put(s, a1);
            }
        }
        return mapCategoriesTodetails;
    }
}