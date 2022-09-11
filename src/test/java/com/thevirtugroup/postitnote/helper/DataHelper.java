package com.thevirtugroup.postitnote.helper;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.util.GeneratorUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DataHelper {

    public static String USERNAME = "user";
    private static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    private DataHelper() {
    }

    public static List<Note> getNotes() throws ParseException {

        return Arrays.asList(
                new Note(GeneratorUtil.generateRandomNumber(), "Note 1", "This is Note One", SDF.parse("01/08/2022")),
                new Note(GeneratorUtil.generateRandomNumber(), "Note 2", "This is Note Tow", SDF.parse("02/08/2022")),
                new Note(GeneratorUtil.generateRandomNumber(), "Note 3", "This is Note Three", SDF.parse("03/08/2022")));
    }

    public static Note saveNote() {
        return new Note(null, "Note 1 Save", "This is Note One for save", null);
    }

    public static Note savedNote() throws ParseException {
        return new Note(114555522l, "Note 1 Save", "This is Note One for save", SDF.parse("01/08/2022"));
    }

    public static Note updateNote() {
        return new Note(111554587l, "Note 1 Update", "This is Note One for update", null);
    }

    public static Note updatedNote() throws ParseException {
        return new Note(111554587l, "Note 1 Update", "This is Note One for update", SDF.parse("01/08/2022"));
    }

    public static Note updateOriginalNote() throws ParseException {
        return new Note(111554587l, "Note 1", "This is Note", SDF.parse("01/08/2022"));
    }
}
