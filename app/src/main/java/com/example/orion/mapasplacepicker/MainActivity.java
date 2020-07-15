package com.example.orion.mapasplacepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etLat)
    EditText etLat;

    @BindView(R.id.etLng)
    EditText etLng;

    int PLACE_PICKER_REQUEST = 1;

    @OnClick(R.id.btnGetLocation)
    public void getLocation(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this),PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnGoLocation)
    public void goLocation(){
        Intent intent= new Intent(this, MapsActivity.class);
        Bundle bundle=new Bundle();
        Double latitude= Double.parseDouble(etLat.getText().toString());
        Double longitude= Double.parseDouble(etLng.getText().toString());
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                //String toastMsg = String.format("Place: %s", place.getName());
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                LatLng latlng= place.getLatLng();
                etLat.setText(latlng.latitude + "");
                etLng.setText(latlng.longitude + "");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

}
