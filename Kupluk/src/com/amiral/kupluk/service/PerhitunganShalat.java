package com.amiral.kupluk.service;

import android.util.Log;

public class PerhitunganShalat {

	private float waktuZuhur;
	private float waktuAshar;
	private float waktuMaghrib;
	private float waktuIsya;
	private float waktuSubuh;

	private double latitudeLokasi;
	private double longitudLokasi;
	private double altitudeLokasi;
	private double zonaWaktuLokasi;

	private double jdLokasi;
	private double sudutSubuh;
	private double sudutIsya;
	private double delta;
	private double ET;
	private int mazhab;

	// Konstruktor
	public PerhitunganShalat(double latitudeLokasi, double longitudeLokasi,
			double altitudeLokasi, double zonaWaktu, int tanggal, int bulan,
			int tahun) {
		// TODO Auto-generated constructor stub
		setLatitudeLokasi(latitudeLokasi);
		setLongitudLokasi(longitudeLokasi);
		setAltitudeLokasi(altitudeLokasi);
		setZonaWaktuLokasi(zonaWaktu);
		setJdLokasi(tanggal, bulan, tahun);
	}

	public float getWaktuZuhur() {
		setET(getJdLokasi());
		return waktuZuhur = (float) (12 + getZonaWaktuLokasi()
				- getLongitudLokasi() / 15 - getET() / 60);
	}

	public float getWaktuAshar() {
		setDelta(getJdLokasi());
		Log.d("mazhab", "mazhab= " + getMazhab());
		return waktuAshar = (float) (getWaktuZuhur() + (12 / Math.PI)
				* Math
						.acos((Math
								.sin(Math
										.atan(1 / (getMazhab() + Math.tan(Math
												.abs(getLatitudeLokasi()
														- getDelta()))))) - Math
								.sin(getDelta())
								* Math.sin(getLatitudeLokasi()))
								/ (Math.cos(getDelta()) * Math
										.cos(getLatitudeLokasi()))));
	}

	public float getWaktuMaghrib() {
		setDelta(getJdLokasi());
		return waktuMaghrib = (float) (getWaktuZuhur() + (12 / Math.PI)
				* Math
						.acos((Math.sin((-0.8333 - 0.0347 * Math.pow(
								getAltitudeLokasi(), 0.5))
								* Math.PI / 180) - Math.sin(getDelta())
								* Math.sin(getLatitudeLokasi()))
								/ (Math.cos(getDelta()) * Math
										.cos(getLatitudeLokasi()))));
	}

	public float getWaktuIsya() {
		setDelta(getJdLokasi());
		return waktuIsya = (float) (getWaktuZuhur() + (12 / Math.PI)
				* Math
						.acos((Math.sin((-1 * getSudutIsya()) * Math.PI / 180) - Math
								.sin(getDelta())
								* Math.sin(getLatitudeLokasi()))
								/ (Math.cos(getDelta()) * Math
										.cos(getLatitudeLokasi()))));
	}

	public float getWaktuSubuh() {
		setDelta(getJdLokasi());
		return waktuSubuh = (float) (getWaktuZuhur() - (12 / Math.PI)
				* Math
						.acos((Math.sin((-1 * getSudutSubuh()) * Math.PI / 180) - Math
								.sin(getDelta())
								* Math.sin(getLatitudeLokasi()))
								/ (Math.cos(getDelta()) * Math
										.cos(getLatitudeLokasi()))));
	}

	public double getLatitudeLokasi() {
		return latitudeLokasi;
	}

	public void setLatitudeLokasi(double latitudeLokasi) {
		this.latitudeLokasi = latitudeLokasi * Math.PI / 180;
	}

	public double getLongitudLokasi() {
		return longitudLokasi;
	}

	public void setLongitudLokasi(double longitudLokasi) {
		this.longitudLokasi = longitudLokasi;
	}

	public double getAltitudeLokasi() {
		return altitudeLokasi;
	}

	public void setAltitudeLokasi(double altitudeLokasi) {
		this.altitudeLokasi = altitudeLokasi;
	}

	public double getZonaWaktuLokasi() {
		return zonaWaktuLokasi;
	}

	public void setZonaWaktuLokasi(double zonaWaktuLokasi) {
		this.zonaWaktuLokasi = zonaWaktuLokasi;
	}

	public double getSudutSubuh() {
		return sudutSubuh;
	}

	public void setSudutSubuh(double sudutSubuh) {
		this.sudutSubuh = sudutSubuh;
	}

	public double getSudutIsya() {
		return sudutIsya;
	}

	public void setSudutIsya(double sudutIsya) {
		this.sudutIsya = sudutIsya;
	}

	public double getJdLokasi() {
		return jdLokasi;
	}

	public void setJdLokasi(int tanggal, int bulan, int tahun) {
		int M = bulan < 3 ? bulan + 12 : bulan;
		int Y = bulan < 3 ? tahun - 1 : tahun;
		int A = Y / 100;
		int B = 2 + (int) (A / 4) - A;
		double JDgreenWicht = 1720994.5 + (int) (365.25 * Y)
				+ (int) (30.60001 * (M + 1)) + B + tanggal + 0.5;
		Log.d("shalat", "JDG= " + JDgreenWicht + "zona= "
				+ getZonaWaktuLokasi());
		double zona = getZonaWaktuLokasi();
		double JDlokasi = JDgreenWicht - zona / 24;
		this.jdLokasi = JDlokasi;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double jdLks) {
		double sudutTanggal = 2 * Math.PI * (jdLks - 2451545) / 365.25;
		double dlt = 0.37877
				+ 23.264
				* Math.sin((57.297 * sudutTanggal - 79.547) * Math.PI / 180)
				+ 0.3812
				* Math
						.sin((2 * 57.297 * sudutTanggal - 82.682) * Math.PI
								/ 180)
				+ 0.17132
				* Math
						.sin((3 * 57.297 * sudutTanggal - 59.722) * Math.PI
								/ 180);
		this.delta = dlt * Math.PI / 180;
	}

	public double getET() {
		return ET;
	}

	public void setET(double jdLks) {
		double U = (jdLks - 2451545) / 36525;
		double L0 = (280.46607 + 36000.7698 * U) * Math.PI / 180;
		double eT = (-1 * (1789 + 237 * U) * Math.sin(L0) - (7146 - 62 * U)
				* Math.cos(L0) + (9934 - 14 * U) * Math.sin(2 * L0)
				- (29 + 5 * U) * Math.cos(2 * L0) + (74 + 10 * U)
				* Math.sin(3 * L0) + (320 - 4 * U) * Math.cos(3 * L0) - 212 * Math
				.sin(4 * L0)) / 1000;
		ET = eT;
	}

	public int getMazhab() {
		return mazhab;
	}

	public void setMazhab(int mazhab) {
		this.mazhab = mazhab;
	}

}
