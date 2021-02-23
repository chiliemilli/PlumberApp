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

   //public static final String QUERY_FOR_PLUMBER_ENTRY ="http://10.0.0.10:8080/Plumbers?task=auth&mode=2&login=";
    //public static final String DEFAULT_QUERY ="http://10.0.0.10:8080/Plumbers";
  public static final String QUERY_FOR_PLUMBER_ENTRY ="http://192.168.43.12:8080/Plumbers?task=auth&mode=2&login=";
    public static final String DEFAULT_QUERY ="http://192.168.43.12:8080/Plumbers";

    public interface OrderInfoCallback {
        void onError(String message);
        void onResponse(OrderInfoModel orderInfoModel, PlumberDataModel plumberDataModel);
    }

    //Plumber's entry
    public void getOrderInfoByLogin(final Context context, final String login, final String password, final OrderInfoCallback orderInfoCallback){

        String url=QUERY_FOR_PLUMBER_ENTRY+login+"&password="+password;
       final  OrderInfoModel currentOrder=new OrderInfoModel();
       final PlumberDataModel plumberDataModel=new PlumberDataModel();


        StringRequest request= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (!jsonObject.optString("order_id").equals("0")) {

                        JSONObject orderInfoJSON = jsonObject.getJSONObject("order_info");
                        JSONArray orderInfoList = orderInfoJSON.getJSONArray("data");
                        JSONObject currentOrderJSON = (JSONObject) orderInfoList.get(0);

                        JSONObject forStatus=jsonObject.getJSONObject("order");
                        JSONArray forStatusArray=forStatus.getJSONArray("data");
                        JSONObject currentOrderStatus = (JSONObject) forStatusArray.get(0);

                        currentOrder.setOrderId(currentOrderJSON.getInt("ORDER_INFO_ID"));
                        currentOrder.setOrderType(currentOrderJSON.getString("ORDER_TYPE"));
                        currentOrder.setOrderDescription(currentOrderJSON.getString("ORDER_DESCRIPTION"));
                        currentOrder.setOrderPrice(currentOrderJSON.getString("ORDER_PRICE"));
                        currentOrder.setPhoneNumber(currentOrderJSON.getLong("CUSTOMER_PHONE"));
                        currentOrder.setOrderAddress(currentOrderJSON.getString("CUSTOMER_ADDRESS"));

                        currentOrder.setOrderWorkingStatus(currentOrderStatus.getString("ORDER_STATUS"));
                        currentOrder.setOrderStatus(true);


                        plumberDataModel.setPlumberID(jsonObject.getInt("ID"));

                    }
                    else{
                       plumberDataModel.setPlumberStatus("online");
                        plumberDataModel.setPlumberID(jsonObject.getInt("ID"));
                    }
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    orderInfoCallback.onResponse(currentOrder, plumberDataModel);


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
    });

        MySingleton.getInstance(context).addToRequestQueue(request);

    }


    //Plumber's regiastration НЕ РЕАЛИЗОВАНА
    public void registrationRequest(final Context context, final String login, final String password){

        List<OrderInfoModel> orderInfoModel=new ArrayList<>();
        String url=QUERY_FOR_PLUMBER_ENTRY;

        Map<String,String> params=new HashMap<>();
            params.put("task", "reg");
            params.put("mode", "2");
            params.put("login", login);
            params.put("password", password);


        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(context, "Error"+error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    //смена статуса заказа
    public void changeOrderStatus(final Context context, final String status, final String date, final int orderId, final int plumberID){

        String url= DEFAULT_QUERY;
        final String orderIDString=String.valueOf(orderId);
        final String plumberIDString=String.valueOf(plumberID);

        Map<String,String> params=new HashMap<>();
         params.put("task","action");
         params.put("action", status);
         params.put("date", date);
         params.put("order_id", orderIDString);
         params.put("plumber_id",plumberIDString);

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Error"+error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    //выход сантехника
    public void exitPlumber(final Context context, final int plumberID, final String plumberStatus){

        String url=DEFAULT_QUERY;
        final String plumberIDString=String.valueOf(plumberID);


        Map<String,String> params=new HashMap<>();
        params.put("task","disconnect");
        params.put("plumber_id", plumberIDString);
        params.put("status", plumberStatus);

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               // Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);

    }
}
