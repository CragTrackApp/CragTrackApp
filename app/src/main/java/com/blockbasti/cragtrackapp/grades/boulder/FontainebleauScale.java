package com.blockbasti.cragtrackapp.grades.boulder;

import java.util.HashMap;

public class FontainebleauScale implements BoulderScale {

    static HashMap<Integer, BoulderGrade> Grades;

    static String GradeUnit = "Fb";

    static {
        Grades = new HashMap<>();
        Grades.put(1, new BoulderGrade(1, "1"));
        Grades.put(2, new BoulderGrade(2, "2"));
        Grades.put(3, new BoulderGrade(3, "3"));
        Grades.put(4, new BoulderGrade(4, "4a"));
        Grades.put(5, new BoulderGrade(5, "4b"));
        Grades.put(6, new BoulderGrade(6, "4c"));
        Grades.put(7, new BoulderGrade(7, "5a"));
        Grades.put(8, new BoulderGrade(8, "5b"));
        Grades.put(9, new BoulderGrade(9, "5c"));
        Grades.put(10, new BoulderGrade(10, "6a"));
        Grades.put(11, new BoulderGrade(11, "6a+"));
        Grades.put(12, new BoulderGrade(12, "6b"));
        Grades.put(13, new BoulderGrade(13, "6b+"));
        Grades.put(14, new BoulderGrade(14, "6c"));
        Grades.put(15, new BoulderGrade(15, "6c+"));
        Grades.put(16, new BoulderGrade(16, "7a"));
        Grades.put(17, new BoulderGrade(17, "7a+"));
        Grades.put(18, new BoulderGrade(18, "7b"));
        Grades.put(19, new BoulderGrade(19, "7b+"));
        Grades.put(20, new BoulderGrade(20, "7c"));
        Grades.put(21, new BoulderGrade(21, "7c+"));
        Grades.put(22, new BoulderGrade(22, "8a"));
        Grades.put(23, new BoulderGrade(23, "8a+"));
        Grades.put(24, new BoulderGrade(24, "8b"));
        Grades.put(25, new BoulderGrade(25, "8b+"));
        Grades.put(26, new BoulderGrade(26, "8c"));
        Grades.put(27, new BoulderGrade(27, "8c+"));
        Grades.put(28, new BoulderGrade(28, "9a"));
    }
}
