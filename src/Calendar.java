import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
    final private JsonObject working_hours;
    final private JsonArray planned_meeting;

    public Calendar(JsonObject workingHours, JsonArray plannedMeeting) {
        working_hours = workingHours;
        planned_meeting = plannedMeeting;
    }

    public LocalTime getStartWorkTime() {
        return LocalTime.parse(working_hours.get("start").getAsString());
    }

    public LocalTime getEndWorkTime() {
        JsonElement hourAsJsonElement = working_hours.get("end");
        String hourAsString = hourAsJsonElement.getAsString();
        return LocalTime.parse(hourAsString);
    }

    public List<LocalTime> getStartMeeting() {
        List<LocalTime> starts = new ArrayList<>();
        for(JsonElement hourAsJsonElement: planned_meeting) {
            JsonObject hourAsJsonObject = hourAsJsonElement.getAsJsonObject();
            String hourAsString = hourAsJsonObject.get("start").getAsString();
            LocalTime hourAsTime = LocalTime.parse(hourAsString);
            starts.add(hourAsTime);
        }
        return starts;
    }

    public List<LocalTime> getEndMeeting() {
        List<LocalTime> ends = new ArrayList<>();
        for(JsonElement hourAsJsonElement: planned_meeting) {
            JsonObject hourAsJsonObject = hourAsJsonElement.getAsJsonObject();
            String hourAsString = hourAsJsonObject.get("end").getAsString();
            LocalTime hourAsTime = LocalTime.parse(hourAsString);
            ends.add(hourAsTime);
        }
        return ends;
    }
//struktura danych z push uklada w srodku i sortuje
    public List<List<LocalTime>> getFreeTime() {
        List<List<LocalTime>> freeTimes = new ArrayList<>();
        LocalTime endWork = getEndWorkTime();
        LocalTime currentHour = getStartWorkTime();
        for(JsonElement meetingAsJsonElement: planned_meeting) {
            JsonObject meetingAsJsonObject = meetingAsJsonElement.getAsJsonObject();
            String hourAsString = meetingAsJsonObject.get("start").getAsString();
            LocalTime hourAsTime = LocalTime.parse(hourAsString);
            if(currentHour.isBefore(hourAsTime)) {
                List<LocalTime> f_time= new ArrayList<>();
                f_time.add(currentHour);
                f_time.add(hourAsTime);
                freeTimes.add(f_time);
            }
            String endHour = meetingAsJsonObject.get("end").getAsString();
            currentHour = LocalTime.parse(endHour);
        }
        if(currentHour.isBefore(endWork)) {
            List<LocalTime> f_time= new ArrayList<>();
            f_time.add(currentHour);
            f_time.add(endWork);
            freeTimes.add(f_time);
        }
        System.out.print(freeTimes);
        return freeTimes;
    }

    public List<List<LocalTime>> getPossibleMeetings(Calendar c, int duration) {
        List<List<LocalTime>> freeTimes = getFreeTime();
        List<List<LocalTime>> freeTimes2 = c.getFreeTime();
        List<List<LocalTime>> possibleMeetings = new ArrayList<>();
        for(List<LocalTime> freeTime: freeTimes) {
            for(List<LocalTime> freeTime2: freeTimes2) {
                if (freeTime2.getFirst().isAfter(freeTime.getLast())) {
                    continue;
                }
                if (freeTime.getFirst().isBefore(freeTime2.getFirst()) && !freeTime.getLast().isBefore(freeTime2.getFirst().plusMinutes(duration)))
                {
                    List<LocalTime> potentialMeeting = new ArrayList<>();
                    potentialMeeting.add(freeTime2.getFirst());
                    potentialMeeting.add(freeTime.getLast());
                    possibleMeetings.add(potentialMeeting);
                }
                if (freeTime2.getFirst().isBefore(freeTime.getFirst()) && !freeTime2.getLast().isBefore(freeTime.getFirst().plusMinutes(duration)))
                {
                    List<LocalTime> potentialMeeting2 = new ArrayList<>();
                    potentialMeeting2.add(freeTime.getFirst());
                    potentialMeeting2.add(freeTime2.getLast());
                    possibleMeetings.add(potentialMeeting2);
                }
            }
        }
        System.out.print(possibleMeetings);
        return possibleMeetings;
    }
}
