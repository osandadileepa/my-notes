package com.thevirtugroup.postitnote.controller;

import com.thevirtugroup.postitnote.helper.DataHelper;
import com.thevirtugroup.postitnote.service.NoteService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@WebAppConfiguration
//@RunWith(MockitoJUnitRunner.class)
//@ContextConfiguration(classes = NoteController.class, loader = AnnotationConfigContextLoader.class)
public class NoteControllerTests {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private NoteService noteService;

    @Before
    public void setMockOutput() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /***
     * With older spring boot version I could not find a way to do Mock testing for controllers and my finding
     * suggest that we need to update the spring boot version to 1.4 or later or use EasyMock.
     *
     */

    public void getNotesFromUsernameTest() throws Exception {
        Mockito.when(this.noteService.getNotesFromUsername(DataHelper.USERNAME)).thenReturn(DataHelper.getNotes());

        mockMvc.perform(get("/api/get-notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Note 1")))
                .andExpect(jsonPath("$[0].summary", Matchers.is("This is Note One")));
    }
}
