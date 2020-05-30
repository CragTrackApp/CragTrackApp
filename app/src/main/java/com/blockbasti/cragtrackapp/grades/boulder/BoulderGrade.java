package com.blockbasti.cragtrackapp.grades.boulder;

import com.blockbasti.cragtrackapp.grades.ClimbGrade;

public class BoulderGrade implements ClimbGrade {
    public int score;
    public String gradeString;

    public BoulderGrade(int score, String gradeString) {
        this.score = score;
        this.gradeString = gradeString;
    }
}
