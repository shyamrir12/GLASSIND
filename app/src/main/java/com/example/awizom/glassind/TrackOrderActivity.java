package com.example.awizom.glassind;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.example.awizom.glassind.Adapters.OrderAdapter;
import com.example.awizom.glassind.Model.DataWorkOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.SearchView;

public class TrackOrderActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    ProgressDialog progressDialog ;
    DatabaseReference dataorder;
    List<DataWorkOrder> orderList;
    OrderAdapter adapter;
    DataWorkOrder addWorkorder;
    int CAMRA_REQUEST_CODE=0;
    String filename="";
    Button addorder,addexcelorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        addorder=findViewById(R.id.order);
        addexcelorder=findViewById(R.id.excelorder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new ProgressDialog(this);
        orderList=new ArrayList<>();
        getOrder();
        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDeleteDialog();
            }
        });
        addexcelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seemore = new Intent(TrackOrderActivity.this,WorkOrder.class);

                startActivity(seemore);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
       MenuItem menuItemcal=menu.findItem(R.id.action_calender);
        android.support.v7.widget.SearchView searchView=(android.support.v7.widget.SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(this);
        menuItemcal.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getOrderbydate();
                return false;
            }
        });
        return true;
    }
    private void getOrderbydate()
    {
       //show calender

    }
    private void getOrder() {
        try {
            //String res="";
            progressDialog.setMessage("loading...");
            progressDialog.show();
            dataorder = FirebaseDatabase.getInstance().getReference("orders");
            //without filter
           dataorder.addListenerForSingleValueEvent(valueEventListener);

         /*   dataorder.addValueEventListener(new ValueEventListener() {
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
                    progressDialog.dismiss();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });*/
         //   new MyCourse.GETCourseList().execute(SharedPrefManager.getInstance(this).getUser().access_token);
         //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            // System.out.println("Error: " + e);
        }
    }
     ValueEventListener valueEventListener=new ValueEventListener() {
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
             progressDialog.dismiss();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {
             progressDialog.dismiss();
         }
     };
    private void showUpdateDeleteDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.order_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextWorkingDate = (EditText) dialogView.findViewById(R.id.editTextWorkingDate);
        final EditText editTextPartyName = (EditText) dialogView.findViewById(R.id.editTextPartyName);
        final EditText editTextLocation = (EditText) dialogView.findViewById(R.id.editTextLocation);
        final EditText editTextPINo = (EditText) dialogView.findViewById(R.id.editTextPINo);
        final EditText editTextworkOrderNo = (EditText) dialogView.findViewById(R.id.editTextworkOrderNo);
        final EditText editTextThick = (EditText) dialogView.findViewById(R.id.editTextThick);
        final EditText editTextColor = (EditText) dialogView.findViewById(R.id.editTextColor);
        final EditText editTextBTD = (EditText) dialogView.findViewById(R.id.editTextBTD);
        final EditText editTextSizeIn = (EditText) dialogView.findViewById(R.id.editTextSizeIn);
        final EditText editTextActualSize = (EditText) dialogView.findViewById(R.id.editTextActualSize);
        final EditText editTextHole = (EditText) dialogView.findViewById(R.id.editTextHole);
        final EditText editTextCut = (EditText) dialogView.findViewById(R.id.editTextCut);
        final EditText editTextQty = (EditText) dialogView.findViewById(R.id.editTextQty);
        final EditText editTextAreaInSQM= (EditText) dialogView.findViewById(R.id.editTextAreaInSQM);
        final EditText editTextOrderDate = (EditText) dialogView.findViewById(R.id.editTextOrderDate);
        final EditText editTextWeight = (EditText) dialogView.findViewById(R.id.editTextWeight);
        final EditText editTextRemark = (EditText) dialogView.findViewById(R.id.editTextRemark);

      /*  editTextWorkingDate.setText(addWorkorder.getWorkingDate());
        editTextPartyName.setText(addWorkorder.getPartyName());
        editTextLocation.setText(addWorkorder.getLocation());
        editTextPINo.setText(Integer.toString( addWorkorder.getPINo()));
        editTextworkOrderNo.setText(Integer.toString(  addWorkorder.getWorkOrderNo()));
        editTextThick.setText(Double.toString( addWorkorder.getGlassSpecificationThick()));
        editTextColor.setText( addWorkorder.getGlassSpecificationColor());
        editTextBTD.setText(addWorkorder.getGlassSpecificationBTD());
        editTextSizeIn.setText(addWorkorder.getSizeIn());
        editTextActualSize.setText(addWorkorder.getActualSize());
        editTextHole.setText(addWorkorder.getHole());
        editTextCut.setText(addWorkorder.getCut());
        editTextQty.setText(Integer.toString( addWorkorder.getQty()));
        editTextAreaInSQM.setText(Double.toString(addWorkorder.getAreaInSQM()));
        editTextOrderDate.setText(addWorkorder.getOrderDate());
        editTextWeight.setText(Double.toString(addWorkorder.getGWaight()));
        editTextRemark.setText(addWorkorder.getRemark());*/



        final Button buttonAdd = (Button) dialogView.findViewById(R.id.buttonAddOrder);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancel);

        dialogBuilder.setTitle("Add Order");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String WorkingDate = editTextWorkingDate.getText().toString().trim();
                String PartyName = editTextPartyName.getText().toString().trim();
                String Location = editTextLocation.getText().toString().trim();
                int PINo = Integer.parseInt( editTextPINo.getText().toString().trim());
                int workOrderNo =Integer.parseInt( editTextworkOrderNo.getText().toString().trim());
                double GlassSpecificationThick =Double.parseDouble( editTextThick.getText().toString().trim());
                String GlassSpecificationColor = editTextColor.getText().toString().trim();
                String GlassSpecificationBTD = editTextBTD.getText().toString().trim();
                String SizeIn = editTextSizeIn.getText().toString().trim();
                //String SizeMm = editTextSizeMm.getText().toString().trim();
                String ActualSize =  editTextActualSize.getText().toString().trim();
                String Hole = editTextHole.getText().toString().trim();
                String Cut =  editTextCut.getText().toString().trim();
                int Qty = Integer.parseInt( editTextQty.getText().toString().trim());
                double AreaInSQM =Double.parseDouble( editTextAreaInSQM.getText().toString().trim());
                String OrderDate =  editTextOrderDate.getText().toString().trim();
                double GWaight = Double.parseDouble( editTextWeight.getText().toString().trim());
                String Remark = editTextRemark.getText().toString().trim();
                addWorkorder=new DataWorkOrder(WorkingDate, PartyName, Location, PINo, workOrderNo, GlassSpecificationThick, GlassSpecificationColor, GlassSpecificationBTD, SizeIn, "0", ActualSize, Hole, Cut, Qty, AreaInSQM, OrderDate, 0, Remark,"");

                if (!TextUtils.isEmpty(WorkingDate)||!TextUtils.isEmpty(PartyName)
                        ||!TextUtils.isEmpty(Location)
                        ||PINo==0
                        ||workOrderNo==0
                        ||GlassSpecificationThick==0
                        ||!TextUtils.isEmpty(GlassSpecificationColor)
                        ||!TextUtils.isEmpty(GlassSpecificationBTD)
                        ||!TextUtils.isEmpty(SizeIn)

                        ||!TextUtils.isEmpty(Hole)
                        ||!TextUtils.isEmpty(Cut)
                        ||Qty==0
                        ||AreaInSQM==0
                        ||!TextUtils.isEmpty(OrderDate)
                        ||!TextUtils.isEmpty(Remark)
                        ) {
                    addOrder(addWorkorder);
                    b.dismiss();
                }
            }


        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
                /*
                 * we will code this method to delete the artist
                 * */

            }
        });
    }

    private boolean addOrder(DataWorkOrder finalWorkorder) {
        //getting the specified artist reference
        dataorder = FirebaseDatabase.getInstance().getReference("orders");
        String id = dataorder.push().getKey();
        finalWorkorder.setId(id);
        dataorder.child(id).setValue(finalWorkorder);
        Toast.makeText(getApplicationContext(), "Order Added", Toast.LENGTH_LONG).show();
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        dataorder = FirebaseDatabase.getInstance().getReference("orders");
        //filter byname
        Query  query= FirebaseDatabase.getInstance().getReference("orders")
                .orderByChild("partyName").equalTo(newText);
        query.addListenerForSingleValueEvent(valueEventListener);
        return false;
    }


}
