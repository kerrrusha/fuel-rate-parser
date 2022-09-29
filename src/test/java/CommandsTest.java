import com.kerrrusha.fuel_rate_parser.model.GasStationFuelRate;
import com.kerrrusha.fuel_rate_parser.parser.GasStationCity;
import com.kerrrusha.fuel_rate_parser.parser.RateParser;
import com.kerrrusha.fuel_rate_parser.parser.RateParserSource;
import com.kerrrusha.fuel_rate_parser.tools.OutputUtil;
import com.kerrrusha.fuel_rate_parser.tools.RatesToTablePreparingUtil;
import dnl.utils.text.table.TextTable;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class CommandsTest {
	@Test
	public void RatesCommandTest() {
		GasStationCity city = GasStationCity.CHERNIHIV;
		RateParserSource source = RateParserSource.AUTORIA;

		List<GasStationFuelRate> rates;
		try {
			rates = RateParser.parseConcreteRates(source, city);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		OutputUtil.println("Rates from " + source + " for " + city + " city:");

		String[] columnNames = RatesToTablePreparingUtil.prepareHeader().toArray(new String[0]);
		String[][] data = RatesToTablePreparingUtil.prepareData(rates);

		TextTable tt = new TextTable(columnNames, data);
		tt.printTable();
	}
}
