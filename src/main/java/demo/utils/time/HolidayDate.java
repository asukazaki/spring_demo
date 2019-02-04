package demo.utils.time;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayDate {
	
	private final String HOLIDAY_CSV_PATH = "data/syukujitsu_kyujitsu.csv";
	
	private final String DELIMITER_DATE = "/";
	
	public String IsHoliday(String yyyy, String MM, String dd) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(HOLIDAY_CSV_PATH), StandardCharsets.UTF_8);
		Map<String, String>holidayMap = new HashMap<>();
		
		for(String s : lines) {
			String[] items = s.split(",");
			holidayMap.put(items[0].replaceAll("-", DELIMITER_DATE), items[1]);
		}
		
		StringBuilder sb = new StringBuilder(yyyy);
		sb.append(DELIMITER_DATE);
		sb.append(MM);
		sb.append(DELIMITER_DATE);
		sb.append(dd);
		if(holidayMap.containsKey(sb.toString())) {
			return holidayMap.get(sb.toString());
		}
		
		return null;
		
	}
	
	public List<String[]> getHolidayTargetMonth(String yyyy, String MM) throws IOException{
		List<String[]> list = new ArrayList<>();
		
		
		List<String> lines = Files.readAllLines(Paths.get(HOLIDAY_CSV_PATH), StandardCharsets.UTF_8);
		
		String targetMonth = yyyy + "-" + MM;
		for(String s : lines) {
			if(s.startsWith(targetMonth)) {
				String[] holidayDateAndName = new String[2];
				String[] items = s.split(",");
				// items[0] -> 2017-01-01, items[1] -> 元日
				holidayDateAndName[0] = items[0].split("-")[2];
				holidayDateAndName[1] = items[1];
				list.add(holidayDateAndName);
			}
		}
		return list;
		
	}

}
