package unam.mobi.kanji.dibujado;

import java.util.ArrayList;

import unam.mobi.kanji.R;
import unam.mobi.kanji.proce_info.Algoritmo_Reconocimiento;
import unam.mobi.kanji.proce_info.Procesa_Info;
import unam.mobi.kanji.proce_info.Puntos;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
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
	// private int ancho;
	// private int alto;
	private int factor;

	// Punto inicia y final
	private Puntos[] compara_puntos;

	// Se encarga de guardar los puntos
	private ArrayList<Puntos> puntos_importantes;
	private float x, y;
	private Puntos anterior;

	// Porcentaje acumulativo
	private double porcentaje;

	// Listener Personalizado
	private OnMiEscuchador miEscuchador;

	// Sonido
	// private MediaPlayer player;

	// public Lienzo(Context context) {
	// super(context);
	// inicia_Lienzo();
	// }
	private SoundPool soundPool;
	private int id_music;

	// marca de agua auxiliares

	private Paint cuadricula;
	private Paint marcas;
	private int sobrante;
	private Path marcar_agu;

	private ArrayList<Integer> guias_aux;
	private ArrayList<Integer> puntos_aux;

	// public Lienzo(Context context, AttributeSet attributeSet) {
	// // TODO Auto-generated constructor stub
	// super(context, attributeSet);
	// inicia_Lienzo();
	// }

	public Lienzo(Context context) {
		super(context);
		inicia_Lienzo();
	}

	private void inicia_Lienzo() {

		cambio_pant = true;

		procesa_guia_punto = new Procesa_Info();

		path_auxiliar = new Path();
		path_principal = new Path();

		incia_Paint();

		// ancho = 0;
		// alto = 0;
		factor = 0;

		puntos_importantes = new ArrayList<Puntos>();
		compara_puntos = new Puntos[2];
		anterior = new Puntos(-30, -30);

		porcentaje = 0.0f;

		// Escuchador
		setFocusable(true);
		setFocusableInTouchMode(true);
		setOnTouchListener(this);

		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		id_music = soundPool.load(getContext(), R.raw.sonido, 1);

		marcar_agu = new Path();

	}

	private void incia_Paint() {

		cuadricula = new Paint();
		cuadricula.setStyle(Style.FILL_AND_STROKE);
		cuadricula.setStrokeWidth(2);
		cuadricula.setColor(Color.BLACK);
		cuadricula.setTextScaleX(1.0f);
		//
		marcas = new Paint();
		marcas.setAntiAlias(true);
		marcas.setStyle(Style.STROKE);
		marcas.setColor(Color.rgb(192, 192, 192));
		// marcas.setColor(Color.BLACK);
		marcas.setStrokeJoin(Paint.Join.ROUND);
		marcas.setStrokeCap(Paint.Cap.ROUND);

		lineas = new Paint();
		lineas.setAntiAlias(true);
		lineas.setColor(Color.BLACK);
		// lineas.setDither(true);
		lineas.setStyle(Paint.Style.STROKE);
		lineas.setStrokeJoin(Paint.Join.ROUND);
		lineas.setStrokeCap(Paint.Cap.ROUND);

		referencias = new Paint();
		referencias.setAntiAlias(true);
		referencias.setStyle(Paint.Style.FILL);
		referencias.setColor(Color.rgb(200, 0, 0));

	}

	public void inic_Manej_Punt(ArrayList<Integer> guias,
			ArrayList<Integer> puntos) {
		procesa_guia_punto.inicia_punu_guia(guias, puntos);

		guias_aux = guias;
		puntos_aux = puntos;

		// Log.d("tamaños guias", String.valueOf(guias.get(guias.size() - 2)));
		// Log.d("tamaño puntos", String.valueOf(puntos.size()));

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		if (cambio_pant) {

			cambio_pant = false;
			int ancho = w - (w % 24);
			int alto = h - (h % 24);
			factor = ancho / 24;

			lineas.setStrokeWidth(factor);
			lineas.setPathEffect(new CornerPathEffect(factor));

			marcas.setStrokeWidth(2 * factor);
			marcas.setPathEffect(new CornerPathEffect(2 * factor));

			sobrante = (alto - ancho) / 2;

			if (sobrante % factor > 0) {
				sobrante = sobrante + (factor - sobrante % factor);
			}
			// Log.d("ancho", String.valueOf(ancho));
			// Log.d("alto", String.valueOf(alto));
			// Log.d("factor", String.valueOf(factor));
			// Log.d("sobrante", String.valueOf(sobrante));

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

		canvas.drawColor(Color.argb(0, 0, 0, 0));

		for (int i = 0; i < getMeasuredHeight(); i += factor) {
			canvas.drawLine(0, i, getMeasuredWidth(), i, cuadricula);
//			canvas.drawText("" + i, 10, i, cuadricula);
		}

		for (int i = 0; i < getMeasuredWidth(); i += factor) {
			canvas.drawLine(i, 0, i, getMeasuredHeight(), cuadricula);
//			canvas.drawText("" + i, i, 15, cuadricula);
		}

		marcar_agu.reset();
		marcar_agu.moveTo(puntos_aux.get(0) * (factor / 10.0f),
				(puntos_aux.get(1) * (factor / 10.0f)) + sobrante);
		int inicio_pun = 0;

		for (int i = 0; i < guias_aux.size(); i++) {
			if (guias_aux.get(i) != -1) {

				for (int j = inicio_pun; j <= guias_aux.get(i); j++) {
					marcar_agu.lineTo(puntos_aux.get(j * 2) * (factor / 10.0f),
							puntos_aux.get(j * 2 + 1) * (factor / 10.0f)
									+ sobrante);
					inicio_pun = j;
					// Log.d("Punto: ", puntos_aux.get(j * 2) * (factor / 10.0f)
					// + " , " + puntos_aux.get(j * 2 + 1)
					// * (factor / 10.0f) + sobrante);

				}

			} else {
				if (i + 1 < guias_aux.size()) {
					inicio_pun = guias_aux.get(i + 1);
					marcar_agu.moveTo(puntos_aux.get(guias_aux.get(i + 1) * 2)
							* (factor / 10.0f),
							puntos_aux.get(guias_aux.get(i + 1) * 2 + 1)
									* (factor / 10.0f) + sobrante);

					// Log.d("Punto: ", puntos.get(i+1).get_X() * (factor /
					// 10.0f)
					// + " , " + puntos.get(i+1).get_Y() * (factor / 10.0f)
					// + sobrante);

				}
			}

		}
		
		canvas.drawPath(marcar_agu, marcas);


		for (int i = 0; i < guias.size(); i++) {
			int valor_guia = guias.get(i);
			canvas.drawCircle(puntos.get(valor_guia).get_X(),
					puntos.get(valor_guia).get_Y(), factor, referencias);
		}

		canvas.drawPath(path_principal, lineas);

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

			if ((anterior.get_X() - (int) x) >= factor
					|| (anterior.get_X() - (int) x) <= -factor
					|| (anterior.get_Y() - (int) y) >= factor
					|| (anterior.get_Y() - (int) y) <= -factor) {

				anterior = new Puntos((int) x, (int) y);
				puntos_importantes.add(anterior);
			}

			evaluador();
			return true;
		} else {
			return true;
		}

		if ((anterior.get_X() - (int) x) >= factor
				|| (anterior.get_X() - (int) x) <= -factor
				|| (anterior.get_Y() - (int) y) >= factor
				|| (anterior.get_Y() - (int) y) <= -factor) {

			anterior = new Puntos((int) x, (int) y);
			puntos_importantes.add(anterior);
		}

		invalidate();
		return true;
	}

	private void evaluador() {

		int distancia;
		// String punto;
		boolean bandera = true;

		distancia = distancia(0, 0);

		if (distancia > factor * factor) {
			bandera = false;
		}

		distancia = distancia(puntos.size() - 1, 1);

		if (distancia > factor * factor) {
			bandera = false;
		}

		if (bandera) {

			Algoritmo_Reconocimiento reconocimiento = new Algoritmo_Reconocimiento(
					factor, puntos.size());

			puntos_importantes.remove(0);
			puntos_importantes.remove(puntos_importantes.size() - 1);
			puntos_importantes.add(0, puntos.get(0));
			puntos_importantes.add(puntos.get(puntos.size() - 1));

			reconocimiento.set_Puntos(puntos, puntos_importantes);

			porcentaje += reconocimiento.calcula_Porcentaje();

			// if (porcentaje>=100)
			// {
			// path_auxiliar.reset();
			// puntos_importantes.clear();
			// anterior = new Puntos(-30, -30);
			//
			// miEscuchador.inicia_Botones(0);
			// }

			path_principal.addPath(path_auxiliar);
			establece_Puntos();

			if (guias.size() == 0) {
				setOnTouchListener(null);

				if (porcentaje > 100.0) {
					porcentaje = 100.0;
				}
				porcentaje = 100.0 - porcentaje;

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

			// AudioManager am =
			// (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);

			// MediaPlayer player = MediaPlayer.create(getContext(),
			// R.raw.sonido);
			soundPool.play(id_music, 1, 1, 0, 0, 1);

		}

		path_auxiliar.reset();
		puntos_importantes.clear();
		anterior = new Puntos(-30, -30);
		invalidate();

	}

	private int distancia(int indice_punto, int indice_compara) {

		int distancia;
		int x1 = puntos.get(indice_punto).get_X();
		int y1 = puntos.get(indice_punto).get_Y();
		int x2 = compara_puntos[indice_compara].get_X();
		int y2 = compara_puntos[indice_compara].get_Y();

		distancia = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);

		return distancia;
	}

	public interface OnMiEscuchador {
		public void inicia_Botones(int porcentaje);

	}

	public void setOnMiEscuchar(OnMiEscuchador escuchador) {
		miEscuchador = escuchador;
	}

}
