
package com.ram.blind;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.HttpClient.CustomHttpClient;
import com.ram.blind.R;
import com.util.Global;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		final Intent homePage = new Intent(this,HomePage.class);
	    ImageButton login = (ImageButton)findViewById(R.id.btnLogin);
	    
	    final EditText un = (EditText)findViewById(R.id.username);
        final EditText pw = (EditText)findViewById(R.id.pwd);
        final TextView exception = (TextView)findViewById(R.id.exception);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
  		StrictMode.setThreadPolicy(policy);
		
	    
	    /* Login Action */
        login.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v) 
			{
				String uName = un.getText().toString();									
				String password = pw.getText().toString();
				System.out.println("user name :"+uName.trim());
				System.out.println("Password"+password.trim());
				
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            	postParameters.add(new BasicNameValuePair("username", uName.trim()));
            	postParameters.add(new BasicNameValuePair("password", password.trim()));
				
				
				if("".equals(uName) && "".equals(password))
				{
					Toast.makeText(getApplicationContext(), "Please,Enter the Username and Password.", Toast.LENGTH_LONG).show();
				}
				
				else if("".equals(uName))
				{
					Toast.makeText(getApplicationContext(), "Please,Enter the Username.", Toast.LENGTH_LONG).show();
				}
				
				else if("".equals(password))
				{
					Toast.makeText(getApplicationContext(),"Please,Enter the Password.", Toast.LENGTH_LONG).show();
				}
				
				
				// Our Main Business Starts Here
				else
				{
					String response = null;
	            	try 
	            	{
	            		System.out.println("=========Connection to HTTP is Started ===========");
	            		
	            	    response = CustomHttpClient.executeHttpPost(Global.URL+"res/JSP/User/JSP-For-Android/Login.jsp",postParameters);
	            	    
	            	    String res=response.toString();
	            	    // res = res.trim(); //removing front and trailing byte spaces
	            	    res= res.replaceAll("\\s+","");   //removing spaces in between the words      	              	 
	            	   
	            	    if(res.equals("true"))
	            	    {
	            		    /* Saving and Retrieving The Data (Starts) [Similar To Session Management In J2EE] */
	            		   	   //Application shared preferences allows you to save and retrieve data in key, value pair. 
	            		  
		            		   SharedPreferences pref = getApplicationContext().getSharedPreferences("any_prefname",MODE_PRIVATE);
		            		   Editor editor = pref.edit();
		            		   
		            		   // Saving The Data
		            		   
		            		   //editor.putString("key_name", "string value"); // Storing string
		            		   editor.putString("username",uName); // Storing string
		            		   editor.commit(); // commit changes
		            		   
		            		   // Retrieving The Data
		            		  
		            		   /*
			            		   pref.getString("key_name", null); // getting String
			                       pref.getInt("key_name", null); // getting Integer
			                       pref.getFloat("key_name", null); // getting Float
			                       pref.getLong("key_name", null); // getting Long
			                       pref.getBoolean("key_name", null); // getting boolean
		            		   */
		            		   
		            		   // Removing The Data
		            		   
		            		   /*
			            		   editor.remove("student_name");//will delete student_name
			            		   editor.commit(); 
		            		   */
		            		   
		            		   // Clearing The Data
		            		   /*
									editor.clear();
									editor.commit();
								*/
		            		   
	            		   /* Saving and Retrieving The Data (Ends) */
		            		   
	            		    startActivity(homePage);
		   					Toast.makeText(getApplicationContext(), "Login Succees.....", Toast.LENGTH_LONG).show();
		   					Toast.makeText(getApplicationContext(), "Welcome To MobNavSys For Visually Impaired", Toast.LENGTH_SHORT).show();
	            	   }
	            	   else
	            	   {
	            	    	Toast.makeText(getApplicationContext(), "Login Failed,Username and password does not match.", Toast.LENGTH_SHORT).show();
	            	   }
	            	    	
	            	} 
	            	catch (Exception e) 
	            	{
	            		exception.setText(e.toString());
	            		System.out.println("******* Exception *******");
	            		e.printStackTrace();
	            	}
				  }
				}
				
			
		});
	}
}
