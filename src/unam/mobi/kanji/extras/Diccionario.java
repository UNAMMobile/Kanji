package unam.mobi.kanji.extras;

public class Diccionario {
	
	private String espa; 
	private String japo;
	
	public Diccionario(String espa, String japo) {
		this.espa = espa; 
		this.japo = japo;  
	}
	
	public String get_Espa ()
	{
		return espa;  
	}
		
	public String get_Japo()
	{
		return japo;
	}
	
}
