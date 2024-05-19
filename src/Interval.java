import java.time.LocalTime;

public class Interval {
    final private LocalTime begin;
    final private LocalTime end;

    public Interval(LocalTime intervalBegin, LocalTime intervalEnd) {
        begin = intervalBegin;
        end = intervalEnd;
    }

    public LocalTime getBegin() {
        return begin;
    }

    public LocalTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[\"" + begin + "\", \"" + end + "\"]";
    }
}
