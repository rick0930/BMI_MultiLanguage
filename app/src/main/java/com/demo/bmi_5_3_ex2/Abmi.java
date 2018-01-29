package com.demo.bmi_5_3_ex2;

import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Locale;

public class Abmi extends AppCompatActivity {

    private String lang;
    private boolean isEng;

    //英語系的UI元件
    private EditText field_feet;
    private EditText field_inch;
    private EditText field_weight;

    //繁中語系的UI元件
    private EditText tw_height;
    private EditText tw_weight;

    //不分語系的UI元件
    private Button calcbutton;
    private TextView view_result;
    private TextView view_suggest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        lang = Locale.getDefault().getLanguage();
        if (lang.equals("en"))
            isEng = true;
        else
            isEng = false;

        findViews();
        setListeners();
    }

    private void findViews() {
        if (isEng) {
            field_feet = (EditText) findViewById(R.id.feet);
            field_inch = (EditText) findViewById(R.id.inch);
            field_weight = (EditText) findViewById(R.id.weight);
        } else {
            tw_height = (EditText) findViewById(R.id.tw_height);
            tw_weight = (EditText) findViewById(R.id.tw_weight);
        }
        calcbutton = (Button) findViewById(R.id.submit);
        view_result = (TextView) findViewById(R.id.result);
        view_suggest = (TextView) findViewById(R.id.suggest);
    }

    private void setListeners() {
        calcbutton.setOnClickListener(BMIListener);
    }

    private Button.OnClickListener BMIListener = new Button.OnClickListener() {
        public void onClick(View v) {
            try {
                double BMI = calcBMI();
                DecimalFormat nf = new DecimalFormat("0.00");
                view_result.setText(getText(R.string.bmi_result) + nf.format(BMI));
                view_suggest = (TextView) findViewById(R.id.suggest);
                if (BMI > 25) {
                    view_suggest.setText(R.string.advice_heavy);
                } else if (BMI < 20) {
                    view_suggest.setText(R.string.advice_light);
                } else {
                    view_suggest.setText(R.string.advice_average);
                }
            } catch (Exception e) { }
        }
    };

    double calcBMI() {
        double BMI = 0;
        if (isEng) {
            double height = (Double.parseDouble(
                field_feet.getText().toString()) * 12 + Double.parseDouble(
                field_inch.getText().toString())) * 2.54 / 100;
            double weight = Double.parseDouble(field_weight.getText().toString()) * 0.45359;
            BMI = weight / (height * height);
        } else {
            double height = Double.parseDouble(tw_height.getText().toString()) / 100;
            double weight = Double.parseDouble(tw_weight.getText().toString());
            BMI = weight / (height * height);
        }

        return BMI;
    }

    public void onLocale(View v) {
        Locale locale = null;
        if (isEng) {
            locale = new Locale("zh", "TW");
            isEng = false;
        } else {
            locale = new Locale("en", "US");
            isEng = true;
        }

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_bmi);
        setActionBarTitle();
        findViews();
        setListeners();
    }

    void setActionBarTitle(){
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.app_name));
    }
}