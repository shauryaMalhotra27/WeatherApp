package com.example.weatherapp;

import android.content.Context;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public ToggleButton changeTempFormat;
    LocationManager locationManager;
    String url = "http://api.weatherapi.com/v1/forecast.json?q={cityName}&key={apiKey}";
    String apiKey = "7d2f5b5a093f482aa9f70213231309";
    AutoCompleteTextView citySearch;
    Call<ExampleCities> exampleCitiesCall;
    private TextView citySearchBtn, showTemp, cityView, timeShow, minTempShow, maxTEmpShow, humidityShow, windSpeed, sunriseShow, sunsetShow, weatherConditionView;
    private ImageView imageView, iconViewCity;
    private String TAG = "main";
    private Call<Example> example1;
    private int dayCode;
    private int cloud;
    private ScrollView backgroundImage;
    private RecyclerView RVweather;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);

        citySearch = findViewById(R.id.citySearch);

        citySearchBtn = findViewById(R.id.citySearchBtn);
        backgroundImage = findViewById(R.id.backgroundImage);
        showTemp = findViewById(R.id.showTemp);
        cityView = findViewById(R.id.cityView);
        imageView = findViewById(R.id.imageView);
        timeShow = findViewById(R.id.timeShow);
        minTempShow = findViewById(R.id.minTempShow);
        maxTEmpShow = findViewById(R.id.maxTEmpShow);
        humidityShow = findViewById(R.id.humidityShow);
        windSpeed = findViewById(R.id.windSpeed);
        sunriseShow = findViewById(R.id.sunrise);
        sunsetShow = findViewById(R.id.sunset);
        iconViewCity = findViewById(R.id.iconViewCity);
        swipeLayout = findViewById(R.id.refreshFunction);
        weatherConditionView = findViewById(R.id.weatherConditionView);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        RVweather = findViewById(R.id.RVweather);
        changeTempFormat = findViewById(R.id.changeTempFormat);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RVweather.setLayoutManager(horizontalLayoutManager);


        citySearchBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                citiesCountry();
                getWeather();

            }
        });


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeather();
                swipeLayout.setRefreshing(false);
            }
        });
    }

    public void getWeather() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface myApi = retrofit.create(ApiInterface.class);

        String cityName = citySearch.getText().toString().trim();

        example1 = myApi.getweather(cityName, apiKey);
        example1.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                if (response.code() == 400) {
                    Toast.makeText(MainActivity.this, "Enter correct city !", Toast.LENGTH_SHORT).show();
                } else if (!(response.isSuccessful())) {
                    Log.d(TAG, "resonse code = " + response.code());
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    //making objects for the particular classes
                    Example mydataFromExample = response.body();
                    Current current = mydataFromExample.getCurrent();
                    Location location = mydataFromExample.getLocation();
                    Forecast forecast = mydataFromExample.getForecast();

                    //way 1 JIC for a better understanding
                    List<Forecastday> forecastdays = forecast.getForecastday();
                    List<Hour> hour = forecast.getForecastday().get(0).getHour();

                    //setting adapter
                    WeatherAdapter adapter = new WeatherAdapter(MainActivity.this, hour, changeTempFormat);
                    RVweather.setAdapter(adapter);

                    // calling by reference and setting values to the respective viewText fields
                    // Double tempC = current.getTempC();
                    // int tempCInt = tempC.intValue();
                    int tempCInt = current.getTempC().intValue();
                    int tempFInt = current.getTempF().intValue();
                    Log.d(TAG, "onResponse: " + tempFInt);
                    String cityL = location.getName();
                    String imageC = current.getCondition().getIcon();
                    String timeL = location.getLocaltime().substring(10);
                    String humidity = String.valueOf(current.getHumidity());
                    int windspeed = current.getWindKph().intValue();
                    String weatherCond = current.getCondition().getText();
                    int minTempC = forecast.getForecastday().get(0).getDay().getMintempC().intValue();
                    int minTempF = forecast.getForecastday().get(0).getDay().getMintempF().intValue();
                    Log.d(TAG, "onResponse: " + minTempF);
                    int maxTempC = forecast.getForecastday().get(0).getDay().getMaxtempC().intValue();
                    int maxTempF = forecast.getForecastday().get(0).getDay().getMaxtempF().intValue();
                    Log.d(TAG, "onResponse: " + maxTempF);
                    String sunRise = forecast.getForecastday().get(0).getAstro().getSunrise();
                    String sunSet = forecast.getForecastday().get(0).getAstro().getSunset();
                    //way 2
                    //Double maxtemp2 = forecast.getForecastday().get(0).getDay().getMaxtempC();
                    //setting values to their respective fields
                    showTemp.setText(tempCInt + "°C");
                    cityView.setText(cityL);
                    dayCode = current.getIsDay();
                    cloud = current.getCloud();
                    timeShow.setText(timeL);
                    humidityShow.setText(humidity);
                    windSpeed.setText(windspeed + " kmph");
                    weatherConditionView.setText(weatherCond);
                    minTempShow.setText("min = " + minTempC + "°C");
                    maxTEmpShow.setText("max = " + maxTempC + "°C");
                    sunriseShow.setText(sunRise);
                    sunsetShow.setText(sunSet);

                    changeTempFormat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (changeTempFormat.isChecked()) {
                                showTemp.setText(tempFInt + "°F");
                                minTempShow.setText("min = " + minTempF + "°F");
                                maxTEmpShow.setText("max = " + maxTempF + "°F");
                            } else {
                                showTemp.setText(tempCInt + "°C");
                                minTempShow.setText("min = " + minTempC + "°C");
                                maxTEmpShow.setText("max = " + maxTempC + "°C");
                            }
                        }
                    });

                    Glide.with(MainActivity.this)
                            .load(Uri.parse("https:" + imageC))
                            .into(imageView);

                    Log.d(TAG, "daycode" + dayCode);

                    if (cloud == 0) {

                        backgroundImage.setBackground(getDrawable(R.drawable.clearsky_bg_screen));

                    } else if (cloud <= 80) {

                        backgroundImage.setBackground(getDrawable(R.drawable.partly_cloudy_screen));

                    } else {

                        backgroundImage.setBackground(getDrawable(R.drawable.rainy_screen));

                    }

                    dayOrNight();


                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d(TAG, "error" + t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void dayOrNight() {
        if (dayCode == 1) iconViewCity.setImageResource(R.drawable.sun_icon_yellow);
        else {
            iconViewCity.setImageResource(R.drawable.moon_icon_yellow);

        }

    }

    public void citiesCountry() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://countriesnow.space/api/v0.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface2 myApi2 = retrofit.create(ApiInterface2.class);

        exampleCitiesCall = myApi2.citiesCountry();
        exampleCitiesCall.enqueue(new Callback<ExampleCities>() {
            @Override
            public void onResponse(Call<ExampleCities> call, Response<ExampleCities> response) {


                ExampleCities myDataForCities = response.body();

                ArrayList<Datum> data = myDataForCities.getData();
                Log.d(TAG, "data" + data);

                //initializing empty list
                ArrayList<String> allDATA = new ArrayList<>();

                for (int i = 0; i < data.size(); i++) {
                    ArrayList<String> allCity = data.get(i).getCities();

                    for (int j = 0; j < allCity.size(); j++) {
                        String a = allCity.get(j);
                        allDATA.add(String.valueOf(a));
                    }
                }


                Log.d(TAG, "allDATA" + allDATA);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_activated_1, allDATA);
                citySearch.setAdapter(arrayAdapter);


            }

            @Override
            public void onFailure(Call<ExampleCities> call, Throwable t) {

            }
        });


    }


}