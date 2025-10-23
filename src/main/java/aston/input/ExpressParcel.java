package aston.input;

public class ExpressParcel extends Parcel {
    private final int priorityLevel;

    public ExpressParcel(String recipientName, double weight, int trackingNumber, int priorityLevel) {
        super(recipientName, weight, trackingNumber);
        this.priorityLevel = priorityLevel;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    @Override
    public String toString() {
        return super.toString() + ", priority=" + priorityLevel;
    }
}
