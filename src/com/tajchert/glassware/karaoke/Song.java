package com.tajchert.glassware.karaoke;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Song {
	
	public String title;
	public long length;
	public String author;
	public String album;
	public Bitmap cover;
	
	/**
	 * @param title
	 * @param length
	 * @param author
	 */
	public Song(String title, long length, String author) {
		super();
		this.title = title;
		this.length = length;
		this.author = author;
	}

	public ArrayList<SongLine> lyrics = new ArrayList<SongLine>();
	
	
}
