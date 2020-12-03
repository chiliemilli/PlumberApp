package com.kubsu.plumberapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VolleyRestAPIRequester {


    private static final String TAG="error";
    public static final String QUERY_FOR_PLUMBER_ENTRY ="http://10.0.0.10:8080/Plumbers?task=auth&mode=2&login=";



    public interface OrderInfoCallback {
        void onError(String message);
        void onResponse(OrderInfoModel orderInfoModel);

    }

    //Plumber's entry
    public void getOrderInfoByLogin(final Context context, final String login, final String password, final OrderInfoCallback orderInfoCallback){

        String url=QUERY_FOR_PLUMBER_ENTRY+login+"&password="+password;
       final  OrderInfoModel currentOrder=new OrderInfoModel();

        StringRequest request= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                        JSONObject orderInfoJSON=jsonObject.getJSONObject("order_info");
                        JSONArray orderInfoList=orderInfoJSON.getJSONArray("data");
                        JSONObject currentOrderJSON=(JSONObject) orderInfoList.get(0);

                        currentOrder.setOrderId(currentOrderJSON.getInt("ORDER_INFO_ID"));
                        currentOrder.setOrderType(currentOrderJSON.getString("ORDER_TYPE"));
                        currentOrder.setOrderDescription(currentOrderJSON.getString("ORDER_DESCRIPTION"));
                        currentOrder.setOrderPrice(currentOrderJSON.getString("ORDER_PRICE"));
                        currentOrder.setPhoneNumber(currentOrderJSON.getLong("CUSTOMER_PHONE"));
                        currentOrder.setOrderAddress(currentOrderJSON.getString("CUSTOMER_ADDRESS"));
                        orderInfoCallback.onResponse(currentOrder);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

            }
    });

        MySingleton.getInstance(context).addToRequestQueue(request);

    }




    //Plumber's regiastration
    public void registrationRequest(final Context context, final String login, final String password){

        List<OrderInfoModel> orderInfoModel=new ArrayList<>();
        String url=QUERY_FOR_PLUMBER_ENTRY;

//       JSONObject requestBody=new JSONObject();
//        try {
//            requestBody.put("task", "auth");
//            requestBody.put("login", login);
//            requestBody.put("password", password);
//            requestBody.put("mode", "2");
//        }catch (JSONException e){
//            e.printStackTrace();
//        }

        Map<String,String> params=new HashMap<>();
            params.put("task", "reg");
            params.put("mode", "2");
            params.put("login", login);
            params.put("password", password);


        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error"+error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);

    }

}
