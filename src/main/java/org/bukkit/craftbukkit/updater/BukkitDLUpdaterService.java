package org.bukkit.craftbukkit.updater;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BukkitDLUpdaterService
{
  private static final String API_PREFIX_ARTIFACT = "/api/1.0/downloads/projects/craftbukkit/view/";
  private static final String API_PREFIX_CHANNEL = "/api/1.0/downloads/channels/";
  private static final DateDeserializer dateDeserializer = new DateDeserializer();
  private final String host;
  
  public BukkitDLUpdaterService(String host) {
    this.host = host;
  }
  
  public ArtifactDetails getArtifact(String slug, String name) {
    try {
      return fetchArtifact(slug);
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(BukkitDLUpdaterService.class.getName()).log(Level.WARNING, "Could not get " + name + ": " + ex.getClass().getSimpleName());
    } catch (IOException ex) {
      Logger.getLogger(BukkitDLUpdaterService.class.getName()).log(Level.WARNING, "Could not get " + name + ": " + ex.getClass().getSimpleName());
    }
    
    return null;
  }
  
  public ArtifactDetails fetchArtifact(String slug) throws UnsupportedEncodingException, IOException {
    URL url = new URL("http", this.host, "/api/1.0/downloads/projects/craftbukkit/view/" + slug);
    InputStreamReader reader = null;
    try
    {
      reader = new InputStreamReader(url.openStream());
      Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, dateDeserializer).setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
      ArtifactDetails fromJson = (ArtifactDetails)gson.fromJson(reader, ArtifactDetails.class);
      
      return fromJson;
    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }
  
  public ArtifactDetails.ChannelDetails getChannel(String slug, String name) {
    try {
      return fetchChannel(slug);
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(BukkitDLUpdaterService.class.getName()).log(Level.WARNING, "Could not get " + name + ": " + ex.getClass().getSimpleName());
    } catch (IOException ex) {
      Logger.getLogger(BukkitDLUpdaterService.class.getName()).log(Level.WARNING, "Could not get " + name + ": " + ex.getClass().getSimpleName());
    }
    
    return null;
  }
  
  public ArtifactDetails.ChannelDetails fetchChannel(String slug) throws UnsupportedEncodingException, IOException {
    URL url = new URL("http", this.host, "/api/1.0/downloads/channels/" + slug);
    InputStreamReader reader = null;
    try
    {
      reader = new InputStreamReader(url.openStream());
      Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, dateDeserializer).setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
      ArtifactDetails.ChannelDetails fromJson = (ArtifactDetails.ChannelDetails)gson.fromJson(reader, ArtifactDetails.ChannelDetails.class);
      
      return fromJson;
    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }
  
  static class DateDeserializer implements com.google.gson.JsonDeserializer<Date> {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public Date deserialize(com.google.gson.JsonElement je, java.lang.reflect.Type type, com.google.gson.JsonDeserializationContext jdc) throws com.google.gson.JsonParseException {
      try {
        return format.parse(je.getAsString());
      } catch (java.text.ParseException ex) {
        throw new com.google.gson.JsonParseException("Date is not formatted correctly", ex);
      }
    }
  }
}
