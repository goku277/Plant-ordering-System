package com.goku277.plantorder.ProcessingUnit;

import java.util.*;
public class ExtractId {
  /*  public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String data= "name: Sunflower price: 25 detail: Sunflower is a beutiful flower that grows during winter season category: Winter Plants key: Winter Plants Sunflower 1cf541a5-eb35-4414-9cca-797917e52f51 imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Winter%20Plants%20Sunflower%20809b32c1-deff-4513-ba6c-a20d7b4633bf?alt=media&token=b44cbf83-a01a-4fad-9a0c-55b4bf270f5d name: Lily price: 25 detail: Lily are found during summer category: Summer Plants key: Summer Plants Lily cbe17802-764b-43b3-bb19-e46787bf1ffa imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Summer%20Plants%20Lily%20e6591c4e-273c-48dd-ba5f-5d2410b1c1e3?alt=media&token=2437cded-a646-4a57-b76e-01b2a2d305b3 name: Papaya price: 25 detail: Papaya are good and tasty to eat category: Vegetable key: Vegetable Papaya 4e8241f8-3abd-413c-af8d-ce11e5773aaf imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Vegetable%20Papaya%20ad3d5373-eea8-4bdb-9e7a-2af880bba261?alt=media&token=4948a833-a233-4fcd-bc46-5622352df226 name: Spinach price: 15 detail: Spinach is a vegetable plant that can be found during winter season. category: Winter Plants key: Winter Plants Spinach 47f6a761-fe16-4f6f-82a0-31adc555eb8b imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/Winter%20Plants%20Spinach%20fdae1728-c46f-41f8-ae46-e4dd5e97f990?alt=media&token=eb86fae5-34d8-422f-9618-402572d17787 name: Aloevera price: 50 detail: Aloevera is a good plant that is both medicinal and can be found almost all the time in a year... category: All Season Plants key: All Season Plants Aloevera cd2b4f91-e28c-4197-b579-0e811b354539 imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/All%20Season%20Plants%20Aloevera%200a04067e-978c-4e7b-b71e-676b60f0d97d?alt=media&token=f2f444c7-1f1d-4a3d-9c5c-433685720cc3 name: Rose price: 100 detail: Red rose category: All Season Plants key: All Season Plants Rose 753ed7a1-937c-4a84-b4fe-09449f98d2c6 imageUri: https://firebasestorage.googleapis.com/v0/b/platorder.appspot.com/o/All%20Season%20Plants%20Rose%20a45dc1ff-b036-4c0c-b818-ce063a6037b8?alt=media&token=b61480ff-fd7b-4f02-bb8a-b52ad39da291";
        Map<String, Set<Set<String>>> a1= init(data);
        System.out.println("From main() a1 is: " + a1);
    }    */

    public Map<String, Set<Set<String>>> init(String data) {
        // System.out.println("data: " + data);
        String split[]= data.split("name:");
        Set<String> idSet= new LinkedHashSet<>();
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                s= s.trim();
                //   System.out.println("split: " + s);
                if (s.contains("key:") && s.contains("imageUri:")) {
                    idSet.add(s.substring(s.indexOf("key:"), s.indexOf("imageUri:")).replace("key:","").trim());
                }
            }
        }
        // System.out.println("idSet: " + idSet);
        Map<String, Set<Set<String>>> mapIdToDetails= new LinkedHashMap<>();
        Set<Set<String>> setOfSets= null;
        Set<String> setDetails= null;
        for (String s: idSet) {
            setOfSets= new LinkedHashSet<>();
            for (String s1: split) {
                setDetails= new LinkedHashSet<>();
                if (!s1.trim().isEmpty()) {
                    s1= s1.trim();
                    if (s1.contains(s)) {
                        if (s1.contains("price:")) {
                            setDetails.add(s1.substring(s1.indexOf(s1.charAt(0)), s1.indexOf("price:")).trim());
                        }
                        if (s1.contains("detail:") && s1.contains("category:")) {
                            setDetails.add(s1.substring(s1.indexOf("detail:"), s1.indexOf("category:")).trim());
                        }
                        if (s1.contains("category:") && s1.contains("key:")) {
                            setDetails.add(s1.substring(s1.indexOf("category:"), s1.indexOf("key:")).trim());
                        }
                        if (s1.contains("key:") && s1.contains("imageUri:")) {
                            setDetails.add(s1.substring(s1.indexOf("key:"), s1.indexOf("imageUri:")).trim());
                        }
                        if (s1.contains("imageUri:")) {
                            setDetails.add(s1.substring(s1.indexOf("imageUri:")));
                        }
                        setOfSets.add(setDetails);
                    }
                }
            }
            mapIdToDetails.put(s, setOfSets);
        }
        // System.out.println("mapIdToDetails: " + mapIdToDetails);
        return mapIdToDetails;
    }
}
