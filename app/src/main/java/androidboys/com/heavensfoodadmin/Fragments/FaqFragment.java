package androidboys.com.heavensfoodadmin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidboys.com.heavensfoodadmin.Adapters.FaqArrayAdapter;
import androidboys.com.heavensfoodadmin.Models.Faq;
import androidboys.com.heavensfoodadmin.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FaqFragment extends Fragment {
    private ListView faqlistView;
    private ArrayList<Faq> faqArrayList=new ArrayList<>();
    private FaqArrayAdapter faqArrayAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.f_a_q_fragment,container,false);
        faqlistView=view.findViewById(R.id.faqListView);
        faqArrayAdapter=new FaqArrayAdapter(getContext(),faqArrayList);
        faqlistView.setAdapter(faqArrayAdapter);
        fetchFaqFromFirebase();

        return view;
    }

    private void fetchFaqFromFirebase() {

        FirebaseDatabase.getInstance().getReference("Faq").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                faqArrayList.add(dataSnapshot.getValue(Faq.class));
                if (faqArrayAdapter != null)
                    faqArrayAdapter.notifyDataSetChanged();
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

    public FaqFragment() {
    }

    public static FaqFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FaqFragment fragment = new FaqFragment();
        fragment.setArguments(args);
        return fragment;
    }
}