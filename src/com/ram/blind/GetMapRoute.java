/**
 @Author : Munna Kumar Singh
 Date : Nov 26, 2013
 File : GetMapRoute.java
 Package : com.dev.mob
*/

package com.ram.blind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.HttpClient.CustomHttpClient;
import com.util.Distance;
import com.util.Global;
import com.util.SpinnerObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GetMapRoute extends Activity implements LocationListener
{
	String response = null;
	String res = null;
	String result[];
    double currentLatitude=0,currentLongitude=0;
    
    //Location Parm(Starts)
	    LocationManager locationManager ;
		String provider;
		Location location; // location
	    // The minimum distance to change Updates in meters
	 	//private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	 	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meters
	
	 	// The minimum time between updates in milliseconds
	 	//private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	 	//private static final long MIN_TIME_BW_UPDATES = 1000 * 20 * 1; // 20 seconds
	 	private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 10 seconds
 	 //Location Parm(Ends)
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_route);
		
		final Intent updateLocIntent = new Intent(this,AutoUpdateLocation.class);
		
		Button getDirection = (Button)findViewById(R.id.get_path);
		final TextView exception = (TextView)findViewById(R.id.route_exception);
		final Spinner sp = (Spinner)findViewById(R.id.select_route);
		
		//All About Locations(Starts)
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
      //All About Locations(Ends)
        
		try 
		{
			//Getting The All Existing Routes(Starts)
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        //postParameters.add(new BasicNameValuePair("username",username));
			try 
		    {
		            		
		    	response = CustomHttpClient.executeHttpPost(Global.URL+"res/JSP/User/JSP-For-Android/get_routes.jsp",postParameters);
	       	    String res=response.toString();
	       	    res = res.trim();
	       	    res = res.replaceAll("\'","\\\\'");  // replacing ' to \\' 
	       	    //res= res.replaceAll("\\s+",""); for removing space in between words        	              	 
	            result = res.split("~");
	            
	       	 } 
	       	 catch (Exception e) 
	       	 {
	       		System.out.println("***** Opps,Exception in GetMapRoute While Getting The Routes *****");
	       		e.printStackTrace();
	       	 }
			//Getting The All Existing Routes(Ends)
			
			
	        List<SpinnerObject> list = new ArrayList<SpinnerObject>();
	        SpinnerObject spinnerObj = null;
	        for(int i=0;i<result.length;i++)
	        {
	        	String str = result[i];
	        	String[] info = str.split(",");
	        	int id = Integer.parseInt(info[0]);
	        	String name = info[1];
	        	
	        	spinnerObj = new SpinnerObject(id,name);
	        	list.add(spinnerObj);
	        }
	        
	        
	        // Creating adapter for spinner
	        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<SpinnerObject>(this,android.R.layout.simple_spinner_item,list);
	        // Drop down layout style - list view with radio button
	        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        // attaching data adapter to spinner
	        sp.setAdapter(dataAdapter);
	        
	        //Triggering The Button Click Action
	        getDirection.setOnClickListener(new View.OnClickListener() 
	        {
				
				public void onClick(View arg0) 
				{
					
					SharedPreferences pref = getApplicationContext().getSharedPreferences("any_prefname",MODE_PRIVATE);
			 	 String   username = pref.getString("username","");//2nd arg is the default value
					SpinnerObject obj = (SpinnerObject) sp.getSelectedItem();
					int databaseId = obj.getId();
					String name = obj.getValue();
					Toast.makeText(getApplicationContext(),"Id : " + databaseId+"\n"+"Route :"+name,Toast.LENGTH_LONG).show();
					
					//Getting The All Existing Land Mark For Selected Route(Starts)
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					   postParameters.add(new BasicNameValuePair("username",username));
			        postParameters.add(new BasicNameValuePair("route_id",new Integer(databaseId).toString()));
					try 
				    {
				            		
				    	response = CustomHttpClient.executeHttpPost(Global.URL+"res/JSP/User/JSP-For-Android/get_route_landmarks.jsp",postParameters);
			       	    String res=response.toString();
			       	    res = res.trim();
			       	    res = res.replaceAll("\'","\\\\'");  // replacing ' to \\' 
			       	    //res= res.replaceAll("\\s+",""); for removing space in between words        	              	 
			            result = res.split("~");
			            //System.out.println("LandMark List : " + Arrays.toString(result));
			            
			          
			        updateLocIntent.putExtra("cur_lat",currentLatitude);
			        updateLocIntent.putExtra("cur_lng",currentLongitude);
		            updateLocIntent.putExtra("landmarks",result);
			        startActivity(updateLocIntent);
			           
			       	 } 
			       	 catch (Exception e) 
			       	 {
			       		System.out.println("***** Opps,Exception in GetMapRoute While Getting The LandMarks *****");
			       		e.printStackTrace();
			       	 }
					//Getting The All Existing Land Mark For Selected Route
					
				}
			});
		}
		catch (Exception e)
		{
			exception.setText(e.toString());
			System.out.println("Opps,Exception In GetMapRoute=>onCreate():");
			System.out.println(e);
			e.printStackTrace();
		}
		
		
	}
	
/*
 * 	(non-Javadoc)
 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
 */
	public void onLocationChanged(Location location) 
	{
		currentLatitude = location.getLatitude();
		currentLongitude= location.getLongitude();
		
	}
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}




