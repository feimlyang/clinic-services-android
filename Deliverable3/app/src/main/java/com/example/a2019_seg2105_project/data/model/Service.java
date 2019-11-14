package com.example.a2019_seg2105_project.data.model;

import java.util.ArrayList;
import java.util.Map;

public class Service {
    //key
    public String serviceName = null;
    //attributes
    public String category = null;
    public String subcategory = null;
    public String rolePerforming = null;

    //Construtor
    public Service(final String serviceName, final String category, final String subcategory, final String rolePerforming) {
        this.serviceName = serviceName;
        this.category = category;
        this.subcategory = subcategory;
        this.rolePerforming = rolePerforming;
    }


/*

    private final ArrayList<String> categoryList;
    private final ArrayList<String> rolePerformingList;
    //private ArrayList<ArrayList<String>> subcategory;
    public enum subcategorySeries{
        subcategory0,
        subcategory1,
        subcategory2,
        subcategory3,
        subcategory4;
    }

    private final ArrayList<String> subcategory0;
    private final ArrayList<String> subcategory1;
    private final ArrayList<String> subcategory2;
    private final ArrayList<String> subcategory3;
    private final ArrayList<String> subcategory4;

    //Constructor
    private Service(){
        this.categoryList = new ArrayList<String>(){{
            add("Family Medicine"); //subcategory0 <- categoryList[0]
            add("Examination"); //subcategory1
            add("Physiotherapy"); //subcategory2
            add("Immunization and Vaccination");//subcategory3
            add("Skin Care");//subcategory4
        }};
        this.rolePerformingList = new ArrayList<String>(){{
            add("Doctor");
            add("Nurse");
            add("Staff");
        }};

        this.subcategory0 = new ArrayList<String>(){{
            add("Internal Medicine");
            add("Surgery");
            add("Pediatrics");
            add("General Consulting");
        }};

        this.subcategory1 = new ArrayList<String>(){{
            add("Ultrasound");
            add("X-ray");
            add("Blood Test");
        }};
        this.subcategory2 = new ArrayList<String>(){{
            add("Magnetic Treatment");
            add("Massage");
            add("Sport Injury");
        }};

        this.subcategory3 = new ArrayList<String>(){{
            add("Flu Shoot");
            add("Hep B");
        }};
        this.subcategory4 = new ArrayList<String>(){{
            add("Acne");
            add("Tanning");
            add("Firming");
        }};
    }

    //Getters
    */
    /*get category by index*//*

    public String getCategory(int n){
        return categoryList.get(n);
    }

    public ArrayList<String> getCategoryList()
    { return this.categoryList;}

    public ArrayList<String> defineSubcategory(subcategorySeries subcategory){
        switch (subcategory){
            case subcategory0: return this.subcategory0;
            case subcategory1: return this.subcategory1;
            case subcategory2: return this.subcategory2;
            case subcategory3: return this.subcategory3;
            case subcategory4: return this.subcategory4;
        }
        return null;
    }

    */
    /*get subcategory by give related subcategory, and index of the elem of the subcategroy *//*

    public String getSubcategory(ArrayList<String> subcategroy, int n){
        if (subcategroy == null) throw new NullPointerException("category does not exist");
        return subcategroy.get(n);
    }

    public String getRolePerforming(int n){
        return rolePerformingList.get(n);
    }

*/
}
