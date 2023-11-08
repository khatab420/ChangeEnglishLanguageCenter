package com.khataab.changeenglishlanguagecenter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.google.android.material.snackbar.Snackbar;
import java.io.File;
import java.io.IOException;

public class CustomSnackbar {
    private MediaPlayer mediaPlayer;
    private Snackbar customSnackbar;
    private boolean isSnackbarVisible = false;
    private Activity activity;
    private boolean isPlaying = false;
    SeekBar seekBar;
    ImageButton button_left,button_right,button_play_pause;

    public CustomSnackbar(Activity activity) {
        this.activity = activity;
        this.mediaPlayer = null; // Initialize the MediaPlayer as null
    }

    public Snackbar showCustomSnackbar(File file, String message) {
        if (mediaPlayer != null) {
            releaseMediaPlayer(); // Release any existing MediaPlayer
        }
        mediaPlayer = new MediaPlayer(); // Create a new MediaPlayer instance

        initMediaPlayer(file);

        LayoutInflater inflater = LayoutInflater.from(activity);
        View snackbarView = inflater.inflate(R.layout.custom_snackbar_layout, null);

        customSnackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                "",
                Snackbar.LENGTH_INDEFINITE
        );
        seekBar=snackbarView.findViewById(R.id.custom_snackbar_seekbar);
        button_left=snackbarView.findViewById(R.id.custom_snackbar_back);
        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    int newPosition = currentPosition - 3000; // Rewind by 10 seconds (adjust as needed)
                    if (newPosition < 0) {
                        newPosition = 0; // Ensure the new position is not negative
                    }
                    mediaPlayer.seekTo(newPosition);
                }
            }
        });

        button_right=snackbarView.findViewById(R.id.custom_snackbar_farward);
        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    int newPosition = currentPosition + 3000; // Fast-forward by 10 seconds (adjust as needed)
                    mediaPlayer.seekTo(newPosition);
                }
            }
        });

        button_play_pause=snackbarView.findViewById(R.id.custom_snackbar_play_pause);
        button_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    button_play_pause.setImageResource(R.drawable.play_arrow);
                    stopMediaPlayer();
                }else {
                    button_play_pause.setImageResource(R.drawable.pause);
                    playMediaPlayer();

                }
            }
        });
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for your use case
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for your use case
            }
        });

        // Start a periodic task to update SeekBar progress
        updateSeekBarProgress();

        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) customSnackbar.getView();
        layout.setPadding(0, 0, 0, 0);
        layout.addView(snackbarView, 0);

        customSnackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (isPlaying) {
                    stopMediaPlayer();
                    isSnackbarVisible = false;
                }
            }
        });

        customSnackbar.show();
        playMediaPlayer();
        isSnackbarVisible = true;

        return customSnackbar;
    }

    private void updateSeekBarProgress() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isPlaying) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                }
                handler.postDelayed(this, 1000); // Update SeekBar every 1 second
            }
        };
        handler.post(runnable);
    }

    private void initMediaPlayer(File file) {
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playMediaPlayer() {
        if (!isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    private void stopMediaPlayer() {
        if (isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public void close() {
        if (isSnackbarVisible) {
            customSnackbar.dismiss();
            isSnackbarVisible = false;
            // Do not release the MediaPlayer here
        }
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            if (isPlaying) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            // Do set the mediaPlayer to null here
            mediaPlayer = null;
        }
    }

    public boolean isSnackbarVisible() {
        return isSnackbarVisible;
    }
}
