package com.locdhph46788.asm1_ph46788;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    CarAdapter carAdapter;
    RecyclerView rcvCar;
    List<CarModel> listCar;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        rcvCar = findViewById(R.id.rcv_car);
        btnAdd = findViewById(R.id.fab_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAdd();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<CarModel>> call = apiService.getCars();

        call.enqueue(new Callback<List<CarModel>>() {
            @Override
            public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                if (response.isSuccessful()) {
                    listCar = response.body();
                    carAdapter = new CarAdapter(getApplicationContext(), listCar);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    rcvCar.setLayoutManager(linearLayoutManager);
                    rcvCar.setAdapter(carAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CarModel>> call, Throwable t) {
                Log.e("GetCar", t.getMessage());
            }
        });

    }

    public void DialogAdd() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_add_car, null);
        alert.setView(view);
        AlertDialog alertDialog = alert.create();
        EditText edtName = view.findViewById(R.id.edt_name);
        EditText edtPrice = view.findViewById(R.id.edt_price);
        EditText edtQuantity = view.findViewById(R.id.edt_quantity);
        EditText edtStatus = view.findViewById(R.id.edt_status);


        Button btnAdd = view.findViewById(R.id.btn_add_car);

        btnAdd.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String price = edtPrice.getText().toString();
            String quantity = edtQuantity.getText().toString();
            String status = edtStatus.getText().toString();
            if (name.equals("")) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            } else if (price.equals("")) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
            } else if (quantity.equals("")) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập số lương", Toast.LENGTH_SHORT).show();
            } else if (status.equals("")) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập tình trạng", Toast.LENGTH_SHORT).show();
            } else {
                if (true) {
                    CarModel car = new CarModel(name, price, quantity, status);
                } else {
                    Toast.makeText(getApplicationContext(), "Không thêm được", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();

    }

}