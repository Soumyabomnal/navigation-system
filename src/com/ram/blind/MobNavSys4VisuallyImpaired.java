
package com.ram.blind;


import com.ram.blind.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MobNavSys4VisuallyImpaired extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = 
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	
    	getMenuInflater().inflate(R.menu.main, menu);
    	
		return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	  
        switch (item.getItemId())
        {
        case R.id.signupp:
           
           
        	Intent signUpIntent = new Intent(this,RegisterNewUser.class);
    		startActivity(signUpIntent);
            return true;
            
  
        case R.id.login:
        	Intent loginIntent = new Intent(this,Login.class);
    		startActivity(loginIntent);
            return true;
 
       
 
    
 
       
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
	public void onBackPressed() //Disabling The Back Button
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent homeIntent = new Intent(this,MobNavSys4VisuallyImpaired.class);
		startActivity(homeIntent);
	}
    
   
}
































































































































































































































































































































































































































