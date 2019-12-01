package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.example.a2019_seg2105_project.data.UserRepository;
import com.example.a2019_seg2105_project.data.Result;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AdminAccountViewModel extends ViewModel {
       private UserRepository accountRepository;
       private MediatorLiveData<Result> deleteAccountLiveData = new MediatorLiveData<>();
       private static final String userNamePattern = "^(\\d|\\w){3,}$";
       // Constructors
       AdminAccountViewModel(UserRepository accountRepository) {
              this.accountRepository= accountRepository;
       }
       public LiveData<Result> getDeleteAccountLiveData()
       {
              return this.deleteAccountLiveData;
       }
       public boolean isUserNameValid(String username)
       {
           return Pattern.matches(userNamePattern, username);
       }


       public void deleteAccount(String username)
       {
              final LiveData<Result> deleteAccountLiveData = accountRepository.deleteUser(username);
              this.deleteAccountLiveData.addSource(deleteAccountLiveData, new Observer<Result>() {
                     @Override
                     public void onChanged(Result result) {
                            AdminAccountViewModel.this.deleteAccountLiveData.removeSource(deleteAccountLiveData);
                            AdminAccountViewModel.this.deleteAccountLiveData.setValue(result);
                            AdminAccountViewModel.this.deleteAccountLiveData.setValue(null);
                     }
              });

       }
}
