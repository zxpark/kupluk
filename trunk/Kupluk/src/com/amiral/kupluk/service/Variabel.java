package com.amiral.kupluk.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Variabel {
	public static float arahKiblat = 0;

	private Variabel() {

	}

	public static double getZonaWaktu() {
		Calendar now = new GregorianCalendar();
		int gmtOffset = now.getTimeZone().getOffset(now.getTimeInMillis());
		return gmtOffset / 3600000;
	}
}
