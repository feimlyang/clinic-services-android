package com.example.a2019_seg2105_project.ui.clinicApp.featuresAdmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.UserRepository;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.data.model.LoggedInUser;
import com.example.a2019_seg2105_project.ui.clinicApp.login.LoggedInUserView;


import java.util.ArrayList;

public class AdminAccountViewModel extends ViewModel {
       private UserRepository accountRepository;
       private MediatorLiveData<Result> accountLiveData = new MediatorLiveData<Result>();
       // Constructors
       AdminAccountViewModel(UserRepository accountRepository) {
              this.accountRepository= accountRepository;
       }

       public ArrayList<String> getAccountList()
       {
              final LiveData<Result> liveAccountList = accountRepository.getUsername();
              ArrayList<String> currentUserNameList;
              accountLiveData.addSource(liveAccountList , new Observer<Result>() {
                     @Override
                     public void onChanged(Result result) {

                     }
              });
              return null;
       }
}
