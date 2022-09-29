import com.kerrrusha.fuel_rate_parser.tools.NumberFormattingUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberFormattingUtilTest {
	@Test
	public void FormatDoubleTest() {
		double value = 12.34;
		final String expected = "12.34";
		assertEquals(expected, NumberFormattingUtil.formatDouble(value));
	}
}
