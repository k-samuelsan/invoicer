import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Hello World!");
        File file = new File("C:\\Users\\Sam\\Desktop\\schedule.csv");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd");
        Map<String, Map<String, Integer>> map = new TreeMap<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String string = br.readLine();
        string = br.readLine();
        while(string != null) {
            String[] activity = string.split(",");
            String name = activity[0];
            Integer durationInSecs = Integer.parseInt(activity[5]);
            LocalDate dateTime = LocalDateTime.parse(activity[6], DateTimeFormatter.ISO_DATE_TIME).toLocalDate();
            String dateString = dateTime.format(format);
            Map<String, Integer> dayMap = map.get(dateString);
            if(dayMap == null) {
                dayMap = new HashMap<>();
                map.put(dateString, dayMap);
            }
            if(dayMap.get(name) == null) {
                dayMap.put(name, durationInSecs);
            } else {
                dayMap.put(name, durationInSecs + dayMap.get(name));
            }
            string = br.readLine();
        }
        for(String key: map.keySet()) {
            Map<String, Integer> dayMap = map.get(key);
            int totalDuration = 0;
            for(String task: dayMap.keySet()) {
                int duration  = dayMap.get(task);
                totalDuration += duration;
                int hours = duration / 3600;
                int minutes = (duration % 3600) / 60;
                System.out.println(task + " - " + String.format("%02d", hours) + ":" + String.format("%02d", minutes));
            }
            int hours = totalDuration / 3600;
            int minutes = (totalDuration % 3600) / 60;
            System.out.println("Total for " + key + ": " + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + "-------------------------------");
        }
    }
}
