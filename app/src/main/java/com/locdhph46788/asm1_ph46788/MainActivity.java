package com.locdhph46788.asm1_ph46788;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.locdhph46788.asm1_ph46788.Model.CarModel;
import com.locdhph46788.asm1_ph46788.Services.HttpRequest;
import com.locdhph46788.asm1_ph46788.Model.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    CarAdapter carAdapter;
    RecyclerView rcvCar;
    List<CarModel> listCar;
    ArrayList<File> listImg;
    FloatingActionButton btnAdd;
    Button btnSort;
    EditText edtSearch;
    ProgressDialog progressDialog;
    HttpRequest httpRequest;
    static final String TAG = "MainActivity";
    String order = "asc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvCar = findViewById(R.id.rcv_car);
        fetchAPI();
        edtSearch = findViewById(R.id.edt_search_name);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String key = edtSearch.getText().toString().trim();
                    httpRequest.callAPI()
                            .searchCar(key)
                            .enqueue(getCarAPI);
                    Log.d(TAG, "onEditorAction: " + key);
                    return true;
                }
                return false;
            }
        });
        btnSort = findViewById(R.id.btn_sort);
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortProducts();
            }
        });
        btnAdd = findViewById(R.id.fab_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAdd();
            }
        });


    }

    private void fetchAPI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        httpRequest = new HttpRequest();
        httpRequest.callAPI()
                .getCars()
                .enqueue(getCarAPI);
    }

    Callback<Response<List<CarModel>>> getCarAPI = new Callback<Response<List<CarModel>>>() {
        @Override
        public void onResponse(Call<Response<List<CarModel>>> call, retrofit2.Response<Response<List<CarModel>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    listCar = response.body().getData();
                    getData();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<List<CarModel>>> call, Throwable t) {
            Log.e(TAG, "onFailure: " + t.getMessage());
        }


    };


    private void sortProducts() {
        order = order.equals("asc") ? "desc" : "asc";

        httpRequest.callAPI()
                .getCarsSort(order)
                .enqueue(getCarAPI);
    }

    private void getData() {
        carAdapter = new CarAdapter(MainActivity.this, listCar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcvCar.setLayoutManager(linearLayoutManager);
        rcvCar.setAdapter(carAdapter);
        carAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
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
        ImageView ivAddCar = view.findViewById(R.id.img_add_car);
        ivAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("123123", "chooseAvatar: " + 123123);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                getImage.launch(intent);
            }
        });


        Button btnAdd = view.findViewById(R.id.btn_add_car);

        btnAdd.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String price = edtPrice.getText().toString();
            String quantity = edtQuantity.getText().toString();
            String statuss = edtStatus.getText().toString();
            if (name.equals("")) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            } else if (price.equals("")) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
            } else if (quantity.equals("")) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập số lương", Toast.LENGTH_SHORT).show();
            } else if (statuss.equals("")) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập tình trạng", Toast.LENGTH_SHORT).show();
            } else {
                httpRequest = new HttpRequest();
                httpRequest.callAPI()
                        .addCar(new CarModel(name, price, quantity, statuss))
                        .enqueue(new Callback<List<CarModel>>() {
                            @Override
                            public void onResponse(Call<List<CarModel>> call, retrofit2.Response<List<CarModel>> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    listCar.clear();
                                    listCar = response.body();
                                    getData();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<CarModel>> call, Throwable t) {
                                Log.e("GetCar", t.getMessage());

                            }
                        });
                Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {

                        Uri tempUri = null;

                        Intent data = o.getData();
                        if (data.getClipData() != null) {
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                tempUri = imageUri;

                                File file = createFileFormUri(imageUri, "image" + i);
                                listImg.add(file);
                            }


                        } else if (data.getData() != null) {
                            // Trường hợp chỉ chọn một hình ảnh
                            Uri imageUri = data.getData();

                            tempUri = imageUri;
                            // Thực hiện các xử lý với imageUri
                            File file = createFileFormUri(imageUri, "image");
                            listImg.add(file);

                        }

                        if (tempUri != null) {
//                            Glide.with(MainActivity.this)
//                                    .load(tempUri)
//                                    .thumbnail(Glide.with(MainActivity.this).load(R.drawable.ic_photo))
//                                    .centerCrop()
//                                    .circleCrop()
//                                    .skipMemoryCache(true)
//                                    .into()
                        }
                    }
                }
            });

    private File createFileFormUri(Uri path, String name) {
        File _file = new File(MainActivity.this.getCacheDir(), name + ".png");
        try {
            InputStream in = MainActivity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            Log.d("123123", "createFileFormUri: " + _file);
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}