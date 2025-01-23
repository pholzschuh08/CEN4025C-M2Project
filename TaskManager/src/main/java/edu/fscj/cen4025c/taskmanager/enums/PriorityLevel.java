package edu.fscj.cen4025c.taskmanager.enums;

public enum PriorityLevel {
    LOW("Low: Can be addressed later"),
    MEDIUM("Medium: Needs attention but not urgent"),
    HIGH("High: Requires immediate attention");

    private final String description;

    PriorityLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
