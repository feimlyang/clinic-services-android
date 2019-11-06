package com.example.a2019_seg2105_project.data.model;

import java.util.ArrayList;

public class Service {
    //key
    public String serviceName;

    //attributes
    //category; subcategroy; rolePerforming

    private ArrayList<String> categoryList;
    private ArrayList<String> rolePerformingList;
    //private ArrayList<ArrayList<String>> subcategory;
    private ArrayList<String> subcategory0;
    private ArrayList<String> subcategory1;
    private ArrayList<String> subcategory2;
    private ArrayList<String> subcategory3;
    private ArrayList<String> subcategory4;

    //Constructor
    public Service(){
        this.categoryList = new ArrayList<String>(){{
            add("Family Medicine"); //subcategory0 <- categoryList[0]
            add("Examination"); //subcategory1
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
    /*get category by index*/
    public String getCategory(int n){
        return categoryList.get(n);
    }

    /*get subcategory by give related subcategory, and index of the elem of the subcategroy */
    public String getSubcategory(ArrayList<String> subcategroyX, int n){
        return subcategroyX.get(n);
    }

    public String getRolePerforming(int n){
        return rolePerformingList.get(n);
    }

}
