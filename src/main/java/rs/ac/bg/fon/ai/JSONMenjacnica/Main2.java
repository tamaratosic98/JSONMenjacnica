package rs.ac.bg.fon.ai.JSONMenjacnica;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class Main2 {
	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "1a5a387728ba541d19e546f6f2812417";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES="EUR,CAD,CHF";
	private static final String DATE="&date=2020-12-21";
	
	public static void main(String[] args) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Date date=new Date("2020/12/21");
		Transakcija t1 = new Transakcija();
		Transakcija t2 = new Transakcija();
		Transakcija t3 = new Transakcija();

		try (FileWriter file = new FileWriter("ostale_transakcije.json")) {

			URL url = new URL(BASE_URL  + "/historical?access_key=" + API_KEY+"&source="+ SOURCE +"&currencies="
					+ CURRENCIES+ DATE);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			JsonObject res = gson.fromJson(reader, JsonObject.class);

			System.out.println(res);
			if (res.get("success").getAsBoolean()) {
				List<Transakcija> transakcije =new LinkedList<Transakcija>();
				double kurs1 = res.get("quotes").getAsJsonObject().get("USDEUR").getAsDouble();
				double konvertovaniIznos1 = 100 * kurs1;

				t1.setIzvornaValuta("USD");
				t1.setKrajnjaValuta("EUR");
				t1.setPocetniIznos(100);
				t1.setKonvertovaniIznos(konvertovaniIznos1);
				t1.setDatumTransakcije(date);

				transakcije.add(t1);

				double kurs2 = res.get("quotes").getAsJsonObject().get("USDCAD").getAsDouble();
				double konvertovaniIznos2 = 100 * kurs2;

				t2.setIzvornaValuta("USD");
				t2.setKrajnjaValuta("CAD");
				t2.setPocetniIznos(100);
				t2.setKonvertovaniIznos(konvertovaniIznos2);
				t2.setDatumTransakcije(date);

				transakcije.add(t2);
				
				double kurs3 = res.get("quotes").getAsJsonObject().get("USDCHF").getAsDouble();
				double konvertovaniIznos3 = 100 * kurs3;

				t3.setIzvornaValuta("USD");
				t3.setKrajnjaValuta("CHF");
				t3.setPocetniIznos(100);
				t3.setKonvertovaniIznos(konvertovaniIznos3);
				t3.setDatumTransakcije(date);

				transakcije.add(t3);
				gson.toJson(transakcije, file);	
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}