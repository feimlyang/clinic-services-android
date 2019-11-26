package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.data.Result;
import com.example.a2019_seg2105_project.helpers.GlobalObjectManager;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient.AppointmentViewModel;

import java.util.Map;

public class RateClinicFragment extends Fragment {
    private AppointmentViewModel appointmentViewModel;
    private Button returnButton;
    private Button submitButton;
    private EditText commentFilling;
    private RatingBar ratingBar;
    private TextView points;
    private Map<String, String> attributes;

    GlobalObjectManager helper = GlobalObjectManager.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        appointmentViewModel = ViewModelProviders.of(this, new AppointmentModelFactory()).get(AppointmentViewModel.class);
        View root = inflater.inflate(R.layout.patient_fragment_rateclinic, container, false);
        return root;
    }

    public RateClinicFragment(Map<String, String> myAttributesMap){
        attributes = myAttributesMap;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        returnButton = (Button) getActivity().findViewById(R.id.btn_Return);
        submitButton = (Button) getActivity().findViewById(R.id.btn_Submit);
        commentFilling = (EditText)getActivity().findViewById(R.id.editTextComment);
        ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar);
        points = (TextView) getActivity().findViewById(R.id.textViewRatingPoint);



        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.patient_layout_rateclinic, new PatientMainFragment());
                transaction.commit();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                points.setText(String.valueOf(v));

            }
        });

        commentFilling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                appointmentViewModel.isCommentWithinRange(s.toString());
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentFilling.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please write some comments", Toast.LENGTH_LONG).show();
                } else {
                    commentFilling.setText("");
                    ratingBar.setRating(5);
                    Toast.makeText(getContext(), "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();

                    final String employeeName = attributes.get("employeeName");
                    appointmentViewModel.rateAppointment(employeeName,Float.parseFloat(String.valueOf(points.getText())),commentFilling.getText().toString());
                }
            }
        });
        appointmentViewModel.rateAppointmentData.observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(null == result) return;
                if(result instanceof Result.Failure || result instanceof Result.Error)
                {
                    Toast.makeText(getContext(), (Integer)((Result.Failure) result).getData(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Rate is submitted successfully",  Toast.LENGTH_SHORT).show();


                }

            }
        });





    }
}
