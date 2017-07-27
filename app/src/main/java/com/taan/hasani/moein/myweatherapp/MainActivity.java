package com.taan.hasani.moein.myweatherapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import Data.CityPreference;
import Data.JSONWeatherParser;
import Data.WeatherHttpClient;
import Model.Weather;

public class MainActivity extends AppCompatActivity {

    private TextView city, temp, wind, cloud, pressure, humidity, sunrise, sunset, lastup;
    private Button change;

    Weather weather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        change = (Button) findViewById(R.id.change_city);
        city = (TextView) findViewById(R.id.city_name);
        temp = (TextView) findViewById(R.id.temp);
        wind = (TextView) findViewById(R.id.wind);
        cloud = (TextView) findViewById(R.id.cloud);
        pressure = (TextView) findViewById(R.id.pressure);
        humidity = (TextView) findViewById(R.id.humidity);
        sunrise = (TextView) findViewById(R.id.sunrise);
        lastup = (TextView) findViewById(R.id.last_update);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        RenderWeatherData("Hamedan,ir");
    }

    public void RenderWeatherData(String city) {

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(city + "&units=metric");
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");

        final EditText cityInput = new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("Portland,US");
        builder.setView(cityInput);//***************
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CityPreference cityPreference = new CityPreference(MainActivity.this);
                cityPreference.setCity(cityInput.getText().toString());

                String newCity = cityPreference.getCity();


                //new CityPreference(MainActivity.this).setCity(cityInput.getText().toString());

                //re-render everything again
                RenderWeatherData(newCity);
            }
        });
        builder.show();
    }

    private class WeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {

            String DAta = (new WeatherHttpClient().getWeatherData(params[0]));
            weather = JSONWeatherParser.getWeather(DAta);
            /////
            Log.v("TAG", " city :" + weather.place.getCity() +
                    "\n current:" + weather.currentCondition.getCondition());
            ////
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {

            DateFormat dateformat = DateFormat.getTimeInstance();

//            String sunriseDate=dateformat.format(new Date(weather.place.getSunrise()));
//            String sunsetDate=dateformat.format(new Date(weather.place.getSunset()));
            String updateD = dateformat.format(new Date(weather.place.getLastupdate()));

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());

            city.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText(tempFormat + "°C");
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure() + "hPa");
            wind.setText("Wind: " + weather.wind.getSpeed() + "mps");
            sunrise.setText("Max Temp : " + weather.currentCondition.getMaxTemperature() + "°C");
            lastup.setText("Last Updated: " + updateD);
            cloud.setText("Condition: " + weather.currentCondition.getDescription());

            super.onPostExecute(weather);
        }

    }


}
