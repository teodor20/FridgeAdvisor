package uk.ac.rgu.cwpartone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, DatePickerDialog.OnDateSetListener {
    ZXingScannerView scannerView;
    private String url="https://api.barcodelookup.com/v2/products?barcode=";
    private String APIKey="&formatted=y&key=sryjrxyio2de415iv78mp985imc9ob";
    private String date;
    private String productName;
    private final String sharedPrefFile = "uk.ac.rgu.cwpartone.demo";
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor prefsEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        prefsEditor = sharedPrefs.edit();
    }

    @Override
    public void handleResult(Result result) {
        this.getProduct(result.getText());
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    protected void getProduct(String barcode) {
        MainActivity.resultView.setText(barcode);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String EndPoint = this.getUrl()+barcode+this.getAPIKey();
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                EndPoint,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                        try {
                            setProductName(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        showDatePickerDialog();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.API_Error, Toast.LENGTH_LONG);
                        onBackPressed();
                        toast.show();
                    }
                }
        );

        requestQueue.add(objectRequest);
    }

    public String getUrl() {
        return url;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
              this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setDate(dayOfMonth + "/" + month + "/" + year);
        saveData(getProductName(), getDate());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setProductName(JSONObject response) throws JSONException {
        JSONArray arr = response.getJSONArray("products");
        JSONObject jObj = arr.getJSONObject(0);
        String productName = jObj.getString("product_name");
        setProductName(productName);
    }

    public void saveData(String title, String date) {
        String item = title + " | " + date;
        int insertIndex = sharedPrefs.getAll().size();
        String prefKey = String.valueOf(insertIndex);
        prefsEditor.putString(prefKey, item);
        prefsEditor.apply();
        Context context = getApplicationContext();
        //Toast toast = Toast.makeText(context, R.string.API_Success, Toast.LENGTH_LONG);
        Toast toast = Toast.makeText(context, insertIndex + " " + title + " " + date, Toast.LENGTH_LONG);
        onBackPressed();
        toast.show();
    }
}
