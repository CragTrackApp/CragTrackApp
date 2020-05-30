package com.blockbasti.cragtrackapp.session;

import java.util.ArrayList;
import java.util.Date;

public class Session {
    public String id;
    public String location;
    public Date date;
    public ArrayList<Climb> climbs = new ArrayList<>();
}
