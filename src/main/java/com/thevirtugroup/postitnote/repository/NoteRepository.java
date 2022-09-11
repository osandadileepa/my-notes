package com.thevirtugroup.postitnote.repository;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.util.GeneratorUtil;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class NoteRepository {
    private static Map<String, List<Note>> NOTE_DATA = new HashMap<>();

    public NoteRepository() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Note> userNotes = Arrays.asList(
                new Note(GeneratorUtil.generateRandomNumber(), "Note 1", "This is Note One", sdf.parse("01/08/2022")),
                new Note(GeneratorUtil.generateRandomNumber(), "Note 2", "This is Note Tow", sdf.parse("02/08/2022")),
                new Note(GeneratorUtil.generateRandomNumber(), "Note 3", "This is Note Three", sdf.parse("03/08/2022")),
                new Note(GeneratorUtil.generateRandomNumber(), "Note 4", "This is Note Four", sdf.parse("04/08/2022")),
                new Note(GeneratorUtil.generateRandomNumber(), "Note 5", "This is Note Five", sdf.parse("05/08/2022")));
        NOTE_DATA.put("user", userNotes);
    }

    public List<Note> findAllByUsername(String username) {
        if (!NOTE_DATA.isEmpty()) {
            return NOTE_DATA.get(username);
        }
        return null;
    }

    public Note findByUsernameAndNoteId(String username, long id) {
        if (!NOTE_DATA.isEmpty()) {
            List<Note> userNotes = NOTE_DATA.get(username);
            if (userNotes != null && !userNotes.isEmpty()) {
                return userNotes.stream().filter(note -> note.getId() == id).collect(Collectors.toList()).get(0);
            }
            return null;
        }
        return null;
    }

    public Note update(String username, Note note) {
        if (!NOTE_DATA.isEmpty()) {
            List<Note> userNotes = NOTE_DATA.get(username);
            if (userNotes != null && !userNotes.isEmpty()) {
                List<Note> unchangedNotes = userNotes.stream().filter(nt -> nt.getId() != note.getId()).collect(Collectors.toList());
                unchangedNotes.add(note);
                NOTE_DATA.put(username, unchangedNotes);
                return note;
            }
        }
        return null;
    }

    public Note save(String username, Note note) {
        List<Note> userNotes = NOTE_DATA.get(username);
        if (userNotes == null) {
            userNotes = new ArrayList<>();
        }

        if (!userNotes.isEmpty()) {
            List<Note> newList = new ArrayList<>(userNotes);
            newList.add(note);
            NOTE_DATA.put(username, newList);
        } else {
            userNotes.add(note);
            NOTE_DATA.put(username, userNotes);
        }
        return note;
    }
}
