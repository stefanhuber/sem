package fh_ku.audiomaster;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, SensorEventListener {

    private MediaRecorder recorder;
    private MediaPlayer player;
    private String filepath;

    private Button playButton;
    private Button recordButton;
    private TextView textX;
    private TextView textY;
    private TextView textZ;

    private boolean isPlaying = false;
    private boolean isRecording = false;
    private boolean isSpeaking = true;

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playButton = (Button) findViewById(R.id.button_play);
        recordButton = (Button) findViewById(R.id.button_record);
        tts = new TextToSpeech(this, this);

        filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecording.3gp";

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        textX = (TextView) findViewById(R.id.x_axis);
        textY = (TextView) findViewById(R.id.y_axis);
        textZ = (TextView) findViewById(R.id.z_axis);
    }

    public void record(View view)
    {
        if (!isRecording) {
            startRecording();
        } else {
            stopRecording();
        }

        isRecording = !isRecording;
    }

    public void play(View view)
    {
        if (!isPlaying) {
            startPlaying();
        } else {
            stopPlaying();
        }

        isPlaying = !isPlaying;
    }

    public void talk(View view)
    {
        if (isSpeaking) {
            tts.stop();
        } else {
            while (true) {
                if (!tts.isSpeaking()) {
                    tts.speak("Hello Hello Hello",TextToSpeech.QUEUE_FLUSH,null);
                }
                if (!isSpeaking) {
                    break;
                }
            }
        }

        isSpeaking = !isSpeaking;
    }

    public void startPlaying()
    {
        player = new MediaPlayer();
        try {
            player.setDataSource(filepath);
            player.prepare();
        } catch (IOException e) {
            Log.e("audiomaster", "preparing audioplayer: " + e.getMessage());
        }
        player.start();
    }

    public void stopPlaying()
    {
        player.release();
        player = null;
    }

    public void startRecording()
    {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(filepath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e("audiomaster", "preparing audiorecorder: " + e.getMessage());
        }

        recorder.start();
    }

    public void stopRecording()
    {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInit(int i) {
        tts.setLanguage(Locale.US);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        textX.setText(Double.toString(sensorEvent.values[0]));
        textY.setText(Double.toString(sensorEvent.values[1]));
        textZ.setText(Double.toString(sensorEvent.values[2]));

        tts.setSpeechRate((float) sensorEvent.values[0] / (float) 10);
        tts.setPitch((float) sensorEvent.values[1] / (float) 10);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
