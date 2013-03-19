package com.gradiance;

import java.util.*;
import java.io.*;
import java.text.*;
import java.sql.*;

class Answer {

    public int ansid;
    public String anscontent;
    public String shortexp;

    Answer(int ansid,String anscontent, String shortexp)
    {
        this.ansid = ansid;
        this.anscontent = anscontent;
        this.shortexp = shortexp;
    }

}
