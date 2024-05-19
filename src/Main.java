import com.google.gson.Gson;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Calendar calendar = gson.fromJson(JsonData.JSON1, Calendar.class);
        Calendar calendar2 = gson.fromJson(JsonData.JSON2, Calendar.class);
        Duration duration = new Duration("00:30");
        List<Interval> possibleMeetings = calendar.getPossibleMeetings(calendar2, duration);
        System.out.print(possibleMeetings);
    }
}