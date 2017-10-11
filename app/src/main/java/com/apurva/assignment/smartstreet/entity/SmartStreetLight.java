package com.apurva.assignment.smartstreet.entity;

/**
 * Created by Apurva on 2/19/2017.
 */

import java.util.Date;

public class SmartStreetLight {
    private int Light_ID;
    private String Name;
    private String Description;
    private String installationDate;
    private String Latitude;
    private String Longitude;


    public SmartStreetLight(int Light_IDIn,String NameIn,String DescriptionIn, String installationDateIn,String LatitudeIn, String LongitudeIn){
        Light_ID = Light_IDIn;
        Name = NameIn;
        Description = DescriptionIn;
        installationDate = installationDateIn;
        Latitude = LatitudeIn;
        Longitude = LongitudeIn;

    }

    public SmartStreetLight clone(SmartStreetLight other) {
        SmartStreetLight newSmartStreetLight = new SmartStreetLight(other.Light_ID, other.Name,other.Description,other.installationDate,other.Longitude,other.Latitude);
        return newSmartStreetLight;
    }

    public int getId() { return Light_ID; }
    public String getName() { return Name; }
    public String getDescription() {return Description;}
    public String getInstallationDate() { return installationDate; }
    public String getLongitude() { return Longitude; }
    public String getLatitude() { return Latitude; }

    public void setId(int lightIdIn)
    {
        Light_ID = lightIdIn;
    }

    public void setName(String nameIn)
    {
        Name = nameIn;
    }
    public void setDescription(String descriptionIn)
    {
        Description = descriptionIn;
    }
    public void setInstallationDate(String installationDateIn)
    {
        installationDate = installationDateIn;
    }

    public void setLatitude(String latitudeIn)
    {
        Latitude = latitudeIn;
    }
    public void setLongitude(String longitudeIn)
    {
        Longitude = longitudeIn;
    }


}
