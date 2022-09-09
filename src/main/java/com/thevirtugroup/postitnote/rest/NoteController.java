package com.thevirtugroup.postitnote.rest;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Crud operations related to Notes
 */
@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;

    /***
     * Get all the notes from username
     *
     * @param username
     * @return ResponseEntity<List <Note>>
     */
    @RequestMapping(method = RequestMethod.GET, value = "api/get-notes")
    public ResponseEntity<List<Note>> getAvailableNotes(@RequestParam(name = "username") String username) {
        List<Note> notes = noteService.getNotesFromUsername(username);
        return ResponseEntity.ok(notes);
    }

    /***
     * Save or Update notes for a user
     *
     * @param note
     * @param username
     * @return ResponseEntity<Note>
     */
    @RequestMapping(method = RequestMethod.POST, value = "api/save")
    public ResponseEntity<Note> getAvailableNotes(@RequestBody Note note, @RequestParam(name = "username") String username) {
        Note savedNote = noteService.saveOrUpdateNoteFromUsername(username, note);
        return ResponseEntity.ok(savedNote);
    }
}
