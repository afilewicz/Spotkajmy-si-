import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Calendar {
    final private JsonObject working_hours;
    private JsonArray planned_meeting;

    public Calendar(JsonObject working, JsonArray meetings) {
        if (working == null || meetings == null) {
            throw new IllegalArgumentException("Working hours or planned meetings cannot be null.");
        }
        this.working_hours = working;
        this.planned_meeting = meetings;
    }

    public LocalTime getStartWorkTime() {
        return LocalTime.parse(this.working_hours.get("start").getAsString());
    }

    public LocalTime getEndWorkTime() {
        return LocalTime.parse(this.working_hours.get("end").getAsString());
    }

    public void sortMeetings() {
        List<JsonElement> jsonElementList = new ArrayList<>();
        for (JsonElement element : this.planned_meeting) {
            jsonElementList.add(element);
        }
        jsonElementList.sort(Comparator.comparing(e -> e.getAsJsonObject().get("start").getAsString()));
        JsonArray sortedMeetings = new JsonArray();
        for (JsonElement element : jsonElementList) {
            sortedMeetings.add(element);
        }
        this.planned_meeting = sortedMeetings;
    }

    public List<Interval> getFreeTime() {
        List<Interval> freeTimes = new ArrayList<>();
        LocalTime endWork = getEndWorkTime();
        LocalTime currentHour = getStartWorkTime();
        this.sortMeetings();
        for(JsonElement interval: this.planned_meeting) {
            LocalTime hourAsTime = LocalTime.parse(interval.getAsJsonObject().get("start").getAsString());
            if(currentHour.isBefore(hourAsTime)) {
                freeTimes.add(new Interval(currentHour, hourAsTime));
            }
            currentHour = LocalTime.parse(interval.getAsJsonObject().get("end").getAsString());
        }
        if(currentHour.isBefore(endWork)) {
            freeTimes.add(new Interval(currentHour, endWork));
        }
        return freeTimes;
    }

    public List<Interval> getPossibleMeetings(Calendar c, Duration duration) {
        List<Interval> freeTimes = getFreeTime();
        List<Interval> freeTimes2 = c.getFreeTime();
        List<Interval> possibleMeetings = new ArrayList<>();
        for(Interval freeTime: freeTimes) {
            for(Interval freeTime2: freeTimes2) {
                if (!freeTime2.getBegin().isAfter(freeTime.getEnd()) &&
                        !freeTime.getBegin().isAfter(freeTime2.getEnd())) {
                    LocalTime maxBegin = freeTime.getBegin().isAfter(freeTime2.getBegin()) ? freeTime.getBegin() : freeTime2.getBegin();
                    LocalTime minEnd = freeTime.getEnd().isBefore(freeTime2.getEnd()) ? freeTime.getEnd() : freeTime2.getEnd();
                    if(!minEnd.isBefore(maxBegin.plusMinutes(duration.getMinutes()))) {
                        possibleMeetings.add(new Interval(maxBegin, minEnd));
                    }
                }
            }
        }
        return possibleMeetings;
    }
}
