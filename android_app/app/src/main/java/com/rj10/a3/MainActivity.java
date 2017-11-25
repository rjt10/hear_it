package com.rj10.a3;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.ResultReceiver;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;
    private final int PERMISSION_REQUEST_RECORD_AUDIO = 2;

    Button mOnButton;
    TextView mStatus;
    SpeechResultReceiver speechResultReceiver = null;
    int requestCount = 0;
    private VoiceRecorder mVoiceRecorder;
    private SpeechService mSpeechService;

    private final VoiceRecorder.Callback mVoiceCallback = new VoiceRecorder.Callback() {

        @Override
        public void onVoiceStart() {
            showStatus("onVoiceStart");
            if (mSpeechService != null) {
                mSpeechService.startRecognizing(mVoiceRecorder.getSampleRate());
            }
        }

        @Override
        public void onVoice(byte[] data, int size) {
            showStatus("onVoice");
            if (mSpeechService != null) {
                mSpeechService.recognize(data, size);
            }
        }

        @Override
        public void onVoiceEnd() {
            showStatus("onVoiceEnd");
            if (mSpeechService != null) {
                mSpeechService.finishRecognizing();
            }
        }

        @Override
        public void onStatusUpdate(String msg) {
            showStatus(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speechResultReceiver = new SpeechResultReceiver(new Handler());

        mStatus = (TextView) findViewById(R.id.status);
        mOnButton = (Button) findViewById(R.id.buttonOn);
        mOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCount++;
                String msg = String.format("Starting request #%d", requestCount);
                mStatus.setText(msg);
                startSpeechRec();
            }
        });
    }

    private void startSpeechRec() {
        checkPermissions(Manifest.permission.RECORD_AUDIO, PERMISSION_REQUEST_RECORD_AUDIO);

        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
        }
        mVoiceRecorder = new VoiceRecorder(mVoiceCallback);
        mVoiceRecorder.start();
    }

    private void stopSpeechRec() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
            mVoiceRecorder = null;
        }
    }

    /*
    void startSpeedRec() {
        checkPermissions(Manifest.permission.READ_PHONE_STATE, PERMISSION_REQUEST_READ_PHONE_STATE);
        checkPermissions(Manifest.permission.RECORD_AUDIO, PERMISSION_REQUEST_RECORD_AUDIO);

        Intent intent = new Intent(this, SpeechService.class);
        intent.putExtra("receiver", speechResultReceiver);
        intent.putExtra("sender", "stem main");
        startService(intent);

    }

    void startSpeedRec() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Log.w("Deebug", a);
        }
    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                   Log.w("Deebug", result.get(0));
                }
                break;
            }

        }
    }

    public class SpeechResultReceiver extends ResultReceiver {
        public SpeechResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            final String result = resultData.getString(SpeechService.MSG);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String msg = String.format("%d: %s", requestCount, result);
                    mStatus.setText(msg);
                }
            });
        }
    }

    private void checkPermissions(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("deebug", "requesting permission for " + permission);
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
        }
        Log.d("deebug", "has permission: " + permission);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_READ_PHONE_STATE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // all good
            } else {
                Toast.makeText(this,
                        "The app won't read phone state.", Toast.LENGTH_LONG).show();
            }
        }  else if (requestCode == PERMISSION_REQUEST_RECORD_AUDIO) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // all good
            } else {
                Toast.makeText(this,
                        "The app won't record audio.", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void showStatus(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStatus.setText(msg);
            }
        });
    }
}
