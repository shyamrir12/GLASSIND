package com.example.awizom.glassind;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.awizom.glassind.Adapters.OrderAdapter;
import com.example.awizom.glassind.Model.DataWorkOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TrackOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressDialog progressDialog ;
    DatabaseReference dataorder;
    List<DataWorkOrder> orderList;
    OrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        getOrder();

    }

    private void getOrder() {

        try {
            //String res="";
            progressDialog.setMessage("loading...");
            progressDialog.show();
            dataorder = FirebaseDatabase.getInstance().getReference("orders");
            dataorder.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   orderList.clear();

                    //iterating through all the nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting artist
                       DataWorkOrder order = postSnapshot.getValue(DataWorkOrder.class);
                        //adding artist to the list
                        orderList.add(order);
                    }

                    //creating adapter
                    adapter=new OrderAdapter(TrackOrderActivity.this, orderList);

                    //attaching adapter to the listview
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
         //   new MyCourse.GETCourseList().execute(SharedPrefManager.getInstance(this).getUser().access_token);
         //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();



        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            // System.out.println("Error: " + e);
        }
    }
}
