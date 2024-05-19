public class Duration {
    final private int minutes;
    final private int hours;

    public Duration(String time) {
        String[] parts = time.split(":");
        this.hours = Integer.parseInt(parts[0]);
        this.minutes = Integer.parseInt(parts[1]);
    }

    public int getMinutes() {
        return this.minutes+this.hours*60;
    }
}