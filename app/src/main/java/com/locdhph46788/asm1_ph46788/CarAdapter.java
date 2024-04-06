package com.locdhph46788.asm1_ph46788;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.locdhph46788.asm1_ph46788.Model.CarModel;
import com.locdhph46788.asm1_ph46788.Model.Response;
import com.locdhph46788.asm1_ph46788.Services.APIServices;
import com.locdhph46788.asm1_ph46788.Services.HttpRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    Context context;
    List<CarModel> listCar;


    public CarAdapter(Activity context, List<CarModel> listCar) {
        this.context = context;
        this.listCar = listCar;
    }


    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.car_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder holder, int position) {
        CarModel car = listCar.get(position);
        holder.tvId.setText(car.get_id());
        holder.tvName.setText(car.getName());
        holder.tvPrice.setText(car.getPrice() + "$");
        holder.tvQuantity.setText(car.getQuantity() + "");
        if (Boolean.parseBoolean(car.getStatuss())) {
            holder.tvStatus.setText("Mới");
        } else {
            holder.tvStatus.setText("Cũ");
        }
//        String url = car.getImage().get(position);
//        String newUrl = url.replace("localhost", "10.0.2.2");
//        Glide.with(context)
//                .load(newUrl)
//                .thumbnail(Glide.with(context).load(R.drawable.ic_launcher_background))
//                .into(holder.ivCar);

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = car.get_id();
                CarModel objUpdateCar = listCar.get(holder.getAdapterPosition());
                DialogUpdateCar(id, objUpdateCar);

            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("Xác nhận xóa xe  !");

                alert.setMessage("Bạn có muốn xóa xe có id:" + car.get_id());
                alert.setCancelable(false);
                alert.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String id = car.get_id();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(APIServices.DOMAIN)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        APIServices apiService = retrofit.create(APIServices.class);

                        Call<List<CarModel>> call = apiService.delCar(id);

                        call.enqueue(new Callback<List<CarModel>>() {
                            @Override
                            public void onResponse(Call<List<CarModel>> call, retrofit2.Response<List<CarModel>> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyDataSetChanged();
                                    listCar = response.body();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<CarModel>> call, Throwable t) {
                                Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                                Log.e("zzzzz", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });

                alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listCar.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCar;
        TextView tvId, tvName, tvPrice, tvQuantity, tvStatus;
        Button btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCar = itemView.findViewById(R.id.img_car);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    Callback<Response<CarModel>> responseCar = new Callback<Response<CarModel>>() {
        @Override
        public void onResponse(Call<Response<CarModel>> call, retrofit2.Response<Response<CarModel>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    listCar.add(response.body().getData());
                }
            }
        }

        @Override
        public void onFailure(Call<Response<CarModel>> call, Throwable t) {
            Log.e("zzzzzzzzzz", "onFailure: " + t.getMessage());
        }
    };

    public void DialogUpdateCar(String idUpdate, CarModel objUpdateCar) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_car, null);
        alert.setView(view);


        EditText edtName = view.findViewById(R.id.edt_name);
        EditText edtPrice = view.findViewById(R.id.edt_price);
        EditText edtQuantity = view.findViewById(R.id.edt_quantity);
        EditText edtStatus = view.findViewById(R.id.edt_status);
        Button btnUpdate = view.findViewById(R.id.btn_update_car);


        edtName.setText(objUpdateCar.getName());
        edtPrice.setText(objUpdateCar.getPrice());
        edtQuantity.setText(objUpdateCar.getQuantity());
        edtStatus.setText(objUpdateCar.getStatuss());

        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Hủy", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String price = edtPrice.getText().toString();
                String quantity = edtQuantity.getText().toString();
                String status = edtStatus.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(context, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                } else if (price.equals("")) {
                    Toast.makeText(context, "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
                } else if (quantity.equals("")) {
                    Toast.makeText(context, "Vui lòng nhập số lương", Toast.LENGTH_SHORT).show();
                } else if (status.equals("")) {
                    Toast.makeText(context, "Vui lòng nhập tình trạng", Toast.LENGTH_SHORT).show();
                } else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(APIServices.DOMAIN)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    APIServices apiService = retrofit.create(APIServices.class);

                    Call<List<CarModel>> call = apiService.updateCar(idUpdate, new CarModel(name, price, quantity, status));

                    call.enqueue(new Callback<List<CarModel>>() {
                        @Override
                        public void onResponse(Call<List<CarModel>> call, retrofit2.Response<List<CarModel>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                alertDialog.dismiss();
                                listCar = response.body();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<CarModel>> call, Throwable t) {
                            Log.e("zzzzz", "onFailure: " + t.getMessage());
                        }
                    });
                }
            }


        });

    }


}
