package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import java.util.ArrayList;

public class SpinnerDataModel {
    ArrayList<String> selectedItems;
    String item;
    Boolean isSelected;

    public SpinnerDataModel(String item, Boolean isSelected){
        this.item = item;
        this.isSelected = isSelected;
    }

    public Boolean isSelected(){
        return isSelected;
    }

    public String getItem(){
        return item;
    }

    public void setSelected(Boolean isSelected){
        this.isSelected = isSelected;
    }
}
