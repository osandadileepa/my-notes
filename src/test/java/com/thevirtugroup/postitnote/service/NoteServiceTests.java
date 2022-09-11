package com.thevirtugroup.postitnote.service;

import com.thevirtugroup.postitnote.helper.DataHelper;
import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.repository.NoteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;

import java.text.ParseException;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(NoteService.class)
public class NoteServiceTests {
    @Mock
    private NoteRepository noteRepository;
    @InjectMocks
    private NoteService noteService;

    @Test
    public void getNotesFromUsernameTest() throws ParseException {
        when(this.noteRepository.findAllByUsername(DataHelper.USERNAME)).thenReturn(DataHelper.getNotes());

        List<Note> userNotes = this.noteService.getNotesFromUsername(DataHelper.USERNAME);

        Assert.assertEquals(3, userNotes.size());

        verify(this.noteRepository, times(1)).findAllByUsername(DataHelper.USERNAME);
    }

    @Test
    public void saveNoteTest() throws ParseException {
        when(this.noteRepository.save(DataHelper.USERNAME, DataHelper.savedNote())).thenReturn(DataHelper.savedNote());

        Note userNotes = this.noteService.saveOrUpdateNoteFromUsername(DataHelper.USERNAME, DataHelper.saveNote());

        Assert.assertNotNull(userNotes);
        Assert.assertNotNull(userNotes.getId());
        Assert.assertNull(DataHelper.saveNote().getId());
        Assert.assertEquals(userNotes.getName(), DataHelper.savedNote().getName());
        Assert.assertEquals(userNotes.getSummary(), DataHelper.savedNote().getSummary());
    }

    @Test
    public void updateNoteTest() throws ParseException {
        when(this.noteRepository.findByUsernameAndNoteId(DataHelper.USERNAME, DataHelper.updateNote().getId())).thenReturn(DataHelper.updateOriginalNote());
        when(this.noteRepository.update(DataHelper.USERNAME, DataHelper.updatedNote())).thenReturn(DataHelper.updatedNote());

        Note userNotes = this.noteService.saveOrUpdateNoteFromUsername(DataHelper.USERNAME, DataHelper.updateNote());

        Assert.assertNotNull(userNotes);
        Assert.assertEquals(userNotes.getName(), DataHelper.updatedNote().getName());
        Assert.assertEquals(userNotes.getSummary(), DataHelper.updatedNote().getSummary());
    }

}
