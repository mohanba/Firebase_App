package com.myfirstapp.nmit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView mtextView;
    Firebase mRef;

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtextView= (TextView) findViewById(R.id.ftv);
        editText= (EditText) findViewById(R.id.et1);
        button= (Button) findViewById(R.id.bt1);

        Firebase.setAndroidContext(this);

        mRef=new Firebase("https://my-fb-app-298a0.firebaseio.com/Name");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            String message=dataSnapshot.getValue().toString();
            mtextView.setText(message);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Message = editText.getText().toString();

                if(!Message.equals("")){
                    Map<    String, String> map = new HashMap<String, String>();
                    map.put("Name", Message);
                    map.put("Age", "25");
                    mRef.push().setValue(map);
                }
            }
        });

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map=dataSnapshot.getValue(Map.class);
                String message=map.get("Name").toString();
                mtextView.setText(message);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
