import com.google.gson.Gson;

import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String json1 =
            """
            {
            working_hours: {
                start: "09:00",
                end: "19:55"
            },
            planned_meeting: [
                {
                  start: "09:00",
                  end: "10:30"
                },
                {
                  start: "12:00",
                  end: "13:00"
                },
                {
                  start: "16:00",
                  end: "18:00"
                }
                ]
            }""";

        String json2 =
        """
            {
            working_hours: {
                start: "10:00",
                end: "18:30"
            },
            planned_meeting: [
                {
                  start: "10:00",
                  end: "11:30"
                },
                {
                  start: "12:30",
                  end: "14:30"
                },
                {
                  start: "14:30",
                  end: "15:00"
                },
                {
                  start: "16:00",
                  end: "17:00"
                }
                ]
            }""";

        Gson gson = new Gson();
        Calendar calendar = gson.fromJson(json1, Calendar.class);
        Calendar calendar2 = gson.fromJson(json2, Calendar.class);
        LocalTime startWork = calendar.getStartWorkTime();
        System.out.println(startWork);
        List<LocalTime> meetings = calendar.getStartMeeting();
        for (LocalTime meeting : meetings) {
            System.out.println(meeting);
        }
        int duration = 30;
        calendar.getPossibleMeetings(calendar2, duration);
    }
}