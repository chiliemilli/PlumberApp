package com.kubsu.plumberapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VolleyRestAPIRequester {


    private static final String TAG="error";

    public interface OrderInfoByLoginResponse {
        void onError(String message);

        void onResponse(OrderInfoModel orderInfoModel);
    }



    public static final String QUERY_FOR_PLUMBER_ENTRY ="http://10.0.0.10:8080/Plumbers?";

    public void getOrderInfoByLogin(final Context context, final String login, final String password, final OrderInfoByLoginResponse orderInfoByLoginResponse){

        final OrderInfoModel orderInfoModel=new OrderInfoModel();
        String url="http://10.0.0.10:8080/Plumbers?task=auth&mode=1&login="+login+"&password="+password;

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    if (response.optString("ID").equals(1)){

                        JSONObject orderJson=response.getJSONObject("order_info");

                        JSONArray orderInfoList=orderJson.getJSONArray("data");
                        Toast.makeText(context, orderInfoList.toString(), Toast.LENGTH_SHORT).show();
                        // OrderInfoModel currentOrder=new OrderInfoModel();
                        JSONObject currentOrderJSON=(JSONObject) orderInfoList.get(1) ;

                        orderInfoModel.setOrderId(currentOrderJSON.getInt("order_id"));
                        orderInfoModel.setOrderType(currentOrderJSON.getString("order_type"));
                        orderInfoModel.setOrderDescription(currentOrderJSON.getString("order_description"));
                        orderInfoModel.setOrderPrice(currentOrderJSON.getString("order_price"));
                        orderInfoModel.setPhoneNumber(currentOrderJSON.getString("customer_phone"));
                        orderInfoModel.setOrderAddress(currentOrderJSON.getString("customer_address"));

                        orderInfoByLoginResponse.onResponse(orderInfoModel);

                    }
                    if (response.optString("order_id").equals(0)){
                        orderInfoModel.setNoOrder(true);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error"+error.toString(), Toast.LENGTH_LONG).show();

            }
    })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               // super.getParams();
                Map<String, String> params = new HashMap<String, String>();
                params.put("task", "auth");
                params.put("mode","2");
                params.put("login", login);
                params.put("password", password);

                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(request);

    }


    //REGISTRATION
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
