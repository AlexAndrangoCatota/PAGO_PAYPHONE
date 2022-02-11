package com.example.pago_payphone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class solicitud extends AppCompatActivity {
    EditText txtTransactionIdSearch;
    TextView txtPhoneNumber, txtCountryCode, txtClientUserId, txtReference, txtAmount, txtAmountWithTax,
            txtAmountWithoutTax, txtTax, txtClientTransactionId;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);
        getSupportActionBar().hide();
        txtTransactionIdSearch = (EditText) findViewById(R.id.txtTransactionIdSearch);
        txtAmountWithTax = (TextView) findViewById(R.id.txtAmountWithTax);
        txtClientUserId = (TextView) findViewById(R.id.txtClientUserId);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtClientTransactionId = (TextView) findViewById(R.id.txtClientTransactionId);
        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        txtCountryCode = (TextView) findViewById(R.id.txtCountryCode);
        txtAmountWithoutTax = (TextView) findViewById(R.id.txtAmountWithoutTax);
        txtTax = (TextView) findViewById(R.id.txtTax);
        txtReference = (TextView) findViewById(R.id.txtReference);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTransactionIdSearch.getText().toString().trim().length() > 0)
                    getData();
                else
                    Toast.makeText(solicitud.this, "Debe ingresar el Id", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getData() {
        final String url = "https://pay.payphonetodoesposible.com/api/Sale/" + txtTransactionIdSearch.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        if (response != null) {
                            try {
                                txtAmountWithTax.setText("  " + response.getString("amountWithTax"));
                                txtClientUserId.setText("  " + response.getString("clientUserId"));
                                txtAmount.setText("  " + response.getString("amount"));
                                txtClientTransactionId.setText("  " + response.getString("clientTransactionId"));
                                txtPhoneNumber.setText("  " + response.getString("telefono"));
                                txtCountryCode.setText("  " + response.getString("CountryCode"));
                                txtAmountWithoutTax.setText("  " + response.getString("amountWithoutTax"));
                                txtTax.setText("  " + response.getString("tax"));
                                txtReference.setText("  " + response.getString("reference"));
                                Toast.makeText(solicitud.this, "Correcto", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(solicitud.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(getRequest);
    }
}