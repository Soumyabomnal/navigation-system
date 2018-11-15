
package com.ram.blind;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.util.Distance;
import com.util.Global;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AutoUpdateLocation extends Activity implements
TextToSpeech.OnInitListener
{
	//
		MediaPlayer mp;
	//
	
    // Count Down Timer Parameters(STARTS)
		private CountDownTimer countDownTimer;
		//private final long startTime = 30 * 1000;
		private final long startTime = Global.TIME_INTERVAL;
		private final long interval = 1 * 1000;
    // Count Down Timer Parameters(Ends)
	final Handler myHandler = new Handler();
	int i =0;
	boolean STOP = false;
	//Text to speech 
	private TextToSpeech tts;
	
    TextView countDown,updatedValue,curLoc,curLat,curLong,exception;	
    String name="";
    
    String result[];
    List<String> landMarkList = null,voiceFileList=null,locatioList=null;
    List<Double> distanceList = null;
    List<Integer> chkList = new ArrayList<Integer>();
    double latitude=0,longitude=0,currentLatitude=0,CurrentLongitude=0;
    String curLocation="",voiceFile="",voiceFilePath="",instruction="",phone="";

    
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_location);
		
		tts = new TextToSpeech(this, this);
		
		Intent intent = getIntent();
		result = intent.getStringArrayExtra("landmarks");
	   
		currentLatitude = intent.getDoubleExtra("cur_lat",0);
	    CurrentLongitude = intent.getDoubleExtra("cur_lng",0);
	    
	   //Taking Till 6 Decimal Points Only(Starts)
		DecimalFormat f = new DecimalFormat("##.000000");
		currentLatitude = Double.parseDouble(f.format(currentLatitude));
		CurrentLongitude = Double.parseDouble(f.format(CurrentLongitude));
		//Taking Till 6 Decimal Points Only(Ends)
	    
		landMarkList = Arrays.asList(result);
		//System.out.println("LandMark List : " + Arrays.toString(result));
		System.out.println("LandMark List : " + landMarkList);
		
		final Button monitorService = (Button)findViewById(R.id.manage_service);
		countDown = (TextView)findViewById(R.id.cout_down);
		updatedValue = (TextView)findViewById(R.id.updated_value);
		curLoc = (TextView)findViewById(R.id.cur_loc);
		curLat = (TextView)findViewById(R.id.cur_latitude);
		curLong = (TextView)findViewById(R.id.cur_longitude);
		
		exception = (TextView)findViewById(R.id.loc_exception);
		exception.setTextColor(Color.RED);
		
		//Count Down Timer Implementation Starts
	        countDownTimer = new MyCountDownTimer(startTime,interval);
	        countDown.setText(countDown.getText() + String.valueOf(startTime/1000));
        //Count Down Timer Implementation Ends
	        
	    //Getting The Corrent Location
	        //Intent curLocIntent = new Intent(AutoUpdateLocation.this,GetLocation.class);
			//startActivityForResult(curLocIntent,2);//2 is the requestCode(That must be same as setResult(2,intent) in GetLocation Activity)
			curLat.setText(""+currentLatitude);
   	 		curLong.setText(""+CurrentLongitude);
	    //    
	        
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() 
        {
           @Override
           public void run() 
           {
        	   try {
        		   	autoUpdate();
        	   		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//Calling This method() After Every x second
           }
        //}, 0, 1000);//1000 ms = 1s
    	}, 0,Global.TIME_INTERVAL);//20 * 1000(20000 ms = 20s)    
		
		//Monitoring The Service(Auto Update Location)
        monitorService.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View arg0) 
			{
				if(!STOP)
				{
					STOP = true;
					monitorService.setText("START");
					monitorService.setTextColor(Color.GRAY);
					//speakOut();
				}
				else
				{

				
					STOP = false;
					monitorService.setText("STOP");
					monitorService.setTextColor(Color.RED);
					
					
					
					
				}
				
				
			}
		});
		
	}
	
	public void autoUpdate() throws InterruptedException
	{
		
		if(!STOP)
        { 
			//i++;
			i = i+5;
			
			//Intent intent = new Intent(AutoUpdateLocation.this,TestActivity.class);
			Intent intent = new Intent(AutoUpdateLocation.this,GetLocation.class);
		
			startActivityForResult(intent,2);//2 is the requestCode(That must be same as setResult(2,intent) in GetLocation Activity)
		
			//
			    locatioList = new ArrayList<String>();
				distanceList = new ArrayList<Double>();
				voiceFileList = new ArrayList<String>();
			//
			
			//Distance Calculation(Starts)
            for(int i=0;i<landMarkList.size();i++)
            {
            	
            
            	String item = landMarkList.get(i);
            	item = item.substring(1,item.length()-1);
            	String[] landMarkInfo = item.split(",");
            	System.out.println("=========="+Arrays.toString(landMarkInfo)+"\n");
            	
            	curLocation = landMarkInfo[3];
            	instruction=landMarkInfo[4];
            	latitude = Double.parseDouble(landMarkInfo[5]);
            	longitude = Double.parseDouble(landMarkInfo[6]);
            	voiceFile = landMarkInfo[7];
             	phone = landMarkInfo[8];
            
            	System.out.println("Latitude : " + latitude);
            	System.out.println("Longitude : " + longitude);
            	System.out.println("Voice File : " + voiceFile);
            	System.out.println("Voice File instruction: " + instruction);
            	System.out.println("("+latitude+","+longitude+","+currentLatitude+","+CurrentLongitude+")");
                double distance = Distance.distFrom(latitude, longitude, currentLatitude, CurrentLongitude);
               //text to speach
                
                
              
            	//double distance = Distance.distFrom(currentLatitude,CurrentLongitude,latitude,longitude);
            	System.out.println("Distance : " + distance);
            	System.out.println("---------------------------------");
                locatioList.add(curLocation);
            	distanceList.add(distance);
            	voiceFileList.add(voiceFile);
            	
            	
            } 
        	//Toast.makeText(getApplicationContext(), "cordi :"+latitude+" : "+longitude, Toast.LENGTH_LONG).show();
            System.out.println("Location List : " +locatioList);
            System.out.println("Distance List : " +distanceList);
            System.out.println("Voice File List : " +voiceFileList);
            double minDistance = Collections.min(distanceList);
            int minIndex = distanceList.indexOf(minDistance);
            voiceFile = voiceFileList.get(minIndex);
            curLocation = locatioList.get(minIndex);
            System.out.println("Current Location : " + curLocation);
            System.out.println("(Min. Distance,VoiceFile) = " +"("+minDistance+","+voiceFile+")");
            System.out.println();
          //Distance Calculation(Ends)
          
          //Logic To Play Voice File Only One(Starts)  
            if(!chkList.contains(minIndex))
		    {
            	
		    	chkList.add(minIndex);
		    	voiceFile=voiceFileList.get(minIndex).trim();
		    	   System.out.println("==============test voice =============");
			    	
			    	  Thread.sleep(1000);
			    	   System.out.println("Voice File List shanu : " +voiceFileList.get(minIndex).trim());  
			    	
		          speakOut(voiceFileList.get(minIndex).trim());
		          Thread.sleep(1000);
		         // Toast.makeText(getApplicationContext(), voiceFileList.get(minIndex).trim(), Toast.LENGTH_LONG).show();
		      	
		      	System.out.println("==============test end =============");
		    	
		    }
            
          
            
         
            
          //Logic To Play Voice File Only One(Ends)    
            
          //Checking For Destination Point(Starts)
            if(landMarkList.size() == chkList.size())
            {
            	//Reached To Destination
            	//Play voice File For Destination
            	STOP = true;//Stopping Auto Update Service
            }
          //Checking For Destination Point(Starts) 
			
            System.out.println("Check List : "+chkList);
            System.out.println("Monitor Service Status : " +STOP);
            
	    }
		//countDown.setText(String.valueOf(i));// //This causes a runtime error.
		myHandler.post(myRunnable);
	}
	
	final Runnable myRunnable = new Runnable() 
    {
       public void run() 
       {
          if(!STOP)
          {
	       	   //Count Down Timer Implementation STARTS
	       	   		countDownTimer.start();
	       	  //Count Down Timer Implementation END
	       	   //updatedValue.setText(String.valueOf(i));
	       	   //updatedValue.setText(String.valueOf(i)+"\nVoice File :" + voiceFile);
	        	updatedValue.setText("Voice File :" + voiceFile);
	       	
	       	 
	       	 //
	       	 		curLoc.setText(""+curLocation);
	       	 		curLat.setText(""+currentLatitude);
	       	 		curLong.setText(""+CurrentLongitude);
	       	 		
	       	 
		    	
	       	 //
	       	 
          }
          else
          {
	       	   //i=0;
	       	   //tv.setText(String.valueOf(i));
	       	  
	       	   //
	       	   		countDownTimer.cancel();
	       	   //
          }
       }
    };
	
	
// Implementing The Count Down Timer(Starts)
    public class MyCountDownTimer extends CountDownTimer 
	{
			public MyCountDownTimer(long startTime, long interval) 
			{
				super(startTime, interval);
			}

			@Override
			public void onFinish()
			{
				//tv.setText("Time's up!");
				countDown.setText("Done !");
			}

			@Override
			public void onTick(long millisUntilFinished) 
			{
				countDown.setText("" + millisUntilFinished/1000);
			}
    }
//Implementing The Count Down Timer(Ends)     

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	//Check The Request Code is same as what we passes while starting the Activity
    	if(requestCode == 2)
    	{
    		currentLatitude = data.getDoubleExtra("cur_latitude",0);
    		CurrentLongitude = data.getDoubleExtra("cur_longitude",0);
    		
    		//Taking Till 6 Decimal Points Only(Starts)
    		DecimalFormat f = new DecimalFormat("##.000000");
    		currentLatitude = Double.parseDouble(f.format(currentLatitude));
    		CurrentLongitude = Double.parseDouble(f.format(CurrentLongitude));
    		//Taking Till 6 Decimal Points Only(Ends)
    		System.out.println("------- Current GPS Info ------------");
    		System.out.println("Current Latitude : " + currentLatitude);
    		System.out.println("Current Longitude : " + CurrentLongitude);
    	}
    }

