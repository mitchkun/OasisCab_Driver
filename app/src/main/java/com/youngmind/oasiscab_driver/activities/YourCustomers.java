package com.youngmind.oasiscab_driver.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oasiscab_driver.R;
import com.youngmind.oasiscab_driver.adapters.CustomersAdapter;
import com.youngmind.oasiscab_driver.models.Customer;

import java.util.ArrayList;

public class YourCustomers extends AppCompatActivity {

    ArrayList<Customer> customers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_customers);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addCustomers();


        RecyclerView recyclerView = findViewById(R.id.your_customers_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CustomersAdapter(this, customers));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        finish();

    }

    public void addCustomers() {

        Customer customer = new Customer("Nelly Khumalo", "0.7 km away",
                "New Mall, Mbabane", "Mbabane North, Zone 2");
        customers.add(customer);

        customer = new Customer("Alex Muti", "5.8 km away",
                "Mpolonjeni, Mbabane", "Solanis, Mbabane");
        customers.add(customer);
    }
}
