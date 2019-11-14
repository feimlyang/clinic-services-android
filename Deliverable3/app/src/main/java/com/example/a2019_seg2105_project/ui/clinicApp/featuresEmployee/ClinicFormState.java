package com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee;
import androidx.annotation.Nullable;
public class ClinicFormState {
    @Nullable
    private Integer usernameError;

    public ClinicFormState(@Nullable Integer usernameError)
    {
        //valid employee username
        this.usernameError = usernameError;
    }
    public Integer getError()
    {
        if(null != usernameError)
        {
            return usernameError;
        }
        return null;
    }
}
