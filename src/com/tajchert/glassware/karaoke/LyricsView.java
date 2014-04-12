/*
centi_second * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tajchert.glassware.karaoke;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * View used to display draw a running Chronometer.
 *
 * This code is greatly inspired by the Android's Chronometer widget.
 */
public class LyricsView extends FrameLayout {

    /**
     * Interface to listen for changes on the view layout.
     */
    public interface ChangeListener {
        /** Notified of a change in the view. */
        public void onChange();
    }

    // About 24 FPS.
    private static final long DELAY_MILLIS = 200;//Default 41

    private final TextView mLyricsView;

    private boolean mStarted;
    private boolean mForceStart;
    private boolean mVisible;
    private boolean mRunning;
    private Song song;
    private int lastPosition;
    private long nextPositionTime;

    private long mBaseMillis;

    private ChangeListener mChangeListener;

    public LyricsView(Context context) {
        this(context, null, 0);
    }

    public LyricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LyricsView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        LayoutInflater.from(context).inflate(R.layout.card_lyrics, this);

        mLyricsView = (TextView) findViewById(R.id.lyrics_content);

        setBaseMillis(SystemClock.elapsedRealtime());
    }

    public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		if(song != null){
			lastPosition = 0;
			this.song = song;
		}else{
			throw new IllegalArgumentException("Song is null");
		}
		
	}

	/**
     * Set the base value of the chronometer in milliseconds.
     */
    public void setBaseMillis(long baseMillis) {
        mBaseMillis = baseMillis;
        lastPosition = 0;
        updateText();
    }

    /**
     * Get the base value of the chronometer in milliseconds.
     */
    public long getBaseMillis() {
        return mBaseMillis;
    }

    /**
     * Set a {@link ChangeListener}.
     */
    public void setListener(ChangeListener listener) {
        mChangeListener = listener;
    }

    /**
     * Set whether or not to force the start of the chronometer when a window has not been attached
     * to the view.
     */
    public void setForceStart(boolean forceStart) {
        mForceStart = forceStart;
        updateRunning();
    }

    /**
     * Start the chronometer.
     */
    public void start() {
        mStarted = true;
        updateRunning();
    }

    /**
     * Stop the chronometer.
     */
    public void stop() {
        mStarted = false;
        updateRunning();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVisible = false;
        updateRunning();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = (visibility == VISIBLE);
        updateRunning();
    }


    private final Handler mHandler = new Handler();

    private final Runnable mUpdateTextRunnable = new Runnable() {
        @Override
        public void run() {
            if (mRunning) {
                updateText();
                mHandler.postDelayed(mUpdateTextRunnable, DELAY_MILLIS);
            }
        }
    };

    /**
     * Update the running state of the chronometer.
     */
    private void updateRunning() {
        boolean running = (mVisible || mForceStart) && mStarted;
        if (running != mRunning) {
            if (running) {
                mHandler.post(mUpdateTextRunnable);
            } else {
                mHandler.removeCallbacks(mUpdateTextRunnable);
            }
            mRunning = running;
        }
    }

    /**
     * Update the value of the chronometer.
     */
    private void updateText() {
        long millis = SystemClock.elapsedRealtime() - mBaseMillis;
        
        // Cap chronometer to one hour.
        if(song != null){
	        if(nextPositionTime <= millis && lastPosition < (song.lyrics.size()-2)){
	        	//Log.d("GLASSKARAOKE", "1@" + lastPosition +" / "+song.lyrics.size());
	        	//Log.d("GLASSKARAOKE", "nextPositionTime:" + nextPositionTime +" @ "+millis);
	        	lastPosition++;
	        	mLyricsView.setText(song.lyrics.get(lastPosition).line+"");
	        	//Log.d("GLASSKARAOKE", "@" + millis +" / "+nextPositionTime+", " +song.lyrics.get(lastPosition).line);
	        	nextPositionTime = song.lyrics.get(lastPosition+1).startTime;
	        }
	        if (mChangeListener != null) {
	            mChangeListener.onChange();
	        }
	        if(lastPosition == (song.lyrics.size()-2)){
	        	this.stop();
	        }
        }
        
        //millis %= TimeUnit.HOURS.toMillis(1);

        //mLyricsView.setText(String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millis)));
        //millis %= TimeUnit.MINUTES.toMillis(1);
        /*mSecondView.setText(String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millis)));
        millis = (millis % TimeUnit.SECONDS.toMillis(1)) / 10;
        mCentiSecondView.setText(String.format("%02d", millis));*/
        
    }
}