@Override
public void onInit(int status) {
	// TODO Auto-generated method stub
	if (status == TextToSpeech.SUCCESS) {

		int result = tts.setLanguage(Locale.US);

		// tts.setPitch(5); // set pitch level

		// tts.setSpeechRate(2); // set speech speed rate

		if (result == TextToSpeech.LANG_MISSING_DATA
				|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
			Log.e("TTS", "Language is not supported");
		} else {
			
			//speakOut();
		}

	} else {
		Log.e("TTS", "Initilization Failed");
	}
	
}

private void speakOut(String instruction1) {
	System.out.println("instruction1"+instruction1);
	//Toast.makeText(getApplicationContext(), "instruction1"+instruction1, Toast.LENGTH_SHORT).show();	
	// TODO Auto-generated method stub
	
if(instruction1.equals("you reached your destination"))
{
	SmsManager smsManager = SmsManager.getDefault();
	String sms = " Dear User you reached your destination";
	// System.out.println("smsManager " + smsManager);
	smsManager.sendTextMessage(phone, null, sms, null,null);
	//String text = instruction.getText().toString();
	 System.out.println("Speak out Instrucation ================>>>>>.:"+instruction1);
		tts.speak(instruction1, TextToSpeech.QUEUE_FLUSH, null);
}
else
{
	
	 System.out.println("Speak out Instrucation ================>>>>>.:"+instruction1);
		tts.speak(instruction1, TextToSpeech.QUEUE_FLUSH, null);	
	
	
	
}
	
	
	
	
}
@Override
public void onDestroy() {
	// Don't forget to shutdown!
	if (tts != null) {
		tts.stop();
		tts.shutdown();
	}
	super.onDestroy();
}
}
