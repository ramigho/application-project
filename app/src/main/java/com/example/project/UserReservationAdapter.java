package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.week10.R;
import java.util.ArrayList;


public class UserReservationAdapter extends RecyclerView.Adapter<UserReservationAdapter.UserReservationViewHolder> {
    ArrayList<UserReservation> resArray;

    public static class UserReservationViewHolder extends RecyclerView.ViewHolder{

        TextView lin1, lin2, rsTime;

        public UserReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            rsTime = (TextView) itemView.findViewById(R.id.rsTime);
            lin1 = (TextView) itemView.findViewById(R.id.lin1);
            lin2 = (TextView) itemView.findViewById(R.id.lin2);
        }
    }
    public UserReservationAdapter(ArrayList<UserReservation> userReservationArray) {
        resArray = userReservationArray;
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

        holder.rsTime.setText(latestRes.getTime());
        holder.lin1.setText(latestRes.getHall());
        holder.lin1.setText(latestRes.getInfoline());
    }

    @Override
    public int getItemCount() {
        return resArray.size();
    }
}