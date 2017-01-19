package com.bitbytebitcreations.studyguide.Utils;

import java.util.Date;

/**
 * Created by JeremysMac on 1/18/17.
 */

public class Entry_Object {

    public long rowID;
    public Date entryDate;
    public String entryActivity;
    public long catID; //DIRECTORY NAME, USER CREATED FOLDERS
    public String entryName = ""; //FLASH CARD = QUESTION / SITES = NAMES / DEFINITION = WORD OR PHRASE
    public String entryContent = ""; //FLASH CARD = ANSWER / SITES = URL / DEFINITION = DEFINITION

    public void setRowID(long value){
        this.rowID = value;
    }

    //POPULATED DATE FROM SQLITE DB
    public void setEntryDate(long value){
        this.entryDate = new Date(value); //CONVERT BACK TO DATE
    }
    //POPULATED DATE FROM NEW ENTRY
    public void setEntryDate(Date value){
        this.entryDate = value;
    }

    public void setEntryActivity(String value){
        this.entryActivity = value;
    }

    public void setCatID(long value){
        this.catID = value;
    }

    public void setEntryName(String value){
        this.entryName = value;
    }

    public void setEntryContent(String value){
        this.entryContent = value;
    }
}
