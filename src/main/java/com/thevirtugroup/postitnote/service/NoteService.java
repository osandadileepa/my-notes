package com.thevirtugroup.postitnote.service;

import com.thevirtugroup.postitnote.exception.NoteNotFoundException;
import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.repository.NoteRepository;
import com.thevirtugroup.postitnote.util.GeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class NoteService {
    private static Logger LOGGER = LoggerFactory.getLogger(NoteService.class);

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Get notes for a user
     *
     * @param username
     * @return
     * @throws RuntimeException
     */
    public List<Note> getNotesFromUsername(String username) throws RuntimeException {
        List<Note> userNotes = this.noteRepository.findAllByUsername(username);

        if (userNotes != null && !userNotes.isEmpty()) {
            LOGGER.info("Found {} notes for user: {}", userNotes.size(), username);
            return userNotes;
        } else {
            throw new NoteNotFoundException("Notes not found for the username: " + username);
        }
    }

    /**
     * Save or update notes based on the details
     * @param username
     * @param note
     * @return
     */
    public Note saveOrUpdateNoteFromUsername(String username, Note note) {
        Long noteId = note.getId();
        Note savedNote = null;

        if (noteId != null) {
            LOGGER.info("Note found for user: {} ", username);
            savedNote = this.noteRepository.findByUsernameAndNoteId(username, noteId);

            if (savedNote != null) {
                savedNote.setName(note.getName());
                savedNote.setSummary(note.getSummary());
                savedNote.setDate(Calendar.getInstance().getTime());
                this.noteRepository.update(username, savedNote);
                LOGGER.info("Note details updated. Note Id: {} ", savedNote.getId());
            }
        } else {
            LOGGER.info("Note not found creating new note");
            savedNote = new Note(GeneratorUtil.generateRandomNumber(), note.getName(), note.getSummary(), Calendar.getInstance().getTime());
            this.noteRepository.save(username, savedNote);
            LOGGER.info("New note crrated. Note Id: {}", savedNote.getId());
        }

        return savedNote;
    }
}
