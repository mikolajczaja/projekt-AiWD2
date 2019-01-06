package aiwd.model;

public enum FlowDirection {

    OUT(7),
    IN(9);

    private int directionCode;

    FlowDirection(int directionCode) {
        this.directionCode = directionCode;
    }

    public static FlowDirection valueOf(int directionCode) {
        for (FlowDirection flowDirection : values()) {
            if (flowDirection.directionCode == directionCode) {
                return flowDirection;
            }
        }
        throw new IllegalArgumentException("No FlowDirection values found for directionCode: " + directionCode);
    }
}
