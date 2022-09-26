import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		final String URL = "https://auto.ria.com/uk/toplivo/kiev/";

		final String GAS_STATION_NAME = "refuel";
		final String A95P = "a95p";
		final String A95 = "a95";
		final String A92 = "a92";
		final String DT = "dt";
		final String GAZ = "gaz";

		final Document document = Jsoup.connect(URL).get();
		List<Element> rows = document.select(".refuelContent tr");
		rows.forEach(row -> {
			System.out.println("");

			Element gasStationElement = row.selectFirst("." + GAS_STATION_NAME + " a");
			assert gasStationElement != null;
			String gasStationName = gasStationElement.text();
			System.out.println(gasStationName);

			Element a95PElement = row.selectFirst("." + A95P + " span");
			assert a95PElement != null;
			String a95Pprice = a95PElement.text();
			System.out.println(a95Pprice);

			Element a95Element = row.selectFirst("." + A95 + " span");
			assert a95Element != null;
			String a95Price = a95Element.text();
			System.out.println(a95Price);

			Element a92Element = row.selectFirst("." + A92 + " span");
			assert a92Element != null;
			String a92Price = a92Element.text();
			System.out.println(a92Price);

			Element dtElement = row.selectFirst("." + DT + " span");
			assert dtElement != null;
			String dtPrice = dtElement.text();
			System.out.println(dtPrice);

			Element gazElement = row.selectFirst("." + GAZ + " span");
			assert gazElement != null;
			String gazPrice = a95PElement.text();
			System.out.println(gazPrice);
		});
	}
}
