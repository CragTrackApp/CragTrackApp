package com.blockbasti.cragtrackapp.session;

import com.blockbasti.cragtrackapp.grades.ClimbGrade;

public class Climb {
    public String form;
    public AscensionStyle style;
    public ClimbGrade grade;

    public Climb(String form, AscensionStyle style, ClimbGrade grade) {
        this.form = form;
        this.style = style;
        this.grade = grade;
    }
}
