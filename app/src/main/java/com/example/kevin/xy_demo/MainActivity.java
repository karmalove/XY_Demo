package com.example.kevin.xy_demo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Bundle bundle;
    private Coordinates mCoordinates;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mCoordinates.setData(bundle);
                    mCoordinates.invalidate();
            }
        }
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        bundle = new Bundle();

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500, 300);
        params.setMargins(80, 80, 80, 80);
        mCoordinates = new Coordinates(this);
        mCoordinates.setLayoutParams(params);
        relativeLayout.addView(mCoordinates);
        //开启线程
        new Thread(new GameThread()).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    /**
     * 获得三轴加速度数据，传到自定义view里面
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        int x = (int) event.values[0];
        int y = (int) event.values[1];
        int z = (int) event.values[2];


        bundle.putInt("x", x);
        bundle.putInt("y", y);
        bundle.putInt("z", z);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class GameThread implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                Message message = new Message();
                message.what = 1;
                // 发送消息
                MainActivity.this.mHandler.sendMessage(message);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }


    }
}
