package com.example.kirill.mess.Parsers;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Kirill on 29.11.2016.
 */

public class DataPars {
    public String data;

    public DataPars(String data){
        this.data = data;
        Pars();
    }
    public boolean Pars(){
        /*long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy hh:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/GMT-3"));
        char[] dateString = sdf.format(date).toCharArray();
        char[] dataMess = data.toCharArray();
        Log.d("TAP DATA", " " + new String(dateString) + " ОГО " +new String(dataMess));
       // if(new String(dateString) == new String(dataMess)){
        if(Arrays.equals(dataMess,dateString)) {
            data = "только что";
            return true;}
        /*if(dateString[0] == dataMess[0] && dateString[1] == dataMess[1] && dateString[3] == dataMess[3]
                && dateString[4] == dataMess[4] && dateString[5] == dataMess[5] && dateString[6] == dataMess[6]
                && dateString[7] == dataMess[7] && dateString[11] == dataMess[11] && dateString[12] == dataMess[12]){
            data = data.substring(13); return true;}*/
//        if(dateString[10] != dataMess[10] || dateString[11] != dataMess[11]){data = data.substring(0,11); return true;}
        //if(dateString[13]==dataMess[13] && dateString[14] == dataMess[14]){data = dateString[17] ; return;}
        //if(dateString[11] == dataMess[11] && dateString[12] == dataMess[12]){data = data.substring(0,7) + data.substring(13); return;}
       return false;
    }
    public String getData(){
        return data;
    }
    public void setData(String data){
        this.data = data;
    }
}
