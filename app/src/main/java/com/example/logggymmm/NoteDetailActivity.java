package com.example.logggymmm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NoteDetailActivity extends AppCompatActivity
{
    private EditText tarikEditText, titleEditText, descEditText, logEditText;
    private Button deleteButton;
    private Note selectedNote;
    EditText tarikp;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        tarikp = findViewById(R.id.tarikEditText);
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();


            }
            private void updateCalendar(){
                String Format = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);
                tarikp.setText(sdf.format(calendar.getTime()));

            }

        };
        tarikp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog( NoteDetailActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        initWidgets();
        checkForEditNote();

    }

    private void initWidgets()
    {
        tarikEditText = findViewById(R.id.tarikEditText);
        titleEditText = findViewById(R.id.titleEditText);
        descEditText = findViewById(R.id.descEditText);
        logEditText = findViewById(R.id.logEditText);
        deleteButton = findViewById(R.id.deleteNoteButton);
    }

    private void checkForEditNote()
    {
        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA, -1);
        selectedNote = Note.getNoteForID(passedNoteID);

        if (selectedNote != null)
        {
            tarikEditText.setText(selectedNote.getTarik());
            titleEditText.setText(selectedNote.getTitle());
            descEditText.setText(selectedNote.getDesc());
            logEditText.setText(selectedNote.getLog());

        }
        else
        {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }



    public void saveNote(View view)
    {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String tarik = String.valueOf(tarikEditText.getText());
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descEditText.getText());
        String log = String.valueOf(logEditText.getText());


        if(TextUtils.isEmpty(tarik)){
            tarikEditText.setError("Date is required");
            return;
        }
        
        if(TextUtils.isEmpty(title)){
            titleEditText.setError("Title is required");
            return;
        }



        if(TextUtils.isEmpty(log)){
            logEditText.setError("Log is required");
            return;
        }

        if(selectedNote == null)
        {
            int id = Note.noteArrayList.size();
            Note newNote = new Note(id, tarik, title, desc, log);
            Note.noteArrayList.add(newNote);
            sqLiteManager.addNoteToDatabase(newNote);
        }
        else
        {
            selectedNote.setTarik(tarik);
            selectedNote.setTitle(title);
            selectedNote.setDesc(desc);
            selectedNote.setLog(log);

            sqLiteManager.updateNoteInDB(selectedNote);
        }

        finish();
    }





    public void deleteNote(View view)
    {

        selectedNote.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateNoteInDB(selectedNote);
        finish();
    }






}