package com.tianzh.admin.business.analysis.model;

public class ThPayProperty {
    String ThPayId;
    float weight;

    public String getThPayId() {
        return ThPayId;
    }

    public void setThPayId(String thPayId) {
        ThPayId = thPayId;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ThPayProperty{");
        sb.append("ThPayId='").append(ThPayId).append('\'');
        sb.append(", weight=").append(weight);
        sb.append('}');
        return sb.toString();
    }
}