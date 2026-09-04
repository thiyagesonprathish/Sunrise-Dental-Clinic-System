package model;

public class Treatment {
    private String treatmentType;
    private double cost;

    public Treatment(String treatmentType, double cost) {
        this.treatmentType = treatmentType;
        this.cost = cost;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return treatmentType + " - Rs. " + cost;
    }
}