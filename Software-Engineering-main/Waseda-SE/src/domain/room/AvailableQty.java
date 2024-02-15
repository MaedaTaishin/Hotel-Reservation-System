/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.room;

import java.util.Date;
import java.util.HashMap;

/**
 * Class representing number of available rooms on certain date<br>
 * 
 */
public class AvailableQty {

	/**
	 * Literal of maximum available number
	 */
	public static final int AVAILABLE_ALL = -1;

	private Date date;

	/**
	 * number of available rooms
	 */
	private static HashMap<String, Integer> qty = new HashMap<>();
	static {
		qty.put("SingleRoom", -1);
		qty.put("TwinRoom", -1);
		qty.put("TripleRoom", -1);
		qty.put("KingRoom", -1);
		qty.put("PresidentialSuite", -1);
		qty.put("JapaneseRoom", -1);
		qty.put("DisabledRoom", -1);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getQty(String roomType) {
		return qty.get(roomType);
	}

	public void setQty(String roomType, int roomQty) {
		qty.put(roomType, roomQty);
	}

}
