package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.week10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;



public class UserReservationAdapter extends RecyclerView.Adapter<UserReservationAdapter.UserReservationViewHolder> {
    ArrayList<UserReservation> resArray;
    Context context;


    public static class UserReservationViewHolder extends RecyclerView.ViewHolder{
        Button editRes;
        TextView lin1, lin2, rsTime, rsDate;

        public UserReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            rsTime = (TextView) itemView.findViewById(R.id.rsTime);
            lin1 = (TextView) itemView.findViewById(R.id.lin1);
            lin2 = (TextView) itemView.findViewById(R.id.lin2);
            rsDate = (TextView) itemView.findViewById(R.id.rsDate);
            editRes = (Button) itemView.findViewById(R.id.editRes);
        }
    }
    public UserReservationAdapter(ArrayList<UserReservation> userReservationArray, Context context) {
        resArray = userReservationArray;
        this.context = context;
    }

    @NonNull
    @Override
    public UserReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler2, parent, false);
        UserReservationViewHolder urvh = new UserReservationViewHolder(v);
        return urvh;
    }
    @Override
    public void onBindViewHolder(@NonNull UserReservationViewHolder holder, int position) {
        UserReservation latestRes = resArray.get(position);

        /* Show today's reservations */
        holder.rsTime.setText(latestRes.getTime());
        holder.rsDate.setText(latestRes.getDate());
        holder.lin1.setText(latestRes.getHall());
        holder.lin2.setText(latestRes.getInfoline());

        /* Apumuuttujat */
        final String reserveTime = latestRes.getTime();
        final String reserveDate = latestRes.getDate();
        final String reserveHall = latestRes.getHall();
        final int position_value = position;

        holder.editRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editRes = new Intent(context, EditReservationActivity.class);
                editRes.putExtra("time", reserveTime);
                editRes.putExtra("date", reserveDate);
                editRes.putExtra("hall", reserveHall);
                editRes.putExtra("position", position_value);
                editRes.addFlags(editRes.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(editRes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resArray.size();
    }
}