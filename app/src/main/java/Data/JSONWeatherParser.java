package Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.CurrentCondition;
import Model.Place;
import Model.Weather;
import Model.Wind;
import Util.Utils;

/**
 * Created by Moein on 7/23/2017.
 */

public class JSONWeatherParser {

    public static Weather getWeather(String data){

        try {
            JSONObject jsonObject=new JSONObject(data);

            Weather weather=new Weather();

            ////////////////
            Place place=new Place();

            JSONObject coord=jsonObject.getJSONObject("coord");
            place.setLat(Utils.getFloat("lat",coord));
            place.setLon(Utils.getFloat("lon",coord));

            JSONObject sysObj = Utils.getObject("sys", jsonObject);
            place.setCountry(Utils.getString("country", sysObj));
            place.setLastupdate(Utils.getInt("dt", jsonObject));
            place.setSunrise(Utils.getInt("sunrise", sysObj));
            place.setSunset(Utils.getInt("sunset", sysObj));
            place.setCity(Utils.getString("name", jsonObject));
            weather.place = place;

            weather.place=place;
            ////////////////
            JSONArray jsonArray=jsonObject.getJSONArray("weather");
            JSONObject json_weather=jsonArray.getJSONObject(0);

            CurrentCondition currentCondition=new CurrentCondition();

            currentCondition.setDescription(Utils.getString("description",json_weather));
            currentCondition.setWeatherId(Utils.getInt("id",json_weather));
            currentCondition.setIcon(Utils.getString("icon",json_weather));
            currentCondition.setCondition(Utils.getString("main",json_weather));

            JSONObject mainObject=jsonObject.getJSONObject("main");

            currentCondition.setPressure(Utils.getInt("pressure",mainObject));
            currentCondition.setHumidity(Utils.getInt("humidity",mainObject));
            currentCondition.setMaxTemperature(Utils.getFloat("temp_max",mainObject));
            currentCondition.setMinTemperature(Utils.getFloat("temp_min",mainObject));
            currentCondition.setTemperature(Utils.getFloat("temp",mainObject));

            weather.currentCondition=currentCondition;
            ///////////////////
            JSONObject json_wind=jsonObject.getJSONObject("wind");

            Wind wind=new Wind();

            wind.setDeg(Utils.getInt("deg",json_wind));
            wind.setSpeed(Utils.getFloat("speed",json_wind));

            weather.wind=wind;
            /////////////////////
            JSONObject json_clouds=jsonObject.getJSONObject("clouds");

            weather.clouds.setPercipitation(Utils.getInt("all",json_clouds));

            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }


    }

}
