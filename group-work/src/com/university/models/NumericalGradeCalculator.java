package com.university.models;

public class NumericalGradeCalculator extends GradeCalculator {
    public NumericalGradeCalculator(String courseCode, String studentId) {
        super(courseCode, studentId);
    }

    @Override
    public String calculateGrade(double score) {
        if (!isValidScore(score)) return "Invalid";
        return String.valueOf(score);
    }

    @Override
    public double calculateGPA(String grade) {
        try {
            double s = Double.parseDouble(grade);
            // Map numeric to 4.0 scale linearly 0-100 -> 0-4
            return Math.max(0.0, Math.min(4.0, (s / 100.0) * 4.0));
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }

    @Override
    public boolean isValidScore(double score) {
        return score >= 0 && score <= 100;
    }
}