package com.ram.blind;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ram.blind.R;

public class GetBatteryInformation extends Activity 
{
    public TextView tvBatteryHelth,tvBatteryLevel,tvBatteryStatus,tvPluggedOn,tvBatteryScale,tvTechnology,tvTemperature,tvVoltage;
    public ImageView batteryImageIcon;
    String batteryHealth="",batteryPluggedOn="",batteryStatus="";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_info1);
        
        Button ok = (Button)findViewById(R.id.ok_done);
        
        batteryImageIcon=(ImageView)findViewById(R.id.view_battery);
        
        tvBatteryHelth=(TextView)findViewById(R.id.battery_health);
        tvBatteryLevel=(TextView)findViewById(R.id.battery_level);
        tvBatteryStatus=(TextView)findViewById(R.id.battery_status);
        tvPluggedOn=(TextView)findViewById(R.id.battery_pluggedon);
        tvBatteryScale=(TextView)findViewById(R.id.batteryScale);
        tvTechnology=(TextView)findViewById(R.id.batteryTechnology);
        tvTemperature=(TextView)findViewById(R.id.batteryTemperature);
        tvVoltage=(TextView)findViewById(R.id.batteryVoltage);
       
        
        //To Direct Display The Battery Info(Without Button Click)
        this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        
        ok.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				finish();
			}
		});
        
   }
    
   
    
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() 
    {
        public void onReceive(Context context, Intent intent) 
        {
             
            int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0);
            int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT); 
            int  scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
            String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
            
            //BatteryManager Class Constants
            
            //Battery Health Constants (Of BatteryManager Class)
            if(health == 1)
            {
            	batteryHealth = "Unknown";
            }
            else if(health == 2)
            {
            	batteryHealth = "Good";
            }
            else if(health == 3)
            {
            	batteryHealth = "Overheat";
            }
            else if(health == 4)
            {
            	batteryHealth = "Dead";
            }
            else if(health == 5)
            {
            	batteryHealth = "Over Voltage";
            }
            else if(health == 6)
            {
            	batteryHealth = "Unspecified Failure";
            }
            else if(health == 7)
            {
            	batteryHealth = "Cold";
            }
            else
            {
            	batteryHealth = "Unknown";
            }
             
  
            // All About Battery Plugged On
            if(plugged == 0)
            {
            	batteryPluggedOn = "Running On Battery";
            }
            else if(plugged == 1)
            {
            	batteryPluggedOn = "AC Charger";
            }
            else if(plugged == 2)
            {
            	batteryPluggedOn = "USB Port";
            }
            else if(plugged == 4)
            {
            	batteryPluggedOn = "WireLess";
            }
            
            // All About Battery Status
            if(status == 1)
            {
            	batteryStatus = "Unknown";
            }
            else if(status == 2)
            {
            	batteryStatus = "Charging";
            }
            else if(status == 3)
            {
            	batteryStatus = "Discharging";
            }
            else if(status == 4)
            {
            	batteryStatus = "Not Charging";
            }
            else if(status == 5)
            {
            	batteryStatus = "Full";
            }
            
            //Setting The Values
            batteryImageIcon.setImageResource(icon_small);
            
            tvBatteryHelth.setText(batteryHealth+"("+health+")");
            tvBatteryLevel.setText(level+" %");
            tvBatteryStatus.setText(batteryStatus+"("+status+")");
            tvPluggedOn.setText(batteryPluggedOn+"("+plugged+")");
            tvBatteryScale.setText(new Integer(scale).toString());
            tvTechnology.setText(technology);
            tvTemperature.setText(new Integer(temperature).toString());
            tvVoltage.setText(new Integer(voltage).toString());
            
           
        }
    };
     
}



