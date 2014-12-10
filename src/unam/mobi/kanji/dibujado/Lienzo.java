package unam.mobi.kanji.dibujado;

import java.util.ArrayList;

import unam.mobi.kanji.R;
import unam.mobi.kanji.proce_info.Algoritmo_Reconocimiento;
import unam.mobi.kanji.proce_info.Procesa_Info;
import unam.mobi.kanji.proce_info.Puntos;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class Lienzo extends View implements OnTouchListener {

	private boolean cambio_pant;

	// Path para dibujar
	private Path path_auxiliar;
	private Path path_principal;

	// Paints para dibujar
	private Paint lineas;
	// private Paint cuadricula;
	private Paint referencias;

	// Variables necesarias para dibujar los puntos
	private Procesa_Info procesa_guia_punto;

	private ArrayList<Puntos> puntos;
	private ArrayList<Integer> guias;

	// Variables de la pantallas
	private int ancho;
	private int alto;
	private int factor;

	// Punto inicia y final
	private Puntos[] compara_puntos;

	// Se encarga de guardar los puntos
	private ArrayList<Puntos> puntos_importantes;
	private float x, y;
	private Puntos anterior;

	// Porcentaje acumulativo
	private float porcentaje;
	private int total_puntos;

	// Listener Personalizado
	private OnMiEscuchador miEscuchador;

//	// pRA PROBAR
//	private int sobrante;
//	private ArrayList<Integer> puntos_prue;
//	private ArrayList<Integer> guias_prueba;
//	private Paint prueba;
//	private Path path_prueba; 

	public Lienzo(Context context) {
		super(context);
		inicia_Lienzo();
	}

	public Lienzo(Context context, AttributeSet attributeSet) {
		// TODO Auto-generated constructor stub
		super(context, attributeSet);
		inicia_Lienzo();
	}

	private void inicia_Lienzo() {

		cambio_pant = true;

		procesa_guia_punto = new Procesa_Info();

		path_auxiliar = new Path();
		path_principal = new Path();

		incia_Paint();

		ancho = 0;
		alto = 0;
		factor = 0;

		puntos_importantes = new ArrayList<Puntos>();
		compara_puntos = new Puntos[2];
		anterior = new Puntos(-30, -30);

		total_puntos = 0;
		porcentaje = 0.0f;

		// Escuchador
		setFocusable(true);
		setFocusableInTouchMode(true);
		setOnTouchListener(this);
		
//		path_prueba = new Path(); 
	}

	private void incia_Paint() {

		// cuadricula = new Paint();
		// cuadricula.setStyle(Paint.Style.STROKE);
		// cuadricula.setStrokeWidth(2);
		// cuadricula.setColor(Color.BLACK);

		lineas = new Paint();
		lineas.setAntiAlias(true);
		lineas.setDither(true);
		lineas.setStyle(Paint.Style.STROKE);
		// lineas.setStrokeWidth(20);
		lineas.setColor(Color.BLACK);

		referencias = new Paint();
		referencias.setAntiAlias(true);
		referencias.setStyle(Paint.Style.FILL);
		referencias.setColor(Color.rgb(200, 0, 0));

//		prueba = new Paint();
//		prueba.setAntiAlias(true);
//		prueba.setDither(true);
//		prueba.setStyle(Paint.Style.STROKE);
//		prueba.setStrokeWidth(3);
//		prueba.setColor(Color.BLACK);
	}

	public void inic_Manej_Punt(ArrayList<Integer> guias,
			ArrayList<Integer> puntos) {
		procesa_guia_punto.inicia_punu_guia(guias, puntos);
		total_puntos = puntos.size();
		
//		puntos_prue = puntos; 
//		guias_prueba = guias; 

//		Log.d("tamaños guias", String.valueOf(guias.get(guias.size() - 2)));
//		Log.d("tamaño puntos", String.valueOf(puntos.size()));

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);

		if (cambio_pant) {

			cambio_pant = false;
			ancho = w - (w % 24);
			alto = h - (h % 24);
			factor = ancho / 24;

			lineas.setStrokeWidth((2 * factor) / 3);

			int sobrante = (alto - ancho) / 2;

			if (sobrante % factor > 0) {
				sobrante = sobrante + (factor - sobrante % factor);
			}
			Log.d("ancho", String.valueOf(ancho));
			Log.d("alto", String.valueOf(alto));
			Log.d("factor", String.valueOf(factor));
			Log.d("sobrante", String.valueOf(sobrante));

			procesa_guia_punto.set_factor_y_cent(factor, sobrante);
			establece_Puntos();
		}
	}

	private void establece_Puntos() {
		guias = procesa_guia_punto.sig_Guias();
		puntos = procesa_guia_punto.sig_Puntos();

	}

	public void limpiar_Pantalla() {

		porcentaje = 0.0f;
		procesa_guia_punto.reinicia_Kanji();
		establece_Puntos();
		path_principal.reset();
		setOnTouchListener(this);
		Toast.makeText(getContext(), "Lienzo limpio", Toast.LENGTH_SHORT)
				.show();
		invalidate();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Log.d("entra", "onDraw");

		canvas.drawColor(Color.argb(0, 0, 0, 0));
		
		
//		path_prueba.reset();
//		path_prueba.moveTo(puntos_prue.get(0) * factor, puntos_prue.get(1) * factor
//				+ sobrante);
//		int inicio_pun = 0;
//
//		for (int i = 0; i < guias_prueba.size(); i++) {
//			if (guias_prueba.get(i) != -1) {
//
//				for (int j = inicio_pun; j <= guias_prueba.get(i); j++) {
//					path_prueba.lineTo(puntos_prue.get(j * 2) * factor,
//							puntos_prue.get(j * 2 + 1) * factor + sobrante);
//					inicio_pun = j;
//				}
//
//			} else {
//				if (i + 1 < guias_prueba.size()) {
//					inicio_pun = guias_prueba.get(i + 1);
//					path_prueba.moveTo(puntos_prue.get(guias_prueba.get(i + 1) * 2)
//							* factor, puntos_prue.get(guias_prueba.get(i + 1) * 2 + 1)
//							* factor + sobrante);
//				}
//				prueba.setColor(prueba.getColor() * 5);
//
//			}
//		}

		// for (int i = 0; i < puntos.size(); i = i + 2) {
		// path_principal.lineTo(puntos.get(i) * factor, puntos.get(i + 1)
		// * factor + sobrante);
		// }

//		canvas.drawPath(path_prueba, prueba);

		canvas.drawPath(path_principal, lineas);

		// referencias.setTextSize(20);
		// canvas.drawText(alto + "," + ancho, 20, 40, referencias);
		// canvas.drawText(getBottom()+","+getRight(), 20, 100, referencias);

		// for (int i = 0; i <= alto; i = i + factor) {
		// canvas.drawText(i + "", 30, i, referencias);
		// canvas.drawLine(0.0f, i, ancho, i, cuadricula);
		// }
		// // for (int i = 0; i <= ancho; i = i + factor) {
		//
		// // }

		for (int i = 0; i < guias.size(); i++) {
			int valor_guia = guias.get(i);
//			Log.d("valor guia", valor_guia+"");
			canvas.drawCircle(puntos.get(valor_guia).get_X(),
					puntos.get(valor_guia).get_Y(), factor, referencias);
			// Log.d("guia", String.valueOf(i));
			// Log.d("valor guia", String.valueOf(guias[i]));
			// Log.d("tamaño puntos", String.valueOf(puntos.size()));
		}
		canvas.drawPath(path_auxiliar, lineas);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		x = event.getX();
		y = event.getY();

		// Log.d("Valor x", String.valueOf(event.getX()));

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			compara_puntos[0] = new Puntos((int) x, (int) y);
			path_auxiliar.moveTo(x, y);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			path_auxiliar.lineTo(x, y);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			compara_puntos[1] = new Puntos((int) x, (int) y);
			Evaluador();
			return true;
		} else {
			return true;
		}

		if ((anterior.get_X() - (int) x) >= 30
				|| (anterior.get_X() - (int) x) <= -30
				|| (anterior.get_Y() - (int) y) >= 30
				|| (anterior.get_Y() - (int) y) <= -30) {

			anterior = new Puntos((int) x, (int) y);
			puntos_importantes.add(anterior);
		}

		invalidate();
		return true;
	}

	private void Evaluador() {

		int distancia;
		// String punto;
		boolean bandera = true;

		distancia = Distancia(0, 0);

		if (distancia > factor * factor) {
			// Toast.makeText(getContext(), "Estas muy lejos de punto inicial",
			// Toast.LENGTH_SHORT).show();
			bandera = false;
		}

		distancia = Distancia(puntos.size() - 1, 1);

		if (distancia > factor * factor) {
			// Toast.makeText(getContext(), "Estas muy lejos de punto final",
			// Toast.LENGTH_SHORT).show();

			bandera = false;
		}

		if (bandera) {

			// Realizar evaluacion

//			DecimalFormat df = new DecimalFormat("0.00");
//
//			String puntos = "[";
//			for (int i = 0; i < puntos_importantes.size(); i++) {
//				puntos += df.format(1.0 * puntos_importantes.get(i).get_X()
//						/ factor)
//						+ ","
//						+ df.format((1.0 * puntos_importantes.get(i).get_Y() - sobrante)
//								/ factor) + " ; ";
//			}
//			puntos += "]";
//			Log.d("Puntos Importantes", puntos);

			Algoritmo_Reconocimiento reconocimiento = new Algoritmo_Reconocimiento(
					factor);

//			reconocimiento.setSobrante(sobrante);

			reconocimiento.set_Puntos(this.puntos, puntos_importantes);

			porcentaje += reconocimiento.calcula_Porcentaje();

			path_principal.addPath(path_auxiliar);
			establece_Puntos();

			if (guias.size() == 0) {
				setOnTouchListener(null);

				Log.d("Porcentaje", String.valueOf(porcentaje));
				Log.d("Total punto ", String.valueOf(total_puntos));
				porcentaje = (porcentaje / total_puntos);

				if (porcentaje > 100.0f) {
					porcentaje = 100.0f;
				}
				porcentaje = 100.0f - porcentaje;

				path_auxiliar.reset();
				puntos_importantes.clear();
				anterior = new Puntos(-30, -30);

				miEscuchador.inicia_Botones((int) porcentaje);
			}
		} else {
			Vibrator vibrator = (Vibrator) getContext().getSystemService(
					Context.VIBRATOR_SERVICE);

			if (vibrator != null) {
				vibrator.vibrate(200);
			}

			MediaPlayer player = MediaPlayer.create(getContext(), R.raw.sonido);
			// AudioManager am =
			// (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);

			player.start();
			player.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub

				}
			});

		}

		path_auxiliar.reset();
		puntos_importantes.clear();
		anterior = new Puntos(-30, -30);
		invalidate();

	}

	private int Distancia(int indice_punto, int indice_compara) {

		int distancia;
		int x1 = puntos.get(indice_punto).get_X();
		int y1 = puntos.get(indice_punto).get_Y();
		int x2 = compara_puntos[indice_compara].get_X();
		int y2 = compara_puntos[indice_compara].get_Y();

		distancia = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);

		// Log.d("distancia", String.valueOf(distancia));

		return distancia;
	}

	public interface OnMiEscuchador {
		public void inicia_Botones(int porcentaje);

	}

	public void setOnMiEscuchar(OnMiEscuchador escuchador) {
		miEscuchador = escuchador;
	}

}
