package com.example.a2019_seg2105_project.ui.clinicApp.featuresEmployee;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.a2019_seg2105_project.data.ClinicRepository;

public class ClinicViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ClinicViewModelFactory.class)) {
            return (T) new ClinicViewModelFactory(ClinicRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
