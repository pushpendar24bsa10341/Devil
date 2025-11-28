package ecosmartwaste;

public class BinReport {
    private int id;
    private String location;
    private String status;
    private String wasteType;
    private double ecoScore;

    public BinReport(int id, String location, String status, String wasteType) {
        this.id = id;
        this.location = location;
        this.status = status;
        this.wasteType = wasteType;
        this.ecoScore = calculateEcoScore();
    }

    private double calculateEcoScore() {
        if (wasteType.equalsIgnoreCase("recyclable")) {
            return 8.5;
        } else {
            return 4.0;
        }
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getWasteType() {
        return wasteType;
    }

    public double getEcoScore() {
        return ecoScore;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BinReport [id=" + id + ", location=" + location + ", status=" + status + ", wasteType=" + wasteType
                + ", ecoScore=" + ecoScore + "]";
    }
}
