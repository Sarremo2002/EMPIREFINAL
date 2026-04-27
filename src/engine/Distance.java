package engine;

import java.io.Serializable;

public class Distance implements Serializable {
    private static final long serialVersionUID = 1L;

    private String from;
    private String to;
    private int distance;
    public Distance(String from,String to, int distance)
    {
        this.from=from;
        this.to=to;
        this.distance=distance;
    }
    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }
    public int getDistance() {
        return distance;
    }

}
