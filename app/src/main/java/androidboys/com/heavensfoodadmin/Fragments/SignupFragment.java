package androidboys.com.heavensfoodadmin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.labo.kaji.fragmentanimations.PushPullAnimation;

import java.util.ArrayList;
import java.util.List;

import androidboys.com.heavensfoodadmin.Activities.AuthenticationActivity;
import androidboys.com.heavensfoodadmin.Models.User;
import androidboys.com.heavensfoodadmin.R;
import androidboys.com.heavensfoodadmin.Utils.AuthUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignupFragment extends Fragment implements View.OnClickListener {

    private AuthenticationActivity hostingActivity;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private Button signupButton;
    private TextView loginTextview;

    private String mobileNumber;
    private String email;
    private String password;

    private List<User> users;


    public static SignupFragment newInstance() {

        Bundle args = new Bundle();

        SignupFragment fragment = new SignupFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup,container,false);
        hostingActivity=(AuthenticationActivity)getActivity();


        fetchUsers();


        emailEditText = view.findViewById(R.id.emailEdittext);
        phoneEditText = view.findViewById(R.id.phonenumberEdittext);
        passwordEditText = view.findViewById(R.id.passwordEdittext);
        signupButton = view.findViewById(R.id.signupButton);
        loginTextview = view.findViewById(R.id.loginTextview);

        signupButton.setOnClickListener(this);
        loginTextview.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signupButton:
                 registerUser();
                 break;
            case R.id.loginTextview:
                 hostingActivity.addDifferentFragment(SigninFragment.newInstance());
                 break;
        }

    }

    public void registerUser()
    {
        mobileNumber = phoneEditText.getText().toString().trim();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        if( !(AuthUtil.isValidEmail(email)))
        {
            emailEditText.setError("Enter a valid email");
            emailEditText.requestFocus();
            return;
        }
        if(!(AuthUtil.isVailidPhone(mobileNumber))){
            phoneEditText.setError("Enter a valid mobile number");
            phoneEditText.requestFocus();
            return;
        }

        if(passwordEditText.getText().toString().trim().length()<6)
        {
            passwordEditText.setError("Password should have atleast 6 characters");
            passwordEditText.requestFocus();
            return;
        }


        if(!checkAlreadyExists()) {
            verifyPhoneNumber();
        }else {
            Toast.makeText(hostingActivity, "You are already registered,Please Login", Toast.LENGTH_SHORT).show();
            hostingActivity.addDifferentFragment(SigninFragment.newInstance());
        }

    }

    public void verifyPhoneNumber()
    {

        hostingActivity.addDifferentFragment(VerificationFragment.newInstance(email,mobileNumber,password));

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return PushPullAnimation.create(PushPullAnimation.LEFT,enter,1000);
    }

    public void fetchUsers()
    {
        users = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                users.add(user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean checkAlreadyExists()
    {
        for(int i=0;i<users.size();i++)
        {
            User user = users.get(i);
            if(user.getEmail().equals(email)||user.getPhoneNumber().equals(mobileNumber)){
                return true;
            }
        }

        return false;
    }



}