package com.amiral.kupluk;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.amiral.kupluk.service.FormatWaktu;
import com.amiral.kupluk.service.KuplukService;
import com.amiral.kupluk.service.PerhitunganShalat;
import com.amiral.kupluk.service.Variabel;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class JadwalShalat extends Activity {

	public static final String JADWAL = "jadwal";
	public static final String JAM_SUBUH = "jamSubuh";
	public static final String MENIT_SUBUH = "menitSubuh";

	public static int NOTIF_ID = 100;

	private int hari;
	private int bulan;
	private int tahun;
	private double zonaWaktu;
	public String metode = "";

	private float sudutSubuh;
	private float sudutIsya;
	private int mazhab;
	// private PerhitunganShalat shalat;

	LocationManager locationManager;
	KuplukService updateService = null;
	Location location;
	PerhitunganShalat shalat;
	FormatWaktu subuh;
	FormatWaktu zuhur;
	FormatWaktu ashar;
	FormatWaktu maghrib;
	FormatWaktu isya;

	SharedPreferences sharedPreferences;

	@Override
	public void onCreate(Bundle bundle) {

		Intent svc = new Intent(this, KuplukService.class);
		stopService(svc);

		super.onCreate(bundle);
		setContentView(R.layout.jadwal_shalat);

		NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		mgr.cancel(NOTIF_ID);

		// Mengambil text View di layout
		TextView waktuSubuh = (TextView) findViewById(R.id.label_subuh_jam);
		TextView waktuZuhur = (TextView) findViewById(R.id.label_zuhur_jam);
		TextView waktuAshar = (TextView) findViewById(R.id.label_ashar_jam);
		TextView waktuMaghrib = (TextView) findViewById(R.id.label_maghrib_jam);
		TextView waktuIsya = (TextView) findViewById(R.id.label_isya_jam);

		// inisialisasi tanggal sekarang
		final Calendar c = Calendar.getInstance();
		hari = c.get(Calendar.DAY_OF_MONTH);
		bulan = c.get(Calendar.MONTH) + 1;
		tahun = c.get(Calendar.YEAR);
		zonaWaktu = Variabel.getZonaWaktu();
		
		// inisialisasi Lokasi
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// kriteria penggunaan lokasi
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(true);

		String provider = locationManager.getBestProvider(criteria, true);
		location = locationManager.getLastKnownLocation(provider);
		
//		double lat= location.getLatitude();
//		double lon= location.getLongitude();
//		double alt= location.getAltitude();
		
		double lat=-6.166666667;
		double lon=106.85;
		double alt=50;
		/**
		 * Untuk menampilkan lokasi user berada pada judul jadwal shalat
		 * 
		 */
		
		String namaKota;
		String alamat = null;
		TextView tv ;
		tv = (TextView) findViewById(R.id.label_judul);
		
		if (location!= null){
			Geocoder gc = new Geocoder(this, Locale.getDefault());
			
			try{
				List<Address> addresses= gc.getFromLocation(lat, lon, 1);
				StringBuilder sb= new StringBuilder();
				if(addresses.size()>0){
					Address address= addresses.get(0);
					
					sb.append(address.getLocality());
				}
				alamat = sb.toString();
				tv.setText(alamat +", "+hari + "/"+bulan +"/"+ tahun);
			} catch (IOException e) {}
		}else{
			tv.setText(hari + "/"+bulan +"/"+ tahun);
		}
		
		
		
		/**
		 * Untuk mengambil nilai metode yang telah ditentukan oleh user
		 * 
		 * @params metode merupakan tempat menyimpan sementara
		 */
		metode = Pengaturan.getMetodePerhitungan(this);
		char a = metode.charAt(0);
		switch (a) {
		case '0': {
			sudutSubuh = 20;
			sudutIsya = 18;
		}
			break;

		case '1': {
			sudutSubuh = 18;
			sudutIsya = 18;
		}
			break;

		case '2': {
			sudutSubuh = 15;
			sudutIsya = 15;
		}
			break;

		case '3': {
			sudutSubuh = 17;
			sudutIsya = 18;
		}
			break;

		case '4': {
			sudutSubuh = 18;
			sudutIsya = (float) 18.5;
		}
			break;

		case '5': {
			sudutSubuh = (float) 17.5;
			sudutIsya = (float) 19.5;
		}
			break;
		}

		/**
		 * Untuk mengambil nilai mazhab yang sudah ditentukan oleh user
		 */
		String mzb = Pengaturan.getMazhab(this);
		char m = mzb.charAt(0);
		mazhab = m == '0' ? 1 : 2;

		// Menghitung variabel
		 shalat= new PerhitunganShalat(lat,lon, alt, zonaWaktu, hari,
		 bulan, tahun);

		shalat.setSudutSubuh(sudutSubuh);
		shalat.setSudutIsya(sudutIsya);
		shalat.setMazhab(mazhab);

		// Untuk mengkonfersi hasil perhitungan dalam format jam.
		FormatWaktu subuh = new FormatWaktu(shalat.getWaktuSubuh());
		FormatWaktu zuhur = new FormatWaktu(shalat.getWaktuZuhur());
		FormatWaktu ashar = new FormatWaktu(shalat.getWaktuAshar());
		FormatWaktu maghrib = new FormatWaktu(shalat.getWaktuMaghrib());
		FormatWaktu isya = new FormatWaktu(shalat.getWaktuIsya());

		waktuIsya.setText("" + isya.getJam() + ":" + isya.getMenit() + ":"
				+ isya.getDetik());
		waktuMaghrib.setText("" + maghrib.getJam() + ":" + maghrib.getMenit()
				+ ":" + maghrib.getDetik());
		waktuSubuh.setText("" + subuh.getJam() + ":" + subuh.getMenit() + ":"
				+ subuh.getDetik());
		waktuZuhur.setText("" + zuhur.getJam() + ":" + zuhur.getMenit() + ":"
				+ zuhur.getDetik());
		waktuAshar.setText("" + ashar.getJam() + ":" + ashar.getMenit() + ":"
				+ ashar.getDetik());

		// untuk menyimpan waktu shalat agar dapat dipanggil di KuplukService

		SharedPreferences jadwal = getSharedPreferences(JADWAL, 0);
		SharedPreferences.Editor editor = jadwal.edit();
		editor.clear();

		editor.putInt("jSubuh", subuh.getJam());
		editor.putInt("mSubuh", subuh.getMenit());
		editor.putInt("jZuhur", zuhur.getJam());
		editor.putInt("mZuhur", zuhur.getMenit());
		editor.putInt("jAshar", ashar.getJam());
		editor.putInt("mAshar", ashar.getMenit());
		editor.putInt("jMaghrib", maghrib.getJam());
		editor.putInt("mMaghrib", maghrib.getMenit());
		editor.putInt("jIsya", isya.getJam());
		editor.putInt("mIsya", isya.getMenit());
		editor.commit();

		// cek keluaran
		Log.d("kupluk", "sudut subuh: " + shalat.getSudutSubuh());
		Log.d("kupluk", "sudut isya: " + shalat.getSudutIsya());
		Log.d("kupluk", "metode: " + metode);
		Log.d("kupluk", "mazhab: " + mazhab);
		Log.d("kupluk", "zonawaktu: " + zonaWaktu);
		Log.d("kupluk", "Jd LOkasi:  " + shalat.getJdLokasi());
		Log.d("kupluk", "Delta: " + shalat.getDelta());
		Log.d("kupluk", "ET: " + shalat.getET());
		Log.d("k", "subuh= " + shalat.getWaktuSubuh());
		Log.d("kupluk", "zuhur= " + shalat.getWaktuZuhur());
		Log.d("kupluk", "Ashar= " + shalat.getWaktuAshar());
		Log.d("kupluk", "maghrib= " + shalat.getWaktuMaghrib());
		Log.d("k", "isya= " + shalat.getWaktuIsya());

		// Inisiasi service

		startService(svc);
	}

	@Override
	public void onResume() {
		super.onResume();
		metode = Pengaturan.getMetodePerhitungan(this);
		NotificationManager mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNM.cancel(KuplukService.NOTIF_ID);
	}

	@Override
	public void onStop() {
		super.onStop();
		Intent svc = new Intent(this, KuplukService.class);
		startService(svc);
	}
}
