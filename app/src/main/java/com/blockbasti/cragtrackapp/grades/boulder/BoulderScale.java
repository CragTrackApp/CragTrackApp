package com.blockbasti.cragtrackapp.grades.boulder;

import com.blockbasti.cragtrackapp.grades.ClimbScale;

import java.util.HashMap;

public interface BoulderScale extends ClimbScale {
    String GradeUnit = "";
    HashMap<Integer, BoulderGrade> Grades = new HashMap<>();
}
