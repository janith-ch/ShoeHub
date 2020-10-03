package com.example.shoehub.feedback_u;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoehub.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class feedback extends AppCompatActivity {

    EditText txtBrand,txtDescription;
    Button submitFeedback;
    Spinner spinnerType;

    DatabaseReference databaseFeedback;
    ListView listViewFeedback;

    List<FeedBackClass> feedbackList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        databaseFeedback = FirebaseDatabase.getInstance().getReference("feedback");

        txtBrand=(EditText)findViewById(R.id.txtBrand);
        spinnerType=(Spinner)findViewById(R.id.spinnerType);
        //txtShoeType=(EditText)findViewById(R.id.txtShoeType);
        txtDescription=(EditText)findViewById(R.id.txtDescription);
        submitFeedback=(Button)findViewById(R.id.submitFeedback);

        listViewFeedback = (ListView) findViewById(R.id.listViewFeedback);

        feedbackList = new ArrayList<>();

        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFeedback();
            }
        });

        //not sure wheather working
       // listViewFeedback.setOnItemClickListener();

        listViewFeedback.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                FeedBackClass feedback = feedbackList.get(i);

                showUpdateFeedback(feedback.getFeedbackID(),feedback.getBrand(),feedback.getType(),feedback.getDescription());
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                feedbackList.clear();

                for (DataSnapshot feedbackSnapshot: snapshot.getChildren()){
                    FeedBackClass feedback = feedbackSnapshot.getValue(FeedBackClass.class);

                    feedbackList.add(feedback);
                }

                feedbackList adapter = new feedbackList(feedback.this, feedbackList);
                listViewFeedback.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showUpdateFeedback(final String id, String brand, String type, final String desc){

        final AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView  = inflater.inflate(R.layout.update_feedback,null);

        dialogbuilder.setView(dialogView);

        //final TextView textViewName = (TextView) dialogView.findViewById(R.id.textViewName);
        final EditText editTextBrandName = (EditText) dialogView.findViewById(R.id.editTextBrandName);
        final Spinner spinnerTypeName = (Spinner) dialogView.findViewById(R.id.spinnerTypeName);
        final EditText editTextDescriptionName = (EditText) dialogView.findViewById(R.id.editTextDescriptionName);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogbuilder.setTitle("Updating Feedback : "+desc);

        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String brand = editTextBrandName.getText().toString().trim();
                String desc = editTextDescriptionName.getText().toString().trim();
                String type = spinnerType.getSelectedItem().toString();

                if (TextUtils.isEmpty(desc)){
                    editTextDescriptionName.setError("Description Required");
                    return;
                }

                updateFeedback(id,brand,type,desc);
                alertDialog.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFeedback(id);
            }
        });


    }

    private void deleteFeedback(String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("feedback").child(id);

        databaseReference.removeValue();

        Toast.makeText(this,"Feedback is deleted!!!",Toast.LENGTH_LONG).show();
    }

    private boolean updateFeedback(String id,String brand,String type,String desc){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("feedback").child(id);
        FeedBackClass feedback = new FeedBackClass(id,brand,type,desc);
        databaseReference.setValue(feedback);

        Toast.makeText(this,"Feedback Updated Successfully!!!",Toast.LENGTH_LONG).show();
        return true;
    }

    private void addFeedback(){
        String brand = txtBrand.getText().toString().trim();
        String desc = txtDescription.getText().toString().trim();
        String type = spinnerType.getSelectedItem().toString();

        if (!TextUtils.isEmpty(desc)){

            String id = databaseFeedback.push().getKey();

            FeedBackClass feedback = new FeedBackClass(id,brand,desc,type);

            databaseFeedback.child(id).setValue(feedback);

            Toast.makeText(this,"**Feedback Added**",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"Enter a Description!!!",Toast.LENGTH_LONG).show();
        }
    }

}