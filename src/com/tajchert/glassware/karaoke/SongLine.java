package com.tajchert.glassware.karaoke;
public class SongLine{
	/**
	 * @param line
	 * @param startTime
	 */
	public SongLine(Long startTime, String line) {
		super();
		this.line = line;
		this.startTime = startTime;
	}
	public String line = "";
	public Long startTime =  0l;
}