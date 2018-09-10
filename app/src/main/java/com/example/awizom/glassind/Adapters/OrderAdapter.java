package com.example.awizom.glassind.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.awizom.glassind.Model.DataWorkOrder;
import com.example.awizom.glassind.R;

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
      //  holder.textViewTitle.setText(order.getCourseName());
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

        TextView textViewTitle, textViewShortDesc, textViewPrice;
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
            textViewTitle = itemView.findViewById(R.id.editTextWorkingDate);
            textViewShortDesc = itemView.findViewById(R.id.editTextworkOrderNo);
            button=itemView.findViewById(R.id.button);
            textViewShortDesc.setMaxLines(2);
            textViewPrice = itemView.findViewById(R.id.editTextColor);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(this);
            textViewTitle.setOnClickListener(this);
            button.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
          DataWorkOrder  order =this.orderList.get(position);

        }
    }
}
