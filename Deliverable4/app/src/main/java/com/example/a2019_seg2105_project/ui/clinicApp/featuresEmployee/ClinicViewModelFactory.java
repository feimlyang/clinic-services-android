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
        if (modelClass.isAssignableFrom(ClinicViewModel.class)) {
            return (T) new ClinicViewModel(ClinicRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
