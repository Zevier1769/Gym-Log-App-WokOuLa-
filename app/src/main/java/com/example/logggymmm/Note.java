package com.example.logggymmm;

import java.util.ArrayList;
import java.util.Date;

public class Note
{
    public static ArrayList<Note> noteArrayList = new ArrayList<>();
    public static String NOTE_EDIT_EXTRA = "noteEdit";

    private int id;
    private String tarik;
    private String title;
    private String desc;
    private String log;
    private Date deleted;

    public Note(int id, String tarik, String title, String desc, String log, Date deleted) {
        this.id = id;
        this.tarik = tarik;
        this.title = title;
        this.desc = desc;
        this.log = log;
        this.deleted = deleted;
    }

    public Note(int id, String tarik, String title, String desc, String log) {
        this.id = id;
        this.tarik = tarik;
        this.title = title;
        this.desc = desc;
        this.log = log;
        deleted = null;
    }

    public static Note getNoteForID(int passedNoteID)
    {
        for (Note note : noteArrayList)
        {
            if(note.getId() == passedNoteID)
                return note;

        }
        return null;
    }

    public static ArrayList<Note> nonDeletedNotes()
    {
      ArrayList<Note> nonDeleted = new ArrayList<>();
      for(Note note : noteArrayList)
      {
       if(note.getDeleted() == null)
           nonDeleted.add(note);
      }
      return nonDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarik() {
        return tarik;
    }

    public void setTarik(String tarik) {
        this.tarik = tarik;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
}
