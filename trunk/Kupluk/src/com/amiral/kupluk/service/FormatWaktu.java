package com.amiral.kupluk.service;

public class FormatWaktu {
	private int jam;
	private int menit;
	private int detik;

	public FormatWaktu(float waktu) {
		setJam(waktu);
		setMenit(waktu);
		setDetik(waktu);
	}

	public int getJam() {
		return jam;
	}

	public void setJam(float waktu) {
		int jam = (int) waktu;
		this.jam = jam;
	}

	public int getMenit() {
		return menit;
	}

	public void setMenit(float waktu) {
		int menit = (int) ((waktu - getJam()) * 60);
		this.menit = menit;
	}

	public int getDetik() {
		return detik;
	}

	public void setDetik(float waktu) {
		int detik = (int) ((((waktu - getJam()) * 60) - getMenit()) * 60);
		this.detik = detik;
	}

}
