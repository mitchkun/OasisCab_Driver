package com.example.oasiscab_driver;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.MyViewHolder>  {

    private List<Customer> customers;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView customerName;
        public TextView distanceAway;
        private TextView pickUpLocation;
        private TextView dropOffLocation;
        private TextView acceptButton;
        private TextView declineButton;



        public MyViewHolder(View view) {
            super(view);
            customerName = view.findViewById(R.id.customer_name);
            distanceAway = view.findViewById(R.id.distance_away);
            pickUpLocation =view.findViewById(R.id.pick_up_location);
            dropOffLocation= view.findViewById(R.id.drop_off_location);
            acceptButton = view.findViewById(R.id.accept_btn);
            declineButton = view.findViewById(R.id.decline_btn);
        }
    }


    public CustomersAdapter(Context context, List<Customer>  customers) {
        this.customers = customers;
        this.context = context;
    }

    @Override
    public CustomersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customers_list, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.customerName.setText(customer.getCustomerName());
        holder.distanceAway.setText(customer.getDistanceAway());
        holder.pickUpLocation.setText(customer.getPickUpLocation());
        holder.dropOffLocation.setText(customer.getDropOffLocation());


    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
}
