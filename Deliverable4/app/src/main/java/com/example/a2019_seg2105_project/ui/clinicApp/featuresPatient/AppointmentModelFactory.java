package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.a2019_seg2105_project.data.AppointmentRepository;


public class AppointmentModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AppointmentViewModel.class)) {
            return (T) new AppointmentViewModel(AppointmentRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
