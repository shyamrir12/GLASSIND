package com.example.awizom.glassind.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.awizom.glassind.Fragment.Fragment_Drawing;
import com.example.awizom.glassind.Model.DataWorkOrder;
import com.example.awizom.glassind.R;
import com.example.awizom.glassind.TrackOrderActivity;

import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder > {

    private Context mCtx;

    //we are storing all the products in a list
    private List<DataWorkOrder> orderList;




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
        holder.editTextWorkingDate.setText(order.getWorkingDate());
        holder. editTextPartyName.setText(order.getPartyName());
        holder. editTextLocation.setText(order.getLocation());
        holder.editTextPINo.setText(Integer.toString( order.getPINo()));
        holder. editTextworkOrderNo.setText(Integer.toString(  order.getWorkOrderNo()));
        holder.editTextThick.setText(Double.toString( order.getGlassSpecificationThick()));
        holder. editTextColor.setText( order.getGlassSpecificationColor());
        holder. editTextBTD.setText(order.getGlassSpecificationBTD());
        holder.editTextSizeIn.setText(order.getSizeIn());
        holder. editTextActualSize.setText(order.getActualSize());
        holder. editTextHole.setText(order.getHole());
        holder. editTextCut.setText(order.getCut());
        holder. editTextQty.setText(Integer.toString( order.getQty()));
        holder. editTextAreaInSQM.setText(Double.toString(order.getAreaInSQM()));
        holder. editTextOrderDate.setText(order.getOrderDate());
        holder. editTextWeight.setText(Double.toString(order.getGWaight()));
        holder. editTextRemark.setText(order.getRemark());


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
    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView editTextWorkingDate, editTextPartyName, editTextLocation,editTextPINo,editTextworkOrderNo,editTextThick
                ,editTextColor,editTextBTD,editTextSizeIn,editTextActualSize,editTextHole,
                editTextCut,editTextQty,editTextAreaInSQM,editTextOrderDate,editTextWeight,editTextRemark;
        Button button;

        private Context mCtx;
        //we are storing all the products in a list
        private List<DataWorkOrder> orderList;
        ImageView imageView;

        public OrderViewHolder(View itemView, Context mCtx, List<DataWorkOrder> orderList) {
            super(itemView);
            this.mCtx=mCtx;
            this.orderList=orderList;
            itemView.setOnClickListener(this);
            editTextWorkingDate = itemView.findViewById(R.id.editTextWorkingDate);
            editTextPartyName = itemView.findViewById(R.id.editTextPartyName);
            editTextLocation = itemView.findViewById(R.id.editTextLocation);
            editTextPINo = itemView.findViewById(R.id.editTextPINo);
            editTextworkOrderNo = itemView.findViewById(R.id.editTextworkOrderNo);
            editTextThick = itemView.findViewById(R.id.editTextThick);

            editTextColor = itemView.findViewById(R.id.editTextColor);
            editTextBTD = itemView.findViewById(R.id.editTextBTD);
            editTextSizeIn = itemView.findViewById(R.id.editTextSizeIn);
            editTextActualSize = itemView.findViewById(R.id.editTextActualSize);
            editTextHole = itemView.findViewById(R.id.editTextHole);
            editTextCut = itemView.findViewById(R.id.editTextCut);

            editTextQty = itemView.findViewById(R.id.editTextQty);
            editTextAreaInSQM = itemView.findViewById(R.id.editTextAreaInSQM);
            editTextWeight = itemView.findViewById(R.id.editTextWeight);
            editTextOrderDate = itemView.findViewById(R.id.editTextOrderDate);
            editTextRemark = itemView.findViewById(R.id.editTextRemark);


            imageView = itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(this);
            button=itemView.findViewById(R.id.button);
            button.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
          DataWorkOrder  order =this.orderList.get(position);

            // uploadData.add(new DataWorkOrder("", PartyName, Location, PINo, workOrderNo, GlassSpecificationThick, GlassSpecificationColor, GlassSpecificationBTD, SizeIn, SizeMm, ActualSize, Hole, Cut, Qty, AreaInSQM, OrderDate, 0, Remark));
           // showUpdateDeleteDialog();
        }


    }
}
