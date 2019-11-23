package com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient;

import android.os.Bundle;
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
import androidx.lifecycle.ViewModelProviders;

import com.example.a2019_seg2105_project.R;
import com.example.a2019_seg2105_project.ui.clinicApp.featuresPatient.AppointmentViewModel;

public class RateClinicFragment extends Fragment {
    private AppointmentViewModel appointmentViewModel;
    private Button returnButton;
    private Button submitButton;
    private EditText commentFilling;
    private RatingBar ratingBar;
    private TextView points;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        container.removeAllViews();
        appointmentViewModel = ViewModelProviders.of(this, new AppointmentModelFactory()).get(AppointmentViewModel.class);
        View root = inflater.inflate(R.layout.patient_fragment_rateclinic, container, false);
        return root;
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



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentFilling.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please write some comments", Toast.LENGTH_LONG).show();
                } else {
                    commentFilling.setText("");
                    ratingBar.setRating(9);
                    Toast.makeText(getContext(), "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                    //need some changes!!!
                }
            }
        });





    }
}
