package com.example.awizom.glassind.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.glassind.Model.UserProfile;
import com.example.awizom.glassind.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserProfileAdapter  extends RecyclerView.Adapter<UserProfileAdapter.UserProfileHolder >  {
    private Context mCtx;

    //we are storing all the products in a list
    private List<UserProfile> userList;

    public UserProfileAdapter(Context mCtx, List<UserProfile> userList) {
        this.mCtx = mCtx;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_userlist, null);
        return new UserProfileHolder(view,mCtx,userList);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileHolder holder, int position) {
        UserProfile userProfile = userList.get(position);
        // if(order.getId().equals(""))
        //  imgurl="https://guidedevblob.blob.core.windows.net/"+course.getCourseID().toLowerCase()+"/"+course.getFileId()+"/"+course.getFileName().replace(' ','_').toLowerCase();
        //  else
        //  imgurl="https://www.homesbykimblanton.com/uploads/shared/images/library%202.jpg";
        //binding the data with the viewholder views
        // holder.textViewWorkingDate.setText("WorkingDate :"+order.getWorkingDate());
        holder. textViewEmail.setText("User Email\n"+userProfile.getEmail());
        holder. textViewRole.setText("Role\n"+userProfile.getRole());
        if(userProfile.isActive())
        {
            holder.buttonActivation.setText( "Un-Verify" );
        }
        else {
            holder.buttonActivation.setText( "Verify" );
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserProfileHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        TextView textViewEmail, textViewRole;
        Button buttonActivation;
        private Context mCtx;
        AlertDialog.Builder alert;
        String id,email;
        //we are storing all the products in a list
        private List<UserProfile> userList;
        DatabaseReference datauser;
        public UserProfileHolder(View itemView, Context mCtx, List<UserProfile> userList) {
            super( itemView );

            this.userList=userList;
            itemView.setOnClickListener(this);

            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewRole = itemView.findViewById( R.id.textViewRole);
            buttonActivation=itemView.findViewById(R.id.buttonActivation);
            buttonActivation.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            UserProfile  userProfile =this.userList.get(position);
            id=userProfile.getId();
            email=userProfile.getEmail();
            if (v.getId() ==buttonActivation.getId()) {
               /* alert = new AlertDialog.Builder(mCtx);
                alert.setTitle("");
                alert.setMessage("Are you sure you want to Activate "+email+" ?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {*/

                        datauser = FirebaseDatabase.getInstance().getReference("userprofile").child(id);
                        //updating artist
if(buttonActivation.getText().toString().equals( "Verify" ))
                        datauser.child("active").setValue(true);
else
if(buttonActivation.getText().toString().equals( "Un-Verify" ))
    datauser.child("active").setValue(false);

                      //  Toast.makeText(mCtx, email+" status changed to active", Toast.LENGTH_LONG).show();
              /*      }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();*/
            }
        }
    }
}
