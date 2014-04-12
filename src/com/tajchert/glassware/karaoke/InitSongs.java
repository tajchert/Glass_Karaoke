package com.tajchert.glassware.karaoke;

public class InitSongs {
	
	public static Song initRick(){
		//3:30 min. = 180 000 + 30 000
		Song rick = new Song("Never gonna give you up", 210000, "Rick Astley");
		rick.lyrics.add(new SongLine((long) 0, ""));
		rick.lyrics.add(new SongLine((long) 250, "Never gonna give you up\nRick Astley"));
		rick.lyrics.add(new SongLine((long) 18000, "We're no strangers to love"));
		rick.lyrics.add(new SongLine((long) 22000, "You know the rules and so do I"));
		rick.lyrics.add(new SongLine((long) 27000, "A full commitment's what I'm thinking of"));
		rick.lyrics.add(new SongLine((long) 31000, "You wouldn't get this from any other guy"));
		rick.lyrics.add(new SongLine((long) 35500, "I just wanna tell you how I'm feeling"));
		rick.lyrics.add(new SongLine((long) 40500, "Gotta make you understand"));
		rick.lyrics.add(new SongLine((long) 43000, "Never gonna give you up"));
		rick.lyrics.add(new SongLine((long) 45000, "Never gonna let you down"));
		rick.lyrics.add(new SongLine((long) 47000, "Never gonna run around and desert you"));
		rick.lyrics.add(new SongLine((long) 52000, "Never gonna make you cry"));
		rick.lyrics.add(new SongLine((long) 53500, "Never gonna say goodbye"));
		rick.lyrics.add(new SongLine((long) 56000, "Never gonna tell a lie and hurt you"));
		return rick;
	}

}
