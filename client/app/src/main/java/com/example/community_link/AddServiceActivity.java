package com.example.community_link;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class AddServiceActivity extends CommunityLinkActivity {
    private TimePicker tPicker;
    private ServiceData sd = new ServiceData();
    private DatePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        tPicker=(TimePicker)findViewById(R.id.timePicker);
        tPicker.setIs24HourView(true);
    }

    public void test(View view){
        int hour, minute;
        if (Build.VERSION.SDK_INT >= 23 ){
            hour = tPicker.getHour();
            minute = tPicker.getMinute();
        }
        else{
            hour = tPicker.getCurrentHour();
            minute = tPicker.getCurrentMinute();
        }

        EditText editText = (EditText) findViewById(R.id.etProjectName);
        String message = editText.getText().toString();
        EditText editType = (EditText) findViewById(R.id.etType);
        String type = editType.getText().toString();
        EditText editDesc = (EditText) findViewById(R.id.etDesc);
        String desc = editDesc.getText().toString();
        sd.setTime(hour,minute);
        sd.setEventName(message);
        sd.setName("Jiang Zeming");
        sd.setType(type);
        sd.setDescription(desc);

        picker=(DatePicker)findViewById(R.id.datePicker);
        sd.setDate(picker.getYear(),picker.getMonth(),picker.getDayOfMonth());

        postToServer();

        CharSequence toastMess = "Your service was added!";
        Toast toast = Toast.makeText(view.getContext(), toastMess, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**To Enable the postToServer() Function,
     * Uncomment the postToServer() above,
     **/

    private void postToServer() {

        JSONObject service = new JSONObject();
        try {
            service = new JSONObject(sd.toJSON());
        }catch(JSONException e) {
            e.printStackTrace();
        }

        Response.Listener addServiceResponseCallback = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
            }
        };

        Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("HTTP response didn't work");
                System.out.println(error.toString());
            }
        };

        CommunityLinkApp.requestManager.addService(service, addServiceResponseCallback, errorCallback);
   }

}