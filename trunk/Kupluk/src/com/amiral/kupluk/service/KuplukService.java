package com.amiral.kupluk.service;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.amiral.kupluk.JadwalShalat;
import com.amiral.kupluk.KuplukMain;
import com.amiral.kupluk.Pengaturan;
import com.amiral.kupluk.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

/**
 * UpdateService
 */

public class KuplukService extends Service {
	private Timer timer = new Timer();
	private NotificationManager mNM;
	public static int NOTIF_ID = 100;

	public static final String JADWAL = "jadwal";

	public int jSubuh;
	public int mSubuh;
	public int jZuhur;
	public int mZuhur;
	public int jAshar;
	public int mAshar;
	public int jMaghrib;
	public int mMaghrib;
	public int jIsya;
	public int mIsya;

	public String mode = "";

	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		SharedPreferences sharedPreferences = getSharedPreferences(JADWAL, 0);

		jSubuh = sharedPreferences.getInt("jSubuh", 4);
		mSubuh = sharedPreferences.getInt("mSubuh", 10);
		jZuhur = sharedPreferences.getInt("jZuhur", 1);
		mZuhur = sharedPreferences.getInt("mZuhur", 1);
		jAshar = sharedPreferences.getInt("jAshar", 1);
		mAshar = sharedPreferences.getInt("mAshar", 1);
		jMaghrib = sharedPreferences.getInt("jMaghrib", 1);
		mMaghrib = sharedPreferences.getInt("mMaghrib", 1);
		jIsya = sharedPreferences.getInt("jIsya", 1);
		mIsya = sharedPreferences.getInt("mIsya", 1);

		// jAshar=0;
		// mAshar=40;
		// jMaghrib=0;
		// mMaghrib=38;
		// jIsya=0;
		// mIsya=36;
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// init the service here
		_startService();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		_shutdownService();
	}

	private void _startService() {
		long opIntervalUpdate = 60000;
		Log
				.i("Kupluk", "UpdateService, opIntervalUpdate = "
						+ opIntervalUpdate);
		if (opIntervalUpdate != 0) {

			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					checkAlarm();
				}
			}, 0, opIntervalUpdate);
			Log.i(getClass().getSimpleName(), "Timer started!!!");
		}
	}

	private void checkAlarm() {
		Log.i("Kupluk", "Starting update service...");
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		Log.d("Kupluk", "waktu skrg = " + hour + " : " + minute);
		// if (hour == 22 && minute == 30) { // disini harusnya pengecekan
		// apakah waktu sekarang, sama dengan salah satu waktu shalat atau tidak
		// Log.d("Kupluk", "Ada alarm");
		if (hour == jSubuh && minute == mSubuh) {
			if (Pengaturan.getSetSubuh(this))
				showNotification(R.drawable.icon,
						"Waktu Shalat Subuh telah tiba");
		}
		if (hour == jZuhur && minute == mZuhur) {
			if (Pengaturan.getSetZuhur(this))
				showNotification(R.drawable.icon,
						"Waktu Shalat Zhuhur telah tiba");
		} else if (hour == jAshar && minute == mAshar) {
			if (Pengaturan.getSetAshar(this))
				showNotification(R.drawable.icon,
						"Waktu Shalat Ashar telah tiba");
		} else if (hour == jMaghrib && minute == mMaghrib) {
			if (Pengaturan.getSetMaghrib(this))
				showNotification(R.drawable.icon,
						"Waktu Shalat Maghrib telah tiba");
		} else if (hour == jIsya && minute == mIsya) {
			if (Pengaturan.getSetIsya(this))
				showNotification(R.drawable.icon,
						"Waktu Shalat Isya telah tiba");
		} else {
			Log.d("Kupluk", "pass, tidak ada alarm");
			Log.d("kupluk", "Subuh: " + jSubuh + " " + mSubuh);
			Log.d("Kupluk", "Zuhur: " + jZuhur + " " + mZuhur);
			Log.d("Kupluk", "Ashar: " + jAshar + " " + mAshar);
			Log.d("Kupluk", "Maghrib: " + jMaghrib + " " + mMaghrib);
			Log.d("Kupluk", "Isya: " + jIsya + " " + mIsya);

			Log.d("Kupluk", "subuh: " + Pengaturan.getSetSubuh(this));
			Log.d("Kupluk", "Zuhur: " + Pengaturan.getSetZuhur(this));
			Log.d("Kupluk", "Ashar: " + Pengaturan.getSetAshar(this));
			Log.d("Kupluk", "Maghrib: " + Pengaturan.getSetMaghrib(this));
			Log.d("Kupluk", "Isya: " + Pengaturan.getSetIsya(this));
		}

		Log.i("Kupluk", "End of update service");
	}

	private void showNotification(int moodId, String text) {
		Log.d("Kupluk", "start showNotification()");
		Notification notification = new Notification(moodId, null, System
				.currentTimeMillis());
		// boolean useSound = true;
		// boolean useVibrate = false;
		// boolean useLed = false;

		mode = Pengaturan.getMode(this);
		char b = mode.charAt(0);
		Log.d("kupluk", "mode: " + b);
		switch (b) {
		case '0': {
			// notification.sound=
			// Uri.parse("file:///sdcard/notification/adzan.mp3");

			notification.sound = Uri
					.parse("android.resource://com.amiral.kupluk/"
							+ R.raw.adzan);
			Log.d("Kupluk", "Suara diterima");
		}
			break;
		case '1': {
			notification.vibrate = new long[] { 1000, 1000, 1000, 1000, 1000 };
			// notification.defaults |= Notification.DEFAULT_VIBRATE;
			Log.d("Kupluk", "Getar diterima");
		}
			;
			break;
		case '2': {
			// notification.ledARGB = 0xff00ff00;
			notification.ledARGB = Color.BLUE;
			notification.ledOnMS = 1500;
			notification.ledOffMS = 800;
			notification.flags |= Notification.FLAG_SHOW_LIGHTS;
			Log.d("Kupluk", "Lampu Diterima");
		}
			break;
		}

		/*
		 * if (useSound) {
		 * 
		 * } if (useVibrate) { notification.vibrate = new long[] {50, 250, 200,
		 * 1200}; Log.d("Kupluk", "trigger vibrate notification"); } if (useLed)
		 * { notification.ledARGB = 0xff00ff00; notification.ledOnMS = 1500;
		 * notification.ledOffMS = 800; notification.flags |=
		 * Notification.FLAG_SHOW_LIGHTS ; Log.d("Kupluk",
		 * "trigger led notification"); }
		 */

		// The PendingIntent to launch our activity if the user selects this
		// notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, KuplukMain.class), 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this, "Kupluk - Remainder", text,
				contentIntent);

		// Send the notification.
		mNM.notify(NOTIF_ID, notification);
		Log.d("Kupluk", "end showNotification()");
	}

	private void _shutdownService() {
		if (timer != null)
			timer.cancel();
		Log.i(getClass().getSimpleName(), "Timer stopped!!!");
	}

}