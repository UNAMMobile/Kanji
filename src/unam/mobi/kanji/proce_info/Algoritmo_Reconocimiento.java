package unam.mobi.kanji.proce_info;

import java.util.ArrayList;

import android.util.Log;

public class Algoritmo_Reconocimiento {

	private ArrayList<Puntos> puntos_fijos;
	private ArrayList<Puntos> puntos_a_evaluar;
	private ArrayList<Puntos> puntos_totales;
	private int indicardor_puntos;
	private int factor;
	private double tama_pun;

	public Algoritmo_Reconocimiento(int factor, int size) {
		puntos_fijos = new ArrayList<Puntos>();
		puntos_a_evaluar = new ArrayList<Puntos>();
		puntos_totales = new ArrayList<Puntos>();
		indicardor_puntos = 1;
		this.factor = factor;
		tama_pun = size;
		Log.d("tama—O", "" + tama_pun);
	}

	public void set_Puntos(ArrayList<Puntos> puntos_fijos,
			ArrayList<Puntos> puntos_a_evaluar) {
		this.puntos_fijos.addAll(puntos_fijos);
		this.puntos_a_evaluar.addAll(puntos_a_evaluar);
	}

	public double calcula_Porcentaje() {
		calcula_Puntos();
		return obtiene_Porcentanje();

	}

	private void calcula_Puntos() {

		int x_ini, x_sig, x_cal, x_fin, x_dif_ini, x_dif_fin, incr_x;
		int y_ini, y_sig, y_cal, y_fin, y_dif_ini, y_dif_fin, incr_y;

		// int cont_aux = 0;

		double angulo, factor_x, factor_y;

		x_ini = x_sig = puntos_a_evaluar.get(indicardor_puntos - 1).get_X();
		y_ini = y_sig = puntos_a_evaluar.get(indicardor_puntos - 1).get_Y();

		while (indicardor_puntos < puntos_a_evaluar.size()) {

			x_fin = puntos_a_evaluar.get(indicardor_puntos).get_X();
			y_fin = puntos_a_evaluar.get(indicardor_puntos).get_Y();

			angulo = obtiene_Angulo();

			// Log.i("punto inicial",
			// String.valueOf(x_ini) + "," + String.valueOf(y_ini));
			// Log.i("punto final",
			// String.valueOf(x_fin) + "," + String.valueOf(y_fin));
			Log.i("angulo", String.valueOf(Math.toDegrees(angulo)));

			// Falta evaluar para 0, 90, 180 y 270 grados
			if (angulo < (Math.PI / 2.0) || angulo > (3.0 * Math.PI / 2.0)) {

				x_dif_ini = factor - (x_ini % factor);

				if (x_dif_ini == factor && indicardor_puntos == 1) {
					x_dif_ini = 0;
				}

				x_dif_fin = factor - (x_fin % factor);

				// if (x_dif_fin == factor) {
				// x_dif_fin = 0;
				// }

				incr_x = factor;

				factor_y = Math.tan(angulo);

			} else if (angulo > (Math.PI / 2.0)
					&& angulo < (3.0 * Math.PI / 2.0)) {

				x_dif_ini = -x_ini % factor;

				if (x_dif_ini == 0 && indicardor_puntos > 1) {
					x_dif_ini = -factor;
				}

				x_dif_fin = -x_fin % factor;
				if (x_dif_fin == 0) {
					x_dif_fin = -factor;
				}

				incr_x = -factor;

				factor_y = Math.tan(angulo);

			} else {

				x_dif_ini = 0;
				x_dif_fin = 0;

				incr_x = 0;

				factor_y = 0.0;
			}

			if (angulo > 0.0 && angulo < Math.PI) {

				y_dif_ini = factor - (y_ini % factor);

				if (y_dif_ini == factor && indicardor_puntos == 1) {

					y_dif_ini = 0;
				}

				y_dif_fin = factor - (y_fin % factor);

				// if (y_dif_fin == factor) {
				// y_dif_fin = 0;
				// }

				incr_y = factor;

				factor_x = Math.cos(angulo) / Math.sin(angulo);

			} else if (angulo > Math.PI && angulo < 2.0 * Math.PI) {

				y_dif_ini = -y_ini % factor;

				if (y_dif_ini == 0 && indicardor_puntos > 1) {
					y_dif_ini = -factor;
				}

				y_dif_fin = -y_fin % factor;

				if (y_dif_fin == 0) {
					y_dif_fin = -factor;
				}

				incr_y = -factor;

				factor_x = Math.cos(angulo) / Math.sin(angulo);

			} else {

				y_dif_ini = 0;
				y_dif_fin = 0;

				incr_y = 0;

				factor_x = 0.0f;
			}

			x_sig += x_dif_ini;
			y_sig += y_dif_ini;

			x_fin += x_dif_fin;
			y_fin += y_dif_fin;

			// Log.d("punto siguiente",
			// String.valueOf(x_sig) + "," + String.valueOf(y_sig));
			// Log.d("punto final total",
			// String.valueOf(x_fin) + "," + String.valueOf(y_fin));

			while (x_sig != x_fin) {

				y_cal = (int) Math.round(x_dif_ini * factor_y) + y_ini;

				while (((y_sig < y_cal && incr_y > 0) || (y_sig > y_cal && incr_y < 0))) {

					x_cal = (int) Math.round(y_dif_ini * factor_x) + x_ini;

					puntos_totales.add(new Puntos(x_cal, y_sig));

					y_sig += incr_y;
					y_dif_ini += incr_y;

				}

				if (y_sig == y_cal) {
					y_sig += incr_y;
					y_dif_ini += incr_y;
				}

				puntos_totales.add(new Puntos(x_sig, y_cal));

				x_sig += incr_x;
				x_dif_ini += incr_x;
			}

			while (y_sig != y_fin) {

				x_cal = (int) Math.round(y_dif_ini * factor_x) + x_ini;

				puntos_totales.add(new Puntos(x_cal, y_sig));

				y_sig += incr_y;
				y_dif_ini += incr_y;
			}

			x_ini = x_sig = x_fin - x_dif_fin;
			y_ini = y_sig = y_fin - y_dif_fin;

			indicardor_puntos++;

			// for (int i = cont_aux; i < puntos_totales.size(); i++) {
			// Log.d("Punto Calculado", puntos_totales.get(i).get_X() + " , "
			// + puntos_totales.get(i).get_Y());
			//
			// }

			// cont_aux = puntos_totales.size();
		}
	}

	private double obtiene_Angulo() {
		double modulo, angulo;
		int x = puntos_a_evaluar.get(indicardor_puntos).get_X()
				- puntos_a_evaluar.get(indicardor_puntos - 1).get_X();
		int y = puntos_a_evaluar.get(indicardor_puntos).get_Y()
				- puntos_a_evaluar.get(indicardor_puntos - 1).get_Y();

		modulo = Math.sqrt(x * x + y * y);
		angulo = Math.acos(x / modulo);

		if (y < 0) {
			angulo = (2 * Math.PI) - angulo;
		}
		return angulo;
	}

	private double obtiene_Porcentanje() {
		double porcentje = 0.0;
		double dist_1;
		double dist_2;
		double dist_ant = 0;
		double dist_aux;
		double dist_fin;
		int ind_fij = 0;

		double permitido = 3.0 * factor / 4.0;
		double pendiente = 45.0 / factor;

		// String fijo;
		// String total;

		for (int i = 0; i < puntos_totales.size(); i++) {
			dist_1 = Distancia(ind_fij, i);

			if (ind_fij < puntos_fijos.size() - 1) {
				dist_2 = Distancia(ind_fij + 1, i);

				if (dist_1 < dist_2) {
					dist_fin = dist_1;
					if (dist_ant < dist_fin) {
						for (int j = 1; j < 6
								&& ind_fij + j < puntos_fijos.size() - 1; j++) {
							dist_aux = Distancia(ind_fij + j, i);
							if (dist_aux < dist_fin) {
								dist_fin = dist_aux;
								ind_fij += j;
							}
						}
					}
				} else {
					dist_fin = dist_2;
					ind_fij++;
					if (ind_fij < puntos_fijos.size() - 1) {
						dist_2 = Distancia(ind_fij + 1, i);
						if (dist_2 < dist_fin) {
							dist_fin = dist_2;
							ind_fij++;
						}
					}
				}
			} else {
				dist_fin = dist_1;
			}

			dist_ant = dist_fin;

			// Log.d("Puntos Fijo", puntos_fijos.get(ind_fij).get_X() + " , "
			// + puntos_fijos.get(ind_fij).get_Y());
			// Log.d("Puntos a Evaluar", puntos_totales.get(i).get_X() + " , "
			// + puntos_totales.get(i).get_Y());
			// Log.d("Distancia", "" + dist_fin);

			if (dist_fin > permitido) {

				if (dist_fin > 4 * factor) {
					porcentje = 100;
				}

				porcentje += (pendiente * dist_fin-permitido) / tama_pun;

				if (porcentje >= 100) {
					return 100;
				}

			} else {
				// Log.d("Porcen Indivi", "0.0");
			}

			// Log.d("Porcen Acum", "" + porcentje);

		}

		return porcentje;
	}

	private double Distancia(int ind_fij, int ind_tol) {

		int distancia;
		int x1 = puntos_fijos.get(ind_fij).get_X();
		int y1 = puntos_fijos.get(ind_fij).get_Y();
		int x2 = puntos_totales.get(ind_tol).get_X();
		int y2 = puntos_totales.get(ind_tol).get_Y();

		distancia = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);

		return Math.sqrt(distancia);
	}
}
