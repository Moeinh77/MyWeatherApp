package Data;

import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by paulodichone on 3/11/15.
 */

public class WeatherHttpClient {

    public String getWeatherData(String place) {
        HttpURLConnection connection;
        InputStream inputStream;

        try {

            connection = (HttpURLConnection)
                    (new URL("http://api.openweathermap.org/data/2.5/weather?q=" +
                            place + "&appid=db10fde4200e19142ad5c02ba9c9fb29")).
                            openConnection();

            connection.setRequestMethod("GET");
            connection.setDoInput(true); //Sets the flag indicating whether this {@code URLConnection} allows input
            connection.setDoOutput(true);
            connection.connect();

            //Read the response
            StringBuffer stringBuffer = new StringBuffer();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line ;

            while ((line = bufferedReader.readLine()) != null)
                stringBuffer.append(line + "\r\n");

            inputStream.close();
            connection.disconnect();
            return stringBuffer.toString();

        } catch (IOException e) {

            e.printStackTrace();
        } //finally {
//            try {
//                inputStream.close();
//            } catch (IOException e) {
//
//            }

        //connection.disconnect();


        return null;
    }
}