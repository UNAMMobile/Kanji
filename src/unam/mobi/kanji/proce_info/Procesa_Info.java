package unam.mobi.kanji.proce_info;

import java.util.ArrayList;

public class Procesa_Info {

	private ArrayList<Integer> guias;
	private ArrayList<Integer> puntos;

	private int cont_guia;
	private int cont_punto;
	private int ultimo_punto;

	// factor y distribucion
	private float factor;
	private int centrado;

	public Procesa_Info() {
		factor = 0;
		centrado = 0;
		reinicia_Kanji();
	}

	public void inicia_punu_guia(ArrayList<Integer> guias,
			ArrayList<Integer> puntos) {
		this.guias = guias;
		this.puntos = puntos;

	}

	public void reinicia_Kanji() {
		cont_guia = 0;
		cont_punto = 0;
		ultimo_punto = -1;
	}

	public void set_factor_y_cent(int factor, int centrado) {
		this.centrado = centrado;
		this.factor = factor / 10.0f;
	}

	public ArrayList<Integer> sig_Guias() {
		ArrayList<Integer> sig_guias = new ArrayList<Integer>();
		int aux_valor;
		ultimo_punto++;

		while (cont_guia < guias.size()) {
			aux_valor = guias.get(cont_guia);

			if (aux_valor == -1) {
				cont_guia++;
				break;
			}

			sig_guias.add(aux_valor - ultimo_punto);
			cont_guia++;
		}

		ultimo_punto = guias.get(cont_guia - 2);

		return sig_guias;
	}

	public ArrayList<Puntos> sig_Puntos() {
		ArrayList<Puntos> sig_puntos = new ArrayList<Puntos>();

		int x, y;

		while (cont_punto <= ultimo_punto) {

			x = (int) (puntos.get(cont_punto * 2) * factor);
			y = (int) (puntos.get(cont_punto * 2 + 1) * factor) + centrado;

			sig_puntos.add(new Puntos(x, y));
			cont_punto++;
		}

		return sig_puntos;

	}

}
