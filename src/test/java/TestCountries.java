import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ip3country.CountryLookup;

import static org.junit.Assert.assertEquals;

public class TestCountries {

    private static Map<String, String> lookupTable = createLookup();

    private static Random rand = new Random(System.currentTimeMillis());

    private static Map<String, String> createLookup() {
        Map<String, String> lookup = new HashMap<>();
        lookup.put("1.1.1.1", "US");
        lookup.put("2.2.2.2", "FR");
        lookup.put("3.3.3.3", "US");
        lookup.put("4.4.4.4", "US");
        lookup.put("5.5.5.5", "DE");
        lookup.put("6.6.6.6", "US");
        lookup.put("7.7.7.7", "US");
        lookup.put("8.8.8.8", "US");
        lookup.put("9.9.9.9", "US");
        lookup.put("11.11.11.11", "US");
        lookup.put("12.12.12.12", "US");
        lookup.put("13.13.13.13", "US");
        lookup.put("14.14.14.14", "JP");
        lookup.put("15.15.15.15", "US");
        lookup.put("16.16.16.16", "US");
        lookup.put("17.17.17.17", "US");
        lookup.put("18.18.18.18", "US");
        lookup.put("19.19.19.19", "US");
        lookup.put("20.20.20.20", "US");
        lookup.put("21.21.21.21", "US");
        lookup.put("22.22.22.22", "US");
        lookup.put("23.23.23.23", "US");
        lookup.put("24.24.24.24", "US");
        lookup.put("25.25.25.25", "GB");
        lookup.put("26.26.26.26", "US");
        lookup.put("27.27.27.27", "CN");
        lookup.put("28.28.28.28", "US");
        lookup.put("29.29.29.29", "US");
        lookup.put("30.30.30.30", "US");
        lookup.put("31.31.31.31", "MD");
        lookup.put("41.41.41.41", "EG");
        lookup.put("42.42.42.42", "KR");
        lookup.put("45.45.45.45", "CA");
        lookup.put("46.46.46.46", "RU");
        lookup.put("49.49.49.49", "TH");
        lookup.put("101.101.101.101", "TW");
        lookup.put("110.110.110.110", "CN");
        lookup.put("111.111.111.111", "JP");
        lookup.put("112.112.112.112", "CN");
        lookup.put("150.150.150.150", "KR");
        lookup.put("200.200.200.200", "BR");
        lookup.put("202.202.202.202", "CN");
        lookup.put("45.85.95.65", "CH");
        lookup.put("58.96.74.25", "AU");
        lookup.put("88.99.77.66", "DE");
        lookup.put("25.67.94.211", "GB");
        lookup.put("27.67.94.211", "VN");
        lookup.put("27.62.93.211", "IN");

        return lookup;
    }

    @Test
    public void testCases() {
        for (String ip : lookupTable.keySet()) {
            String computed = CountryLookup.lookupIPString(ip);
            assertEquals(lookupTable.get(ip), computed);
        }
    }

    @Test
    public void testRandom() {
        CountryLookup.initialize();

        List<Long> ipRanges = CountryLookup.Companion.getIpRanges();
        List<String> countryCodes = CountryLookup.Companion.getCountryCodes();

        for (int i = 1; i < ipRanges.size(); i++) {
            Long max = ipRanges.get(i);
            Long min = ipRanges.get(i-1);
            String minLookup = lookup(min);
            String maxLookup = lookup(max - 1);

            String expected = countryCodes.get(i);
            assertEquals(expected, minLookup);
            assertEquals(expected, maxLookup);
            for (int j = 0; j < 100; j++) {
                Long randomIndex = min + rand.nextInt(((int)(max - min)));
                String indexLookup = lookup(randomIndex);
                assertEquals(expected, indexLookup);
            }
        }
    }

    private String lookup(Long index) {
        String result = CountryLookup.lookupIPNumber(index);
        if (result == null) {
            return  "--";
        }
        return result;
    }
}
