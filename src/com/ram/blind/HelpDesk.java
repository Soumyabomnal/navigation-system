/**
 @Author : Munna Kumar Singh
*/
package com.ram.blind;

import com.ram.blind.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HelpDesk extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.help);
    	
    	ImageButton home = (ImageButton)findViewById(R.id.home);
    	ImageButton exit = (ImageButton)findViewById(R.id.exit);
    	
    	final Intent homeIntent = new Intent(this,MobNavSys4VisuallyImpaired.class);
    	
    	home.setOnClickListener(new View.OnClickListener() 
    	{
			
			public void onClick(View arg0) 
			{
				startActivity(homeIntent);
				
			}
		});
    	
    	exit.setOnClickListener(new View.OnClickListener() 
    	{
			
			public void onClick(View arg0) 
			{
				startActivity(homeIntent);
				
			}
		});
    }
}
