package com.example.awizom.glassind.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.awizom.glassind.DrawingActivity;
import com.example.awizom.glassind.Fragment.Fragment_Drawing;
import com.example.awizom.glassind.Model.DataWorkOrder;
import com.example.awizom.glassind.R;
import com.example.awizom.glassind.TrackOrderActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder > {

    private Context mCtx;

    //we are storing all the products in a list
    private List<DataWorkOrder> orderList;

    //If file exist in storage this works.




    public OrderAdapter(Context mCtx, List<DataWorkOrder> orderList) {
        this.mCtx = mCtx;
        this.orderList = orderList;


    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_track_order_list, null);
        return new OrderViewHolder(view,mCtx,orderList);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        String imgurl="";
        //getting the product of the specified position
      DataWorkOrder order = orderList.get(position);
       // if(order.getId().equals(""))
          //  imgurl="https://guidedevblob.blob.core.windows.net/"+course.getCourseID().toLowerCase()+"/"+course.getFileId()+"/"+course.getFileName().replace(' ','_').toLowerCase();
      //  else
          //  imgurl="https://www.homesbykimblanton.com/uploads/shared/images/library%202.jpg";
        //binding the data with the viewholder views
       // holder.textViewWorkingDate.setText("WorkingDate :"+order.getWorkingDate());
        holder. textViewPartyName.setText("PartyName\n"+order.getPartyName());
        holder. textViewLocation.setText("Location\n"+order.getLocation());
        holder.textViewPINo.setText("PINo \n"+Integer.toString( order.getPINo()));
        holder. textViewworkOrderNo.setText("WorkOrderNo \n"+Integer.toString(  order.getWorkOrderNo()));
        holder.textViewThick.setText("Thick\n"+Double.toString( order.getGlassSpecificationThick()));
        holder. textViewColor.setText("Color\n"+ order.getGlassSpecificationColor());
        holder. textViewBTD.setText("BTD\n"+order.getGlassSpecificationBTD());
        holder.textViewSizeIn.setText("SizeIn\n"+order.getSizeIn());
        holder. textViewActualSize.setText("ActualSize\n"+order.getActualSize());
        holder. textViewHole.setText("Hole\n"+order.getHole());
        holder. textViewCut.setText("Cut\n"+order.getCut());
        holder. textViewQty.setText("Qty\n"+Integer.toString( order.getQty()));
        holder. textViewAreaInSQM.setText("AreaInSQM\n"+Double.toString(order.getAreaInSQM()));
        holder. textViewOrderDate.setText("OrderDate\n"+order.getOrderDate());
        holder. textViewWeight.setText("Waight\n"+Double.toString(order.getGWaight()));
        holder. textViewRemark.setText("Remark\n"+order.getRemark());
        if(order.getDrawing().trim().length()==0)
        Glide.with(mCtx).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRA0vf_EXkL0RKmM5718bM1M7742qvMsRCEwvoLbOeiBTACc4kJYA").into(holder.imageView);
        else
        Glide.with(mCtx).load(order.getDrawing()).into(holder.imageView);

        //cut,grind,fab,temp,disp,reject,linerdept;
        if(order.isDEPTDREJECT())
        {
            holder.linerdept.setBackgroundColor(Color.RED);

        }
        else {
            if (order.isDEPTCUT()) {
                holder.cut.setBackgroundColor(Color.GREEN );
            }
            if (order.isDEPTGRIND()) {
                holder.grind.setBackgroundColor(Color.GREEN);
            }
            if (order.isDEPTFAB()) {
                holder.fab.setBackgroundColor(Color.GREEN);
            }
            if (order.isDEPTTEMP()) {
                holder.temp.setBackgroundColor(Color.GREEN);
            }
            if (order.isDEPTDISP()) {
                holder.disp.setBackgroundColor(Color.GREEN);
            }

        }
        if(order.getRemark().equals("RFG"))
        {
            holder.grind.setBackgroundColor(Color.parseColor("#00BFFF"));
        }
        else if (order.getRemark().equals("RFF")) {
            holder.fab.setBackgroundColor(Color.parseColor("#00BFFF"));
        }
        else if (order.getRemark().equals("RFT")) {
            holder.temp.setBackgroundColor(Color.parseColor("#00BFFF"));
        }
        else if (order.getRemark().equals("RFD")) {
            holder.disp.setBackgroundColor(Color.parseColor("#00BFFF"));
        }

        else if (order.getRemark().trim().length()==0) {
            holder.cut.setBackgroundColor(Color.parseColor("#00BFFF"));
        }

        //holder.textViewTitle.setText(order.getCourseName());
      //  holder.textViewShortDesc.setText(order.getCourseDescription());

      //  holder.textViewPrice.setText(String.valueOf(course.getCoursePrice()+" "+course.getCurrency()));
      //  Picasso.with(this.mCtx).load(imgurl).into(holder.imageView);

        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable();

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    class OrderViewHolder extends RecyclerView.ViewHolder  implements View.OnLongClickListener,View.OnClickListener  {

        AlertDialog.Builder alert;
       String id,dept,deptcolname,status;
        TextView textViewWorkingDate, textViewPartyName, textViewLocation,textViewPINo,textViewworkOrderNo,textViewThick
                ,textViewColor,textViewBTD,textViewSizeIn,textViewActualSize,textViewHole,
                textViewCut,textViewQty,textViewAreaInSQM,textViewOrderDate,textViewWeight,textViewRemark;
        Button button,buttonCut,buttonGrind,buttonFab,buttonTemp,buttonDisp,buttonReject;

        LinearLayout cut,grind,fab,temp,disp,reject,linerdept;
        private Context mCtx;
        //we are storing all the products in a list
        private List<DataWorkOrder> orderList;
        ImageView imageView;

        public OrderViewHolder(View itemView, Context mCtx, List<DataWorkOrder> orderList) {
            super(itemView);
            this.mCtx=mCtx;
            this.orderList=orderList;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
           // textViewWorkingDate = itemView.findViewById(R.id.textViewWorkingDate);
            textViewPartyName = itemView.findViewById(R.id.textViewPartyName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewPINo = itemView.findViewById(R.id.textViewPINo);
            textViewworkOrderNo = itemView.findViewById(R.id.textViewworkOrderNo);
            textViewThick = itemView.findViewById(R.id.textViewThick);

            textViewColor = itemView.findViewById(R.id.textViewColor);
            textViewBTD = itemView.findViewById(R.id.textViewBTD);
            textViewSizeIn = itemView.findViewById(R.id.textViewSizeIn);
            textViewActualSize = itemView.findViewById(R.id.textViewActualSize);
            textViewHole = itemView.findViewById(R.id.textViewHole);
            textViewCut = itemView.findViewById(R.id.textViewCut);

            textViewQty = itemView.findViewById(R.id.textViewQty);
            textViewAreaInSQM = itemView.findViewById(R.id.textViewAreaInSQM);
            textViewWeight = itemView.findViewById(R.id.textViewWeight);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            textViewRemark = itemView.findViewById(R.id.textViewRemark);

            cut = itemView.findViewById(R.id.cut);
            grind = itemView.findViewById(R.id.grind);
            fab = itemView.findViewById(R.id.fab);
            temp = itemView.findViewById(R.id.temp);
            disp = itemView.findViewById(R.id.disp);
            reject = itemView.findViewById(R.id.reject);
            linerdept = itemView.findViewById(R.id. linerdept);


            imageView = itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(this);
           // button=itemView.findViewById(R.id.button);
           // button.setOnClickListener(this);
            buttonCut=itemView.findViewById(R.id.buttonCut);
            buttonCut.setOnClickListener(this);
            buttonGrind=itemView.findViewById(R.id.buttonGrind);
            buttonGrind.setOnClickListener(this);
            buttonFab=itemView.findViewById(R.id.buttonFab);
            buttonFab.setOnClickListener(this);
            buttonTemp=itemView.findViewById(R.id.buttonTemp);
            buttonTemp.setOnClickListener(this);
            buttonDisp=itemView.findViewById(R.id.buttonDisp);
            buttonDisp.setOnClickListener(this);
            buttonReject=itemView.findViewById(R.id.buttonReject);
            buttonReject.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
          DataWorkOrder  order =this.orderList.get(position);
            id=order.getId();
            if (v.getId() ==imageView.getId()) {
                Intent intent=new Intent(mCtx, DrawingActivity.class);
                intent.putExtra("filename",order.getId());
                intent.putExtra("livefilepath",order.getDrawing());
                intent.putExtra("pino",order.getPINo());
                intent.putExtra("partyname",order.getPartyName());
                this.mCtx.startActivity(intent);
            }
            if (v.getId() == buttonCut.getId()) {
                dept="CUT Department";
                deptcolname="deptcut";
                status="RFG";
               updateArtist();
            }
            if (v.getId() == buttonGrind.getId()) {
                dept="GRIND Department";
                deptcolname="deptgrind";
                status="RFF";
                updateArtist();
            }
            if (v.getId() == buttonFab.getId()) {
                dept="FAB Department";
                deptcolname="deptfab";
                status="RFT";
                updateArtist();
            }
            if (v.getId() == buttonTemp.getId()) {
                dept="TEMP Department";
                deptcolname="depttemp";
                status="RFD";
                updateArtist();
            }
            if (v.getId() == buttonDisp.getId()) {
                dept="DISP Department";
                deptcolname="deptdisp";
                status="OC";
                updateArtist();
            }
            if (v.getId() == buttonReject.getId()) {
                dept="Reject Department";
                deptcolname="deptdreject";
                status="Reject";
                updateArtist();
            }

            // uploadData.add(new DataWorkOrder("", PartyName, Location, PINo, workOrderNo, GlassSpecificationThick, GlassSpecificationColor, GlassSpecificationBTD, SizeIn, SizeMm, ActualSize, Hole, Cut, Qty, AreaInSQM, OrderDate, 0, Remark));
           // showUpdateDeleteDialog();
        }

        private boolean updateArtist() {
            alert = new AlertDialog.Builder(mCtx);
            alert.setTitle(dept);
            alert.setMessage("Are you sure you want to change the "+dept+" status to OK?");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete
                    //getting the specified artist reference
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("orders").child(id);
                    //updating artist
                    dR.child(deptcolname).setValue(true);
                    dR.child("remark").setValue(status);
                    Toast.makeText(mCtx, dept+" status changed to OK", Toast.LENGTH_LONG).show();
                }
            });
            alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // close dialog
                    dialog.cancel();
                }
            });
            alert.show();

            return true;
        }

        @Override
        public boolean onLongClick(View v) {

            int position=getAdapterPosition();
            DataWorkOrder  order =this.orderList.get(position);
            id=order.getId();
            if (v.getId() ==itemView.getId()) {
                showUpdateDeleteDialog(order);
            }
            return true;
        }

        private void showUpdateDeleteDialog(DataWorkOrder  orderp) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mCtx);
            final String drawing=orderp.getDrawing();
            final boolean dEPTCUT=orderp.isDEPTCUT();
            final boolean dEPTGRIND=orderp.isDEPTCUT();
            final boolean dEPTFAB=orderp.isDEPTCUT();
            final boolean dEPTTEMP=orderp.isDEPTCUT();
            final boolean  dEPTDISP=orderp.isDEPTCUT();
            final  boolean dEPTDREJECT=orderp.isDEPTCUT();
            LayoutInflater inflater = LayoutInflater.from(mCtx);;
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
            final CheckBox checkBox = (CheckBox) dialogView.findViewById(R.id.checkBox);

            checkBox.setVisibility(View.VISIBLE);

            editTextWorkingDate.setText(orderp.getWorkingDate());
            editTextPartyName.setText(orderp.getPartyName());
            editTextLocation.setText(orderp.getLocation());
            editTextPINo.setText(Integer.toString( orderp.getPINo()));
            editTextworkOrderNo.setText(Integer.toString(  orderp.getWorkOrderNo()));
            editTextThick.setText(Double.toString( orderp.getGlassSpecificationThick()));
            editTextColor.setText( orderp.getGlassSpecificationColor());
            editTextBTD.setText(orderp.getGlassSpecificationBTD());
            editTextSizeIn.setText(orderp.getSizeIn());
            editTextActualSize.setText(orderp.getActualSize());
            editTextHole.setText(orderp.getHole());
            editTextCut.setText(orderp.getCut());
            editTextQty.setText(Integer.toString( orderp.getQty()));
            editTextAreaInSQM.setText(Double.toString(orderp.getAreaInSQM()));
            editTextOrderDate.setText(orderp.getOrderDate());
            editTextWeight.setText(Double.toString(orderp.getGWaight()));
            editTextRemark.setText(orderp.getRemark());



            final Button buttonAdd = (Button) dialogView.findViewById(R.id.buttonAddOrder);
            final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancel);
             buttonAdd.setText("Edit");
            dialogBuilder.setTitle("Edit Order");
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
                    DataWorkOrder addWorkorder;
                    if(checkBox.isChecked()) {

                        addWorkorder = new DataWorkOrder(WorkingDate, PartyName, Location, PINo, workOrderNo, GlassSpecificationThick, GlassSpecificationColor, GlassSpecificationBTD, SizeIn, "0", ActualSize, Hole, Cut, Qty, AreaInSQM, OrderDate, 0, Remark, drawing );

                    }
                    else {
                        addWorkorder = new DataWorkOrder(WorkingDate, PartyName, Location, PINo, workOrderNo, GlassSpecificationThick, GlassSpecificationColor, GlassSpecificationBTD, SizeIn, "0", ActualSize, Hole, Cut, Qty, AreaInSQM, OrderDate, 0, Remark, drawing,
                                dEPTCUT, dEPTGRIND, dEPTFAB, dEPTTEMP, dEPTDISP, dEPTDREJECT);
       }

                    if (!TextUtils.isEmpty(WorkingDate)) {
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
            DatabaseReference dataorder = FirebaseDatabase.getInstance().getReference("orders").child(id);

            dataorder.setValue(finalWorkorder);
            Toast.makeText(mCtx, "Order Updated", Toast.LENGTH_LONG).show();
            return true;
        }
    }
}
