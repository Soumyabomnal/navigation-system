package com.ram.blind;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GMapsActivity extends MapActivity 
{
	
	private MapView mapView;
	
	/*
		private static final int latitudeE6 = 37985339;
		private static final int longitudeE6 = 23716735;
	*/
	
	// Link: http://www.findlatitudeandlongitude.com/?loc=New-Delhi   For Getting Latitude and Longitude
	
	//Initializing With Default Value
	 //Manually Providing The Latitude And Longitude(For Jayanagar,Bangalore,Karanatka,India) STARTS
	double latitude = 12.931288;
	double longitude = 77.587582;
	//Manually Providing The Latitude And Longitude(For Jayanagar,Bangalore,Karanatka,India) ENDS
	String address="Jayanagar,Bangalore,Karanatka,India";
	
	String[] result;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_map);
        
    	mapView = (MapView) findViewById(R.id.map_view);       
        mapView.setBuiltInZoomControls(true);
        
        Intent intent = getIntent();
        //latitude = intent.getDoubleExtra("latitude",77.586651);
        //longitude = intent.getDoubleExtra("longitude",77.586651);
       
        address = getAddress(latitude, longitude);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
		CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(drawable,this);
		
		//GeoPoint point = new GeoPoint(latitudeE6, longitudeE6);
		GeoPoint point = new GeoPoint((int)(latitude*1e6),(int)(longitude*1e6));
		OverlayItem overlayitem = new OverlayItem(point,"Address",address);
		
		// Adding More Overlay Items
		
		//GeoPoint point1 = new GeoPoint(59985339,44716735);
		//OverlayItem overlayitem1 = new OverlayItem(point1, "Hello", "I'm in Bangalore, Karnatka!");
		
		itemizedOverlay.addOverlay(overlayitem);
		//itemizedOverlay.addOverlay(overlayitem1);
		
		mapOverlays.add(itemizedOverlay);
		
		MapController mapController = mapView.getController();
		
		mapController.animateTo(point);
		//mapController.setZoom(6); // will point to Bangalore ,After zooming it will point to jayanagar
		//mapController.setZoom(12);//Directly Provide to local place(e.g. Jayanagar)
		mapController.setZoom(16);//Directly Provide to local place(e.g. Jayanagar)
        mapController.setCenter(point);
    }

	@Override
	protected boolean isRouteDisplayed() 
	{
		return false;
	
	}
	
	@SuppressLint("NewApi")
	public String getAddress(double latitude, double longitude) 
	{
        StringBuilder result = new StringBuilder();
        try
        {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) 
            {
                Address address = addresses.get(0);
                result.append(address.getSubLocality()).append(",\n");
                result.append(address.getLocality()).append(",\n");
                result.append(address.getCountryName());
            }
        } 
        catch (IOException e) 
        {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

}



