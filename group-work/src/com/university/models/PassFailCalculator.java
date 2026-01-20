package com.university.models;

public class PassFailCalculator extends GradeCalculator {
    public PassFailCalculator(String courseCode, String studentId) {
        super(courseCode, studentId);
    }

    @Override
    public String calculateGrade(double score) {
        if (!isValidScore(score)) return "Invalid";
        return score >= 60 ? "PASS" : "FAIL";
    }

    @Override
    public double calculateGPA(String grade) {
        return "PASS".equalsIgnoreCase(grade) ? 4.0 : 0.0;
    }

    @Override
    public boolean isValidScore(double score) {
        return score >= 0 && score <= 100;
    }
}