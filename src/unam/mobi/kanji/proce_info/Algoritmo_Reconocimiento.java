package unam.mobi.kanji.proce_info;

import java.util.ArrayList;

public class Algoritmo_Reconocimiento {

	private ArrayList<Puntos> puntos_fijos;
	private ArrayList<Puntos> puntos_a_evaluar;
	private ArrayList<Puntos> puntos_totales;
	private int indicardor_puntos;
	private int factor;

//	private int sobrante;

	public Algoritmo_Reconocimiento(int factor) {

		puntos_fijos = new ArrayList<Puntos>();
		puntos_a_evaluar = new ArrayList<Puntos>();
		puntos_totales = new ArrayList<Puntos>();
		indicardor_puntos = 1;
		this.factor = factor;

	}

//	public void setSobrante(int sobrante) {
//		this.sobrante = sobrante;
//	}

	public void set_Puntos(ArrayList<Puntos> puntos_fijos,
			ArrayList<Puntos> puntos_a_evaluar) {
		this.puntos_fijos.addAll(puntos_fijos);
		this.puntos_a_evaluar.addAll(puntos_a_evaluar);
	}

	public double calcula_Porcentaje() {
		obtiene_Puntos();
//		Log.d("totales", String.valueOf(puntos_totales.size()));
//		String puntos_tot = "[";
//		DecimalFormat df = new DecimalFormat("0.00");
//
//		for (int i = 0; i < puntos_totales.size(); i++) {
//			puntos_tot += df.format(1.0 * puntos_totales.get(i).get_X()
//					/ factor)
//					+ ","
//					+ df.format((1.0 * puntos_totales.get(i).get_Y() - sobrante)
//							/ factor) + " ; ";
//		}
//		puntos_tot += "]";
//		Log.d("puntos calculados", puntos_tot);

		return obtiene_Porcentanje();

	}

	private void obtiene_Puntos() {

		int x_ini, x_sig, x_cal, x_fin, x_dif_ini, x_dif_fin, incr_x;
		int y_ini, y_sig, y_cal, y_fin, y_dif_ini, y_dif_fin, incr_y;

		double angulo, factor_x, factor_y;

		x_ini = x_sig = puntos_a_evaluar.get(indicardor_puntos - 1).get_X();
		y_ini = y_sig = puntos_a_evaluar.get(indicardor_puntos - 1).get_Y();

		puntos_totales.clear();

		while (indicardor_puntos < puntos_a_evaluar.size() - 1) {

			// Log.d("Se queda", "while 1");
			x_fin = puntos_a_evaluar.get(indicardor_puntos).get_X();
			y_fin = puntos_a_evaluar.get(indicardor_puntos).get_Y();

			angulo = obtiene_Angulo();

			// Falta evaluar para 0, 90, 180 y 270 grados
			if (angulo < (Math.PI / 2.0) || angulo > (3.0 * Math.PI / 2.0)) {

				x_dif_ini = factor - (x_ini % factor);
				if (x_dif_ini == factor) {
					x_dif_ini = 0;

					if (indicardor_puntos > 0) {
						x_sig += factor;
					}
				}

				x_dif_fin = factor - (x_fin % factor);

				incr_x = factor;

				factor_y = Math.tan(angulo);

			} else if (angulo > (Math.PI / 2.0)
					&& angulo < (3.0 * Math.PI / 2.0)) {

				x_dif_ini = -x_ini % factor;

				if (x_dif_ini == 0 && indicardor_puntos > 0) {
					x_sig -= factor;
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
				if (y_dif_ini == factor) {
					y_dif_ini = 0;
					if (indicardor_puntos > 0) {
						y_sig += factor;
					}
				}

				y_dif_fin = factor - (y_fin % factor);

				incr_y = factor;

				factor_x = Math.cos(angulo) / Math.sin(angulo);

			} else if (angulo > Math.PI && angulo < 2.0 * Math.PI) {

				y_dif_ini = -y_ini % factor;

				if (y_dif_ini == 0 && indicardor_puntos > 0) {
					y_sig -= factor;
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

			// Log.d("punto inicial",
			// String.valueOf(x_ini) + "," + String.valueOf(y_ini));
			// Log.d("punto final",
			// String.valueOf(x_fin) + "," + String.valueOf(y_fin));
			// Log.d("angulo", String.valueOf(Math.toDegrees(angulo)));

			x_sig += x_dif_ini;
			y_sig += y_dif_ini;

			x_fin += x_dif_fin;
			y_fin += y_dif_fin;

			// Log.d("punto siguiente",
			// String.valueOf(x_sig) + "," + String.valueOf(y_sig));
			// Log.d("punto final total",
			// String.valueOf(x_fin) + "," + String.valueOf(y_fin));

			while (x_sig != x_fin) {
				// Log.d("se queda", "while 2");

				y_cal = (int) (x_dif_ini * factor_y) + y_ini;

				while (((y_sig <= y_cal && incr_y > 0) || (y_sig >= y_cal && incr_y < 0))
						&& y_sig != y_fin) {

					// Log.d("se queda", "while 3");

					if (y_sig != y_cal) {
						x_cal = (int) (y_dif_ini * factor_x) + x_ini;

						// Log.d("punto obtenido de y", String.valueOf(x_cal)
						// + "," + String.valueOf(y_sig));
						puntos_totales.add(new Puntos(x_cal, y_sig));
					}

					y_sig += incr_y;
					y_dif_ini += incr_y;
				}

				// Log.d("punto obtenido de x", String.valueOf(x_sig) + ","
				// + String.valueOf(y_cal));
				puntos_totales.add(new Puntos(x_sig, y_cal));

				x_sig += incr_x;
				x_dif_ini += incr_x;
			}

			while (y_sig != y_fin) {
				// Log.d("Se queda", "while 4");

				x_cal = (int) (y_dif_ini * factor_x) + x_ini;

				// Log.d("punto obtenido de y", String.valueOf(x_cal) + ","
				// + String.valueOf(y_sig));
				puntos_totales.add(new Puntos(x_cal, y_sig));

				y_sig += incr_y;
				y_dif_ini += incr_y;
			}

			x_ini = x_sig = x_fin - x_dif_fin;
			y_ini = y_sig = y_fin - y_dif_fin;

			indicardor_puntos++;

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
		double dist_fin;
		int ind_fij = 0;

		double permitido = factor / 3;
//		String fijo;
//		String total;

		for (int i = 0; i < puntos_totales.size(); i++) {
			dist_1 = Distancia(ind_fij, i);

			if (ind_fij < puntos_fijos.size() - 1) {
				dist_2 = Distancia(ind_fij + 1, i);

				if (dist_1 < dist_2) {
					dist_fin = dist_1;
				} else {
					dist_fin = dist_2;
					ind_fij++;
				}
			} else {
				dist_fin = dist_1;
			}

			if (dist_fin > permitido) {
				porcentje += 2.0 * dist_fin;
				

			}
			else {
//				Log.d("Permitido", "Distancia menor a 9");

			}

			// if (porcentje >= 100.0) {
			// porcentje = 100.0f;
			// break;
			// }

//			DecimalFormat format = new DecimalFormat("0.00");

			
//			fijo = puntos_fijos.get(ind_fij).get_X() + ","
//					+ puntos_fijos.get(ind_fij).get_Y();
//			total = puntos_totales.get(i).get_X() + ","
//					+ puntos_totales.get(i).get_Y();
//
//			
//			Log.d("indicador fijo", String.valueOf(ind_fij)); 
//			Log.d("Fijo", fijo);
//			Log.d("Obtenido", total);
//			Log.d("Distancia", String.valueOf(dist_fin));
			
			// fijo = format.format(puntos_fijos.get(ind_fij).get_X()/factor) +
			// ","
			// +
			// format.format((puntos_fijos.get(ind_fij).get_Y()-sobrante)/factor);
			// total = format.format(puntos_totales.get(i).get_X()/factor) + ","
			// + format.format((puntos_totales.get(i).get_Y()-sobrante)/factor);


		}
//		Log.d("Punto Final fijo", String.valueOf(ind_fij));
//		Log.d("Error", String.valueOf(porcentje));
		return porcentje;
	}

	private double Distancia(int ind_fij, int ind_tol) {

		int distancia;
		int x1 = puntos_fijos.get(ind_fij).get_X();
		int y1 = puntos_fijos.get(ind_fij).get_Y();
		int x2 = puntos_totales.get(ind_tol).get_X();
		int y2 = puntos_totales.get(ind_tol).get_Y();

		distancia = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);

		// Log.d("distancia", String.valueOf(distancia));

		return Math.sqrt(distancia);
	}
}
