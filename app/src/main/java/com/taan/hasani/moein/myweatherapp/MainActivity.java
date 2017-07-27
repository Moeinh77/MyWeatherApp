package com.taan.hasani.moein.myweatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import Data.JSONWeatherParser;
import Data.WeatherHttpClient;
import Model.Weather;

public class MainActivity extends AppCompatActivity {

    private TextView city, temp, wind, cloud, pressure, humidity, sunrise, sunset, lastup;
    private ImageView icon;
    Weather weather=new Weather();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = (TextView) findViewById(R.id.city_name);
        temp = (TextView) findViewById(R.id.temp);
        wind = (TextView) findViewById(R.id.wind);
        cloud = (TextView) findViewById(R.id.cloud);
        pressure = (TextView) findViewById(R.id.pressure);
        humidity = (TextView) findViewById(R.id.humidity);
        sunrise = (TextView) findViewById(R.id.sunrise);
        sunset = (TextView) findViewById(R.id.sunset);
        lastup = (TextView) findViewById(R.id.last_update);
        icon = (ImageView) findViewById(R.id.imageView);

        RenderWeatherData("NewYork,Us");//city.getText().toString());


}
    public void RenderWeatherData(String city) {

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(city+"&units=metric");

    }

    private class WeatherTask extends AsyncTask<String,Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {

            String DAta=(new WeatherHttpClient().getWeatherData(params[0]));
            weather= JSONWeatherParser.getWeather(DAta);
            /////
            Log.v("TAG"," city :"+weather.place.getCity()+
            "\n current:"+weather.currentCondition.getCondition());
            ////
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {

            DateFormat dateformat=DateFormat.getTimeInstance();

//            String sunriseDate=dateformat.format(new Date(weather.place.getSunrise()));
//            String sunsetDate=dateformat.format(new Date(weather.place.getSunset()));
            String updateD=dateformat.format(new Date(weather.place.getLastupdate()));

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());

            city.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText( tempFormat + "°C");
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure() + "hPa");
            wind.setText("Wind: " + weather.wind.getSpeed() + "mps");
            sunrise.setText("Max Temp : " + weather.currentCondition.getMaxTemperature() + "°C");
            sunset.setText("Min Temp: " + weather.currentCondition.getMinTemperature() + "°C"  );
            lastup.setText("Last Updated: " + updateD );
            cloud.setText("Condition: " +  weather.currentCondition.getDescription() );

            super.onPostExecute(weather);
        }

    }




}
