package it.trionica.web.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



@ManagedBean(name="util")
@SessionScoped
public class Util {


	public boolean isNullOrEmptyString(String param){

		boolean ris=false;

		if(param==null || param.trim().equals(""))
			ris=true;

		return ris;

	}

	public String nvl(String param, String valoreDefault){

		String ris="";

		if(param==null || param.trim().equals(""))
			ris=valoreDefault;
		else
			ris=param;

		return ris;

	}

	public String padRight(String s, int n, char fill) {

		if(s==null)
			s="";

		return String.format("%1$-" + n + "s", s).replace(' ', fill);  
	}

	public String padLeft(String s, int n, char fill) {

		if(s==null)
			s="";

		return String.format("%1$" + n + "s", s).replace(' ', fill);  
	}


	public String upper(String param){

		String ris="";

		if(param!=null)
			ris=param.toUpperCase();


		return ris;

	}

	/**
	 * Ritorna una data in formato YYYYMMdd indietro di n giorni rispetto alla data attuale
	 * @return
	 */
	public static String getStartDateSearch(Integer days) {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd");
		Calendar cal = Calendar.getInstance();
	       
        //substracting two day from date in Java
        cal.add(Calendar.DAY_OF_MONTH, days);
        Date yesterday = cal.getTime();
		return formatter.format(yesterday);				
	}



	/**
	 * Metodo che restituisce il valore BigDecimal della stringa passata
	 * Il formato passato deve essere senza separatore di migliaia e con la virgola come separatore decimale
	 * Da utilizzare per trasformare i valori presi dal form
	 * @param valoreStr
	 * @return
	 */
	public BigDecimal getValoreBigDecimalDaStringa(String valoreStr){

		BigDecimal ris= null;

		if(!isNullOrEmptyString(valoreStr))
			ris=new BigDecimal(valoreStr.replace(',', '.'));

		return ris;
	}

	/**
	 * Metodo che restituisce la stringa con il valore del BigDecimal passato.
	 * Non prevede separatore di migliaia e il separatore dei decimali è la virgola
	 * Da utilizzare per travasare dati nel form	
	 * @param valore
	 * @return
	 */
	public String getValoreStringaDaBigDecimal(BigDecimal valore){

		String ris= "";

		if(valore!=null)
			ris=valore.toString().replace('.', ',');
		if (ris.indexOf(",")==0){
			ris="0"+ris;
		}
		return ris;
	}


	public String getMessageFromKey(String key){

		String msg="";

		FacesContext context = FacesContext.getCurrentInstance();
		if (context!=null) {
			Application app = context.getApplication();
			ResourceBundle bundle = app.getResourceBundle(context, "sfprop");
			msg = bundle.getString(key);
		}else {
			ResourceBundle bundle = ResourceBundle.getBundle("anagraficaippicaConst");
			msg = bundle.getString(key);
		}
		return msg;

	}






	public Date calcolaDataLimite(Date dataRif, int numGiorniDiff, String operazione){


		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		c.setTime(dataRif);

		if("-".equals(operazione)){

			c.add(Calendar.DAY_OF_MONTH, (-1*numGiorniDiff));
		}
		else{

			c.add(Calendar.DAY_OF_MONTH, numGiorniDiff);
		}


		//converte l'oggetto Calendar in una stringa nel formato dd/MM/yyyy
		return c.getTime();

	}




	/**
	 * Metodo per leggere le costanti nel bundle all'interno del codice java
	 * @param key
	 * @return
	 */
	public String getConstantValue(String key){

		String msg="";

		FacesContext context = FacesContext.getCurrentInstance();
		if (context!=null) {
			Application app = context.getApplication();
			ResourceBundle bundle = app.getResourceBundle(context, "pconst");
			msg = bundle.getString(key);
		}else {
			ResourceBundle bundle = ResourceBundle.getBundle("anagraficaippicaConstants");
			msg = bundle.getString(key);
		}
		return msg;

	}

