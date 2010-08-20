package com.amiral.kupluk;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Pengaturan extends PreferenceActivity {

	private static final String PIL_METODE_PERHITUNGAN = "pilihan_metode_perhitungan";
	private static final String PIL_METODE_PERHITUNGAN_DEF = "0";

	private static final String PIL_MAZHAB = "pilihan_mazhab";
	private static final String PIl_MAZHAB_DEF = "0";

	private static final String PIL_ZUHUR = "set_zuhur";
	private static final boolean PIL_ZUHUR_DEF = true;
	private static final String PIL_ASHAR = "set_ashar";
	private static final boolean PIL_ASHAR_DEF = true;
	private static final String PIL_MAGHRIB = "set_maghrib";
	private static final boolean PIL_MAGHRIB_DEF = true;
	private static final String PIL_ISYA = "set_isya";
	private static final boolean PIL_ISYA_DEF = true;
	private static final String PIL_SUBUH = "set_subuh";
	private static final boolean PIL_SUBUH_DEF = true;

	private static final String PIL_MODE_PENGINGAT = "pilihan_mode_pengingat";
	private static final String PIL_MODE_PENGINGAT_DEF = "0";

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		addPreferencesFromResource(R.xml.pengaturan);
	}

	// mengambil nilai default dari settingan waktu shalat
	public static boolean getSetZuhur(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PIL_ZUHUR, PIL_ZUHUR_DEF);
	}

	public static boolean getSetAshar(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PIL_ASHAR, PIL_ASHAR_DEF);
	}

	public static boolean getSetMaghrib(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PIL_MAGHRIB, PIL_MAGHRIB_DEF);
	}

	public static boolean getSetIsya(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PIL_ISYA, PIL_ISYA_DEF);
	}

	public static boolean getSetSubuh(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PIL_SUBUH, PIL_SUBUH_DEF);
	}

	public static String getMetodePerhitungan(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(PIL_METODE_PERHITUNGAN, PIL_METODE_PERHITUNGAN_DEF);
	}

	public static String getMazhab(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(PIL_MAZHAB, PIl_MAZHAB_DEF);
	}

	public static String getMode(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(PIL_MODE_PENGINGAT, PIL_MODE_PENGINGAT_DEF);
	}

}
