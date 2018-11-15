/**
 @Author : Munna Kumar Singh
 Date : Sep 12, 2013
 File : SpinnerObject.java
 Package : com.util
*/

package com.util;

public class SpinnerObject 
{

    private int databaseId;
    private String databaseValue;

    public SpinnerObject (int databaseId , String databaseValue ) 
    {
        this.databaseId = databaseId;
        this.databaseValue = databaseValue;
    }

    public int getId () 
    {
        return databaseId;
    }

    public String getValue () 
    {
        return databaseValue;
    }

    @Override
    public String toString () 
    {
        return databaseValue;
    }

}
