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
    public HashMap<String, JSONObject> lectureResources;
    public HashMap<String, JSONObject> mailResources;

    IntegrationData(Context _context) throws IllegalAccessException {
        mailResources = new HashMap<>();
        lectureResources = new HashMap<>();
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
        String resourceMap = "";
        for(int i=0; i<xmlFields.length; i++){
            XmlPullParser parser = context.getResources().getXml(xmlFields[i].getInt(null));
            String js = "";
            try{
                while (parser.next() != XmlResourceParser.END_DOCUMENT) {
                    switch (parser.getEventType()) {
                        case XmlResourceParser.START_DOCUMENT:
                            parser.next();
                            resourceMap = parser.getName();
                            js+="<"+parser.getName()+">";
                            break;
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
                switch(resourceMap){
                    case "mails":
                        mailResources.put(xmlFields[i].getName(), new XmlToJson.Builder(js).build().toJson());
                        break;
                    case "lecture":
                        lectureResources.put(xmlFields[i].getName(), new XmlToJson.Builder(js).build().toJson());
                        break;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
