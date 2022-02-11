package com.example.pago_payphone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String url;
    TextView txt;
    Button btn;
    EditText txtNombre, txtCantidad, txtCosto, txtIva, txtTotal, txtTelef, txtCedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        btn = (Button) findViewById(R.id.btnCobrar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtTelef = (EditText) findViewById(R.id.txtTelef);
        txtCedula = (EditText) findViewById(R.id.txtCedula);
        txtCantidad = (EditText) findViewById(R.id.txtNombre);
        txtTotal = (EditText) findViewById(R.id.txtTotal);
        txtCosto = (EditText) findViewById(R.id.txtCosto);
        txtIva = (EditText) findViewById(R.id.txtIva);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api();
            }
        });
    }

    public JSONObject getJSONtoSend() {
        JSONObject obj = new JSONObject();
        try {
                   //arreglar esta parte
            obj.put("telefono", txtTelef.getText().toString());
            obj.put("Nombre", txtNombre.getText().toString());
            obj.put("clientUserId", txtCedula.getText().toString());
            obj.put("Cantidad", txtCantidad.getText().toString());
            obj.put("responseUrl", "http://paystoreCZ.com/confirm.php");
            obj.put("amount", Integer.valueOf(txtTotal.getText().toString()) +
                    Integer.valueOf(txtCosto.getText().toString()) +
                    Integer.valueOf(txtIva.getText().toString()));
            obj.put("amountWithTax", txtTotal.getText().toString());
            obj.put("amountWithoutTax", txtCosto.getText().toString());
            obj.put("tax", txtIva.getText().toString());
            Date dt = new Date();
            Random rd = new Random();
            String id = (rd.nextInt() + "" + dt.getDay() + "" + dt.getMonth() + "" + dt.getYear() + "" + dt.getHours() + "" + dt.getMinutes() + dt.getSeconds());
            obj.put("clientTransactionId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(obj.toString());
        return obj;
    }

    public void api() {
        if (txtTelef.getText().toString().replaceAll(" ", "").trim().length() > 0 &&
                txtNombre.getText().toString().replaceAll(" ", "").trim().length() > 0 &&
                txtCedula.getText().toString().replaceAll(" ", "").trim().length() > 0 &&
                txtTotal.getText().toString().replaceAll(" ", "").trim().length() > 0 &&
                txtCosto.getText().toString().replaceAll(" ", "").trim().length() > 0 &&
                txtIva.getText().toString().replaceAll(" ", "").trim().length() > 0) {
            RequestQueue queue = Volley.newRequestQueue(this);
            url = "https://pay.payphonetodoesposible.com/api";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, getJSONtoSend(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response);
                            try {
                                txt.append("transactionId: "+ response.getString("transactionId"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                    txt.append(error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    //headers.put("Authorization", "Bearer Ps7d5SOOe9CYIITZa-2llloFRjyXpjUFUuXW6_fcEqyRidzh2WGQOae3BOzm9yEzjPpQYcYIZLfvUsfPcqs7fsL7rUFLuExzi5MYINFlYWXBDb6NwODrbu4194_R39cGu0qOu5pO4MsCB7GyKLFH1FD1gmd9YZTLEfzgu0MjsvN1MiJBGy_2usa9OiNOuzsteO4Uh8zENzWNQhs538NGCVD_laR3-e0ApIErHZp9yCSFfGKOxi1Pr9CKaVhlUFlx8vn5Yp2J9C9yD4nk-JFCL7yYkr-9t_vaP1uEP637AUF2zbUChrUeok_gPLSZ3BKcp9zgB21gKgxywoR35_Etx9JYAJ8");
                    return headers;
                }
            };
            queue.add(jsonObjectRequest);
        }else
        {
            Toast.makeText(MainActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
