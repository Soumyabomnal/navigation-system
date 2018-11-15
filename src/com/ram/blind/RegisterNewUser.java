
package com.ram.blind;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import com.ram.blind.R;
import com.util.Global;
import com.util.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.HttpClient.CustomHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterNewUser extends Activity
{
	//
	String locationList[];
	String professionList[];
	String response = "";
	String res = "";
	//
    
	int dd;
	int mm;
	int yy;
	
	Button dob;
	ImageButton datePiker;
	String sex = "";
	String CurrentDate = "";
	RadioButton radioSexButton;
	RadioButton maleRadioButton;
	Calendar c;
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
     {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.registration);
    	
    	final Intent loginIntent = new Intent(this,Login.class);

        
        
        
        
    	Button register = (Button)findViewById(R.id.user_add_button);
      
        
        final EditText name = (EditText)findViewById(R.id.user_name);
        final EditText username = (EditText)findViewById(R.id.user_username);
        final EditText password = (EditText)findViewById(R.id.user_password);
        final EditText sexx = (EditText)findViewById(R.id.sex);
        final EditText dob = (EditText)findViewById(R.id.dob);
        final EditText address = (EditText)findViewById(R.id.user_address);
        final EditText contactNo = (EditText)findViewById(R.id.user_contact_no);
        final EditText phoneNo = (EditText)findViewById(R.id.user_phone);
        final EditText emailID = (EditText)findViewById(R.id.user_emailID);
       
       
        

        
     
        
       register.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		    String user_name = name.getText().toString();
	 	 	String user_username = username.getText().toString();
	 	 	String user_password = password.getText().toString();
	 	 	String user_dob = dob.getText().toString();
	 	 	
	 	 	String se = sexx.getText().toString();
	 	 	String user_address = address.getText().toString();
	 	 	String user_contact = contactNo.getText().toString();
	 	 	String user_phone = phoneNo.getText().toString();
	 	 	String user_emailID = emailID.getText().toString();
	 	 	//reading ' in double quote
	 	 	user_address = user_address.replaceAll("\'","\\\\'");
	 	 	//post_data=post_data.replaceAll("\'","\\\\'"); // "\'"  == ' and "\\\\'" == \\'
	 	 	
	 	 	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("name",user_name));
        	postParameters.add(new BasicNameValuePair("username",user_username));
        	postParameters.add(new BasicNameValuePair("password",user_password));
        	postParameters.add(new BasicNameValuePair("sex",se));
        	postParameters.add(new BasicNameValuePair("dob",user_dob));
        	postParameters.add(new BasicNameValuePair("address",user_address));
        	postParameters.add(new BasicNameValuePair("contact",user_contact));
        	postParameters.add(new BasicNameValuePair("phone",user_phone));
        	postParameters.add(new BasicNameValuePair("emailId",user_emailID));
        	
        	
        	
        	
           	    
		            	//String valid = "1";
	                	String response = null;
	                	
	                    try 
	                    {
	                    	response = CustomHttpClient.executeHttpPost(Global.URL+"res/JSP/User/JSP-For-Android/registration.jsp", postParameters);
	                	    
	                	    String res=response.toString();
	                	   // res = res.trim();
	                	    res= res.replaceAll("\\s+","");         	              	 
	                	  
	                	    if(res.equals("1"))
	                	    {
	                	    	
	                	    	Toast.makeText(getApplicationContext(),"You have registered sucessfully....",Toast.LENGTH_LONG).show();
	                	    	
	                	    	
	                	    	Intent intent=new Intent(getApplicationContext(),MobNavSys4VisuallyImpaired.class);
	                	    	startActivity(loginIntent);
	                	   }
	                	   else if(res.equals("2"))
                	   {
	                		   
	                		   
	                		   Toast.makeText(getApplicationContext(),"  Sorry,Username Already Exists!!!!!  ",Toast.LENGTH_LONG).show();   
	                		   
	                		  
                		    
                	   }
	     		           
	                	   else
	                	   {
	                	    	Toast.makeText(getApplicationContext(), "Sorry,Registration Failed,Try Again.", Toast.LENGTH_SHORT).show();
	                	   }
	                	    	
	                	} 
	                	catch (Exception e) 
	                	{
	                		Toast.makeText(getApplicationContext(), "Exception : " + e, Toast.LENGTH_SHORT).show();
	                	}
		            }
        	
	});
						
}		
   
    
 
}



