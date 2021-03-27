package com.gtech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.gtech.adapter.ProductAdapter;
import com.gtech.helperclass.CustomDialog;
import com.gtech.helperclass.InternetHelper;
import com.gtech.helperclass.Validation;
import com.gtech.retrofit.APIClient;
import com.gtech.retrofit.ApiInterface;
import com.gtech.retrofit.Response.ProductRes.ProductResponse;
import com.gtech.retrofit.Response.ProductRes.Vijayan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Activity activity;
    Context context;
    InternetHelper internetHelper;
    boolean isInternetPresent;
    ApiInterface apiInterface;
    ProductAdapter adapter;

    ArrayList<Vijayan> arrayList = new ArrayList<>();

    @BindView(R.id.recyclerview_home)
    RecyclerView recyclerview_home;
    @BindView(R.id.no_record_txt)
    TextView no_record_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activity = MainActivity.this;
        context = getApplicationContext();

        internetHelper = new InternetHelper(activity);
        isInternetPresent = internetHelper.isConnectingToInternet();

        if (isInternetPresent) {
            getProduct_service();
        } else {
            Validation.toast(activity, Validation.getString(activity, R.string.nointernet));
        }

    }


    private void getProduct_service() {
        arrayList.clear();
        if (CustomDialog.cus_dialog == true) {
            CustomDialog.custom_dialog_hide();
        }
        CustomDialog.customdialog(activity);

        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("count", "");
            jsonObject.addProperty("sub_cat_id", "24");

//            HashMap<String,String> detail = new HashMap<>();
//            detail.put("count","");
//            detail.put("sub_cat_id","24");

            apiInterface = APIClient.getClient().create(ApiInterface.class);
            Call<ProductResponse> call = apiInterface.post_getProduct(jsonObject);
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    if (CustomDialog.cus_dialog == true) {
                        CustomDialog.custom_dialog_hide();
                    }

                    if (response.isSuccessful()) {
                        String status_ = response.body().getStatus();
                        Log.e("status=>>>",status_);
                        String msg = response.body().getMessage();

                        Log.e("response=>>", status_ = " msg=>>" + msg);

                       // if (status_.equals("success")) {
                            Log.e("response1=>>", status_ = " msg=>>" + msg + " " + response.body().getVijayan().size());
                            if (response.body().getVijayan() != null) {


                                List<Vijayan> list = response.body().getVijayan();
                                if (list.size() != 0) {
                                    Log.e("response=>>", status_ = " msg=>>" + msg + " " + list.size());
                                    arrayList.addAll(list);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
                                    recyclerview_home.setLayoutManager(layoutManager);
                                    adapter = new ProductAdapter(activity, arrayList);
                                    recyclerview_home.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    no_record_txt.setVisibility(View.GONE);
                                    recyclerview_home.setVisibility(View.VISIBLE);
                                } else {
                                    no_record_txt.setVisibility(View.VISIBLE);
                                    recyclerview_home.setVisibility(View.GONE);
                                }

                            } else {
                                no_record_txt.setVisibility(View.VISIBLE);
                                recyclerview_home.setVisibility(View.GONE);
                            }

//                        } else {
//                            no_record_txt.setVisibility(View.VISIBLE);
//                            recyclerview_home.setVisibility(View.GONE);
//                        }

                    }

                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    if (CustomDialog.cus_dialog == true) {
                        CustomDialog.custom_dialog_hide();
                    }
                    Log.e("error=>>", t.toString());
                }
            });

        } catch (JsonParseException e) {

        }

    }

    public void click_spin(String msg){
        Validation.toast(activity,msg);
    }

}