package com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee;
import androidx.annotation.Nullable;
public class ClinicFormState {
    @Nullable
    private Integer clinicNameError;

    public ClinicFormState(@Nullable Integer clinicNameError)
    {
        //valid employee username
        this.clinicNameError = clinicNameError;
    }
    public Integer getError()
    {
        return this.clinicNameError;
    }
}
