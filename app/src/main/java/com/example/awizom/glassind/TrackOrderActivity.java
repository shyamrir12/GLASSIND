package com.example.awizom.glassind;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.glassind.Adapters.OrderAdapter;
import com.example.awizom.glassind.Fragment.DatePickerFragment;
import com.example.awizom.glassind.Model.DataWorkOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.support.v7.widget.SearchView;

public class TrackOrderActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener,DatePickerDialog.OnDateSetListener {
    RecyclerView recyclerView;
    ProgressDialog progressDialog ;
    DatabaseReference dataorder;
    List<DataWorkOrder> orderList;
    OrderAdapter adapter;
    DataWorkOrder addWorkorder;
    int CAMRA_REQUEST_CODE=0;
    String filename="";
    Button addorder,addexcelorder;
    String currentdateString;
    String role;
TextView textViewwelcomeorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        role=getIntent().getStringExtra("role");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        addorder=findViewById(R.id.order);
        addexcelorder=findViewById(R.id.excelorder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new ProgressDialog(this);
        orderList=new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
         currentdateString=formatter.format(date);
         textViewwelcomeorder=findViewById( R.id. textViewwelcomeorder);


        getOrder();

        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equals( "Admin" ))
                showUpdateDeleteDialog();
                else
                    Toast.makeText(getApplicationContext(), "you are not authorized", Toast.LENGTH_SHORT).show();
            }
        });
        addexcelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equals( "Admin" ))
                {
                Intent seemore = new Intent(TrackOrderActivity.this,WorkOrder.class);
                startActivity(seemore);
                }
                else
                {  Toast.makeText(getApplicationContext(), "you are not authorized", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                DialogFragment datepicker=new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"date picker");
                return false;
            }
        });
        return true;
    }

    private void getOrder() {
        try {
            //String res="";

            progressDialog.setMessage("loading...");
            progressDialog.show();
            dataorder = FirebaseDatabase.getInstance().getReference("orders");
            //without filter
            // dataorder.addListenerForSingleValueEvent(valueEventListener);
            Query  query= FirebaseDatabase.getInstance().getReference("orders")
                    .orderByChild("workingDate").equalTo(currentdateString);
            // query.addListenerForSingleValueEvent(valueEventListener);
            query.addValueEventListener(new ValueEventListener() {

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
                    adapter=new OrderAdapter(TrackOrderActivity.this, orderList,role);
                   //attaching adapter to the listview
                    recyclerView.setAdapter(adapter);
                    if(adapter.getItemCount()==0)
                    {
                        textViewwelcomeorder.setVisibility( View.VISIBLE );
                        textViewwelcomeorder.setText( currentdateString+" \ndon't have any work order" );
                        // today don't have any work order
                    }
                    else
                    {
                        textViewwelcomeorder.setVisibility( View.GONE );
                    }
                    progressDialog.dismiss();
                     //when u want to see only perticuller update then add notify hear and
                    //creating adapter and attaching adapter to the listview at oncreate method
                   // adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
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

    private void getOrderbyName(String partynmae) {
        try {
            //String res="";

            progressDialog.setMessage("loading...");
            progressDialog.show();
            dataorder = FirebaseDatabase.getInstance().getReference("orders");
            //without filter
            // dataorder.addListenerForSingleValueEvent(valueEventListener);
            Query  query;
            if(partynmae.trim().length()==0)
            {
                 query= FirebaseDatabase.getInstance().getReference("orders")
                        .orderByChild("workingDate").equalTo(currentdateString);
            }
            else {
                 query = FirebaseDatabase.getInstance().getReference("orders")
                        .orderByChild("partyName").startAt(partynmae);
            }
            // query.addListenerForSingleValueEvent(valueEventListener);
            query.addValueEventListener(new ValueEventListener() {
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
                    adapter=new OrderAdapter(TrackOrderActivity.this, orderList,role);
                    //attaching adapter to the listview
                    recyclerView.setAdapter(adapter);

                    progressDialog.dismiss();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
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
             adapter=new OrderAdapter(TrackOrderActivity.this, orderList,role);
             //attaching adapter to the listview
             recyclerView.setAdapter(adapter);
             if(adapter.getItemCount()==0)
             {
                 textViewwelcomeorder.setVisibility( View.VISIBLE );
                 textViewwelcomeorder.setText( currentdateString+" \ndon't have any work order" );
                 // today don't have any work order
             }
             else
             {
                 textViewwelcomeorder.setVisibility( View.GONE );
             }
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

       editTextWorkingDate.setText(currentdateString);
      //editTextPartyName.setText(addWorkorder.getPartyName());
       // editTextLocation.setText(addWorkorder.getLocation());
        editTextPINo.setText("0");
        editTextworkOrderNo.setText("0");
        editTextThick.setText("0");
       // editTextColor.setText( addWorkorder.getGlassSpecificationColor());
        //editTextBTD.setText(addWorkorder.getGlassSpecificationBTD());
       // editTextSizeIn.setText(addWorkorder.getSizeIn());
        //editTextActualSize.setText(addWorkorder.getActualSize());
       // editTextHole.setText(addWorkorder.getHole());
        //editTextCut.setText(addWorkorder.getCut());
        editTextQty.setText("0");
        editTextAreaInSQM.setText("0");
        editTextOrderDate.setText(currentdateString);
        editTextWeight.setText("0");
       // editTextRemark.setText(addWorkorder.getRemark());



        final Button buttonAdd = (Button) dialogView.findViewById(R.id.buttonAddOrder);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancel);

        dialogBuilder.setTitle("Add Order");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PINo=0,workOrderNo=0,Qty=0;
                double GlassSpecificationThick=0,AreaInSQM =0,GWaight=0;
                String WorkingDate = editTextWorkingDate.getText().toString().trim();
                String PartyName = editTextPartyName.getText().toString().trim();
                String Location = editTextLocation.getText().toString().trim();
                if(TextUtils.isEmpty(editTextPINo.getText().toString())) {

                    editTextPINo.setError("input correct value");
                    editTextPINo.setFocusable( true );
                    return;
                }
                else
                {
                    PINo = Integer.parseInt( editTextPINo.getText().toString().trim() );
                }
                if(TextUtils.isEmpty(editTextworkOrderNo.getText().toString())) {
                    editTextworkOrderNo.setError("input correct value");
                    editTextworkOrderNo.setFocusable( true );
                    return;
                }
                else
                {
                    workOrderNo = Integer.parseInt( editTextworkOrderNo.getText().toString().trim() );
                }

                if(TextUtils.isEmpty(editTextThick.getText().toString())) {

                    editTextThick.setError("input correct value");
                    editTextThick.setFocusable( true );
                    return;
                }
                else
                {
                    GlassSpecificationThick = Double.parseDouble( editTextThick.getText().toString().trim() );

                }

                String GlassSpecificationColor = editTextColor.getText().toString().trim();
                String GlassSpecificationBTD = editTextBTD.getText().toString().trim();
                String SizeIn = editTextSizeIn.getText().toString().trim();
                //String SizeMm = editTextSizeMm.getText().toString().trim();
                String ActualSize =  editTextActualSize.getText().toString().trim();
                String Hole = editTextHole.getText().toString().trim();
                String Cut =  editTextCut.getText().toString().trim();
                if(TextUtils.isEmpty(editTextQty.getText().toString())) {
                    editTextQty.setError("input correct value");
                    editTextQty.setFocusable( true );
                    return;
                }
                else
                {Qty = Integer.parseInt( editTextQty.getText().toString().trim() );

                }
                if(TextUtils.isEmpty(editTextAreaInSQM.getText().toString())) {
                    editTextQty.setError("input correct value");
                    editTextQty.setFocusable( true );
                    return;
                }
                else
                {
                    AreaInSQM = Double.parseDouble( editTextAreaInSQM.getText().toString().trim() );
                }
                String OrderDate =  editTextOrderDate.getText().toString().trim();
                if(TextUtils.isEmpty(editTextWeight.getText().toString())) {
                    editTextWeight.setError("input correct value");
                    editTextWeight.setFocusable( true );
                    return;
                }
                else
                {
                    GWaight = Double.parseDouble( editTextWeight.getText().toString().trim() );
                }
                String Remark = editTextRemark.getText().toString().trim();
                DataWorkOrder addWorkorder;
                if (TextUtils.isEmpty(WorkingDate))
                {
                    editTextWorkingDate.setError("Working Date is required");
                    editTextWorkingDate.setFocusable( true );
                    return;
                }
                if (!WorkingDate.matches("([0-9]{2})/([0-9]{2})/([0-9]{2})"))
                {
                    editTextWorkingDate.setError("input date in correct format(DD/MM/YY)");
                    editTextWorkingDate.setFocusable( true );
                    return;
                }
                if (TextUtils.isEmpty(OrderDate))
                {
                    editTextOrderDate.setError("Working Date is required");
                    editTextOrderDate.setFocusable( true );
                    return;
                }


                addWorkorder=new DataWorkOrder(WorkingDate, PartyName, Location, PINo, workOrderNo, GlassSpecificationThick, GlassSpecificationColor, GlassSpecificationBTD, SizeIn, "0", ActualSize, Hole, Cut, Qty, AreaInSQM, OrderDate, 0, Remark,"");


                    addOrder(addWorkorder);
                    b.dismiss();

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
    public boolean onQueryTextChange(final String newText) {

       //use query for filter
       //getOrderbyName(newText .toLowerCase());
        //filter byname
       // Query  query= FirebaseDatabase.getInstance().getReference("orders")
      //          .orderByChild("partyName").equalTo(newText);
      //  query.addListenerForSingleValueEvent(valueEventListener);

       dataorder = FirebaseDatabase.getInstance().getReference("orders");
       dataorder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //iterating through all the nodes
                if (newText.trim().length()==0) {
                    orderList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting artist
                        DataWorkOrder order = postSnapshot.getValue(DataWorkOrder.class);
                        //adding artist to the list

                        if (order.getWorkingDate().equals(currentdateString))
                            orderList.add(order);


                    }

                }
                else {

                    orderList.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting artist
                        DataWorkOrder order = postSnapshot.getValue(DataWorkOrder.class);
                        //adding artist to the list

                        if (order.getPartyName().toLowerCase().contains(newText.toLowerCase()))
                            orderList.add(order);


                    }
                }
                //creating adapter
                adapter=new OrderAdapter(TrackOrderActivity.this, orderList,role);
                //attaching adapter to the listview
                recyclerView.setAdapter(adapter);
                textViewwelcomeorder.setVisibility( View.GONE );
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });
        return true;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        //String dateString= DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
       // Date date = new Date();
        currentdateString=formatter.format(c.getTime());
        //String dateString=formatter.format(date);
       // Toast.makeText(this, dateString, Toast.LENGTH_SHORT).show();
        getOrder();
        recyclerView.setAdapter(adapter);
    }
}
