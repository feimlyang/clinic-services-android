package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import androidx.annotation.Nullable;

public class AppointmentFormState {

    @Nullable
    private Integer patientNameError;

    public AppointmentFormState(@Nullable Integer clinicNameError)
    {
        //valid employee username
        this.patientNameError = clinicNameError;
    }
    public Integer getError()
    {
        return this.patientNameError;
    }
}

