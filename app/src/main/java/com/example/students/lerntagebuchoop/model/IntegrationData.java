package com.example.students.lerntagebuchoop.model;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.example.students.lerntagebuchoop.R;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Field;
import java.util.HashMap;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class IntegrationData {
    private static IntegrationData instance;
    public HashMap<String, JSONObject> resources;

    IntegrationData(Context _context) throws IllegalAccessException {
        resources = new HashMap<>();
        loadAllResources(_context);
    }

    public static IntegrationData getInstance(Context context){
        if(instance == null){
            try {
                instance = new IntegrationData(context);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static IntegrationData getInstance(){
        return instance;
    }

    private void loadAllResources(Context context) throws IllegalAccessException {
        Field[] xmlFields = R.xml.class.getFields();

        for(int i=0; i<xmlFields.length; i++){
            XmlPullParser parser = context.getResources().getXml(xmlFields[i].getInt(null));
            String js = "";
            try{
                while (parser.next() != XmlResourceParser.END_DOCUMENT) {
                    switch (parser.getEventType()) {
                        case XmlResourceParser.START_TAG:
                            js+="<"+parser.getName()+">";
                            break;
                        case XmlResourceParser.TEXT:
                            js+= parser.getText();
                            break;
                        case XmlResourceParser.END_TAG:
                            js += "</"+parser.getName()+">";
                            break;
                    }
                }
                resources.put(xmlFields[i].getName(), new XmlToJson.Builder(js).build().toJson());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
