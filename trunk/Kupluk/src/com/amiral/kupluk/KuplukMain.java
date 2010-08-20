package com.amiral.kupluk;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent; //import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class KuplukMain extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Resources resources= getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// Membuat intent untuk memanggil tab lainnya
		// tab Jadwal shalat
		intent = new Intent().setClass(this, JadwalShalat.class);
		spec = tabHost.newTabSpec("JadwalShalat")
				.setIndicator(
						new Tampilan(this, R.drawable.bedug,
								R.string.jadwal_shalat_tab)).setContent(intent);
		tabHost.addTab(spec);

		// tab Arah Kiblat
		intent = new Intent().setClass(this, Kiblat.class);
		spec = tabHost.newTabSpec("ArahKiblat").setIndicator(
				new Tampilan(this, R.drawable.kabah, R.string.kiblat_tab))
				.setContent(intent);
		tabHost.addTab(spec);

		// tab Lokasi Masjid
		intent = new Intent().setClass(this, PetaMasjid.class);
		spec = tabHost.newTabSpec("LokasiMasjid").setIndicator(
				new Tampilan(this, R.drawable.home, R.string.peta_masjid_tab))
				.setContent(intent);
		tabHost.addTab(spec);

		// tab Pengaturan
		intent = new Intent().setClass(this, Pengaturan.class);
		spec = tabHost.newTabSpec("Pengaturan").setIndicator(
				new Tampilan(this, R.drawable.pengaturan,
						R.string.pengaturan_tab)).setContent(intent);
		tabHost.addTab(spec);
	}

	private class Tampilan extends LinearLayout {

		public Tampilan(Context context, int gambar, int label) {
			super(context);
			// TODO Auto-generated constructor stub

			ImageView iv = new ImageView(context);
			TextView tv = new TextView(context);

			iv.setImageResource(gambar);
			tv.setText(label);
			tv.setGravity(0x01); // posisi ditengah

			setOrientation(LinearLayout.VERTICAL);
			addView(iv);
			addView(tv);

		}

	}
	
	 //untuk mendeklarasikan Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater= getMenuInflater();
    	inflater.inflate(R.menu.about, menu);
    	
    	return true;
    }
    
    //Untuk mengeksekusi jika pilihan menu dipilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	super.onOptionsItemSelected(item);
    	switch (item.getItemId()) {
		case R.id.about:
			startActivity(new Intent(this, Tentang.class));
			return true;
			
		//klo mau lanjutin tambahin aja sendiri
		}
    	return false;
    }

}
