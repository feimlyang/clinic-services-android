package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import androidx.annotation.Nullable;

public class AdminServiceFormState {
    @Nullable
    private Integer serviceNameError;

    public AdminServiceFormState(@Nullable Integer serviceNameError)
    {
        this.serviceNameError = serviceNameError;
    }
    public Integer getError()
    {
        if(null != serviceNameError)
        {
            return serviceNameError;
        }
        return null;
    }
}