	public String getConfigValue(String key){

		String msg="";

		FacesContext context = FacesContext.getCurrentInstance();
		if (context!=null) {
			Application app = context.getApplication();
			ResourceBundle bundle = app.getResourceBundle(context, "pconfig");
			msg = bundle.getString(key);
		}else {
			ResourceBundle bundle = ResourceBundle.getBundle("anagraficaippicaConfig");
			msg = bundle.getString(key);
		}		
		return msg;

	}





	public void removeBean(String beanName) { 
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(beanName, null);

	}


	public String generateUUID() {
		return createUUID().toString(); 
	}

	public boolean validaFormatUUID(String id) {
		try {
			UUID.fromString(id);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	protected UUID createUUID() {
		return  UUID.randomUUID();
	}


	public java.sql.Date convertLocalDateToSqlDate(LocalDate lDate) {

		java.sql.Date sDate = null;


		if(lDate!=null)
			sDate = java.sql.Date.valueOf(lDate);


		return sDate;
	}


	public String getStringaDaLocalDate(LocalDate data, String pattern) {
		String ret = null;
		if (data != null) {
			ret = data.format(DateTimeFormatter.ofPattern(pattern));
		}
		return ret;
	}


	public boolean checkRegex(String input, String regex) {

		boolean result = false;
		try{

			// Settiamo il pattern per il confronto
			Pattern p = Pattern.compile(regex);

			// Eseguiamo il match della stringa data con il pattern
			Matcher m = p.matcher(input);

			// Salviamo il risultato del match
			boolean matchFound = m.matches();

			if (matchFound)
				result = true;


		}
		catch(Exception e)
		{
		}
		return result;
	}

	public LocalDate getOggi() {

		return LocalDate.now();
	}


	/**
	 * Metodo che prende in input un array di stringhe e restituisce una lista con i soli elementi non nulli
	 * @param arrayIn
	 * @return
	 */
	public List<String> getElementiNotNullDaArray(String[] arrayIn){

		List<String> ris = new ArrayList<String>();

		if(arrayIn!=null){

			for(int i=0; i<arrayIn.length;i++){

				if(!this.isNullOrEmptyString(arrayIn[i]))
					ris.add(arrayIn[i]);
			}
		}

		return ris;
	}


/*
 * formatta la data in DD/MM/YYYY
 */
	public String formattaData(LocalDate data) {
		return getStringaDaLocalDate(data, "dd/MM/yyyy");
	}

	/*
	 * formatta la data in YYYYMMDD
	 */
	public String formattaData2(LocalDate data) {
		return getStringaDaLocalDate(data, "yyyyMMdd");
	}
	

	public LocalDate toLocalDate(String dataS) {
		return stringToLocalDate(dataS, "dd/MM/yyyy");
	}

	public LocalDate toLocalDate2(String dataS) {
		return stringToLocalDate(dataS, "yyyyMMdd");
	}

	public LocalDate toLocalDateFull(String dataS) {
		return stringToLocalDate(dataS, "dd/MM/yyyy hh:mm");
	}

	public String toDataNonFormattata(String data) {
		return getStringaDaLocalDate(toLocalDate(data), "yyyyMMdd");
	}
		
	public String toDataFormattata(String data2) {
		return getStringaDaLocalDate(toLocalDate2(data2), "dd/MM/yyyy");
	}
		
	public LocalDate stringToLocalDate(String dataS, String pattern) {
		LocalDate ret = null;
		try {
			if (dataS != null) {
				ret = LocalDate.parse(dataS, DateTimeFormatter.ofPattern(pattern));
			}
		} catch (Exception e) {}
		return ret;
	}

    public String doubleToImporto (double f){
        try{
          NumberFormat nf = NumberFormat.getInstance(Locale.ITALIAN);
          nf.setMaximumFractionDigits(2);
          nf.setMinimumFractionDigits(2);
          return nf.format(f);
        }
        catch(Exception pe)
        {
          return "0.00";
        }

       }


}
