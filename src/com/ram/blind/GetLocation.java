/**
 @Author : Munna Kumar Singh
 Date : Nov 22, 2013
 File : GuideRoute.java
 Package : kumar.route
*/

package com.ram.blind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GetLocation extends Activity implements LocationListener
{
	
	LocationManager locationManager ;
	String provider;
	Location location; // location
	double latitude; // latitude
	double longitude; // longitude
	
	String address="";

	// The minimum distance to change Updates in meters
	//private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meters

	// The minimum time between updates in milliseconds
	//private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	//private static final long MIN_TIME_BW_UPDATES = 1000 * 20 * 1; // 20 seconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 10 seconds
	
	TextView exception;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_location);
		
		exception = (TextView)findViewById(R.id.get_exception);
		
		// Getting LocationManager object
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);        
        
        // Creating an empty criteria object
        Criteria criteria = new Criteria();
        
        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);
        
                
        if(provider!=null && !provider.equals(""))
        {
        	
        	// Get the location from the given provider 
            location = locationManager.getLastKnownLocation(provider);
                        
            //locationManager.requestLocationUpdates(provider, 20000, 1, this);
            locationManager.requestLocationUpdates(provider,MIN_DISTANCE_CHANGE_FOR_UPDATES,MIN_TIME_BW_UPDATES, this);
            
            
            if(location!=null)
            {
            	onLocationChanged(location);
            }
            else
            {
            	exception.setText("Location can't be retrieved!!!!!");
            	System.out.println("Location can't be retrieved");
            	Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
            }
            
        }
        else
        {
        	
        	exception.setText("No Provider Found!!!!!");
        	System.out.println("No Provider Found");
        	Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }
	}

	public void onLocationChanged(Location location) 
	{
		
	
	
	
		//TextView root = (TextView)findViewById(R.id.view_root);
	
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		Toast.makeText(getBaseContext(), "onLocationChanged in GeoLOcation", Toast.LENGTH_SHORT).show();	
		//System.out.println("***** Current Location Info *****");
		//System.out.println("Current Location : " + location+"\nLatitude : "+latitude+"\nLongitude : "+longitude);
		
		//Sending Result Back To Previous Activity(Which Called This Activity)
		Intent intent = new Intent();
		
		intent.putExtra("cur_latitude",latitude);
		intent.putExtra("cur_longitude",longitude);
		
		setResult(2,intent);// 2 = request code
		finish();
	}

	public void onProviderDisabled(String arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) 
	{
		// TODO Auto-generated method stub
		
	}
}
