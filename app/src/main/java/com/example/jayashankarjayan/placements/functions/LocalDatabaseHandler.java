package com.example.jayashankarjayan.placements.functions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jayashankar Jayan on 12/24/2017.
 */

public class  LocalDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "placements";

    // Table name
    private static final String TABLE_PERSONAL_SKILLS = "personal_skills";
    private static final String TABLE_TECHNICAL_SKILLS = "technical_skills";
    private static final String TABLE_STUDENTS = "students";

    // Table Columns names
    private static final String COLUMN_PERSONAL_SKILLS_ID = "id";
    private static final String COLUMN_PERSONAL_SKILL_NAME = "skill_name";

    private static final String COLUMN_TECHNICAL_SKILLS_ID = "id";
    private static final String COLUMN_TECHNICAL_SKILL_NAME = "skill_name";

    private  static final String COLUMN_STUDENT_ID = "id";
    private  static final String COLUMN_NAME_OF_STUDENT = "name_of_student";
    private  static final String COLUMN_USERNAME = "username";
    private  static final String COLUMN_GENDER = "gender";
    private  static final String COLUMN_EMAIL = "email";
    private  static final String COLUMN_REGISTERED_ON = "registered_id";
    private  static final String COLUMN_COLLEGE = "college";
    private  static final String COLUMN_STUDENT_IMAGE = "student_image";
    private  static final String COLUMN_ROLL_NO = "roll_no";
    private  static final String COLUMN_STUDENT_CV = "student_cv";
    private  static final String COLUMN_NUMBER_OF_KT = "number_of_kt";
    private  static final String COLUMN_CONTACT_NUMBER = "contact";
    private  static final String COLUMN_STRENGTHS= "strengths";
    private  static final String COLUMN_WEAKNESSES = "weaknesses";
    private  static final String COLUMN_ACHIEVEMENTS = "achievements";
    private  static final String COLUMN_PERSONAL_SKILLS = "personal_skills";
    private  static final String COLUMN_TECHNICAL_SKILLS = "technical_skills";


    private String CREATE_PERSONAL_SKILLS_TABLE,CREATE_TECHNICAL_SKILLS_TABLE, CREATE_STUDENTS_TABLE;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private boolean inserted = false;

    public LocalDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        CREATE_PERSONAL_SKILLS_TABLE = "CREATE TABLE "+TABLE_PERSONAL_SKILLS+" (" +
                " `"+COLUMN_PERSONAL_SKILLS_ID+"` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " `"+COLUMN_PERSONAL_SKILL_NAME+"` TEXT NOT NULL" +
                ")";

        CREATE_TECHNICAL_SKILLS_TABLE = "CREATE TABLE "+TABLE_TECHNICAL_SKILLS+" (" +
                " `"+COLUMN_TECHNICAL_SKILLS_ID+"` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " `"+COLUMN_TECHNICAL_SKILL_NAME+"` TEXT NOT NULL" +
                ")";



        CREATE_STUDENTS_TABLE = "CREATE TABLE "+TABLE_STUDENTS+" " +
                "( `"+COLUMN_STUDENT_ID+"` INTEGER NOT NULL," +
                " `"+COLUMN_NAME_OF_STUDENT+"` TEXT NOT NULL," +
                " `"+COLUMN_USERNAME+"` TEXT NOT NULL," +
                " `"+COLUMN_GENDER+"` TEXT NOT NULL," +
                " `"+COLUMN_EMAIL+"` TEXT NOT NULL," +
                " `"+COLUMN_COLLEGE+"` TEXT NOT NULL," +
                " `"+COLUMN_REGISTERED_ON+"` TEXT NOT NULL DEFAULT '0000-00-00 00:00:00'," +
                " `"+COLUMN_STUDENT_IMAGE+"` TEXT NOT NULL," +
                " `"+COLUMN_ROLL_NO+"` TEXT NOT NULL," +
                " `"+COLUMN_STUDENT_CV+"` TEXT DEFAULT NULL," +
                " `"+COLUMN_NUMBER_OF_KT+"` INTEGER DEFAULT NULL," +
                " `"+COLUMN_CONTACT_NUMBER+"` INTEGER DEFAULT NULL," +
                " `"+COLUMN_STRENGTHS+"` TEXT DEFAULT NULL," +
                " `"+COLUMN_WEAKNESSES+"` TEXT DEFAULT NULL," +
                " `"+COLUMN_ACHIEVEMENTS+"` TEXT DEFAULT NULL," +
                " `"+COLUMN_PERSONAL_SKILLS+"` TEXT DEFAULT NULL," +
                " `"+COLUMN_TECHNICAL_SKILLS+"` TEXT DEFAULT NULL," +
                " CONSTRAINT `unique_students_username` UNIQUE ("+COLUMN_USERNAME+"))";

         /*String x =    "CREATE TABLE "+TABLE_PERSONAL_SKILLS+" (" +
                    " `"+COLUMN_PERSONAL_SKILLS_ID+"` INTEGER NOT NULL AUTO_INCREMENT," +
                    " `"+COLUMN_COMPANY_NAME+"` TEXT NOT NULL," +
                    " `"+COLUMN_DATE_OF_SEELECTION+"` DATE NOT NULL," +
                    " `"+COLUMN_FOR_COURSE+"` INTEGER NOT NULL," +
                    " `"+COLUMN_MAXIMUM_BACKLOGS+"` INTEGER NOT NULL," +
                    " `"+COLUMN_VACANCIES_FOR+"` TEXT NOT NULL," +
                    " `"+COLUMN_CRITERIA_FOR_SELECTION+"` TEXT NOT NULL," +
                    " `"+COLUMN_TABLE_NAME+"` TEXT NOT NULL," +
                    " `"+COLUMN_ADDED_BY+"` TEXT NOT NULL," +
                    " `"+COLUMN_ADDED_ON+"` TEXT NOT NULL DEFAULT '0000-00-00 00:00:00'," +
                    " `"+COLUMN_COLLEGE_ID+"` INTEGER NOT NULL," +
                    " `"+COLUMN_INTERVIEW_VENUE+"` TEXT DEFAULT NULL," +
                    " `"+COLUMN_ADDITIONAL_INFORMATION+"` TEXT NOT NULL," +
                    " `"+COLUMN_FOR_COMPANY_ACCOUNT+"` INTEGER NOT NULL," +
                    " `"+COLUMN_APPROVED+"` INTEGER NOT NULL," +
                    " `"+COLUMN_CLOSED+"` INTEGER NOT NULL DEFAULT '0'," +
                    " `"+COLUMN_CANCELLED+"` INTEGER NOT NULL DEFAULT '0'," +
                    " `"+COLUMN_SELECTION_DONE+"` INTEGER DEFAULT '0'," +
                    " `"+COLUMN_APPROVED_ON+"` DATETIME DEFAULT '0000-00-00 00:00:00'," +
                    " `"+COLUMN_ADDER_TYPE+"` TEXT NOT NULL," +
                    " `"+COLUMN_APPROVED_BY+"` TEXT DEFAULT NULL," +
                    " `"+COLUMN_APPROVER_TYPE+"` TEXT DEFAULT NULL," +
                    " `"+COLUMN_SEEN+"` TEXT DEFAULT '0'," +
                    " `"+COLUMN_MIN_PERCENTAGE+"` INTEGER NOT NULL DEFAULT '45'," +
                    " `"+COLUMN_MIN_GPA_PERCENTAGE+"` INTEGER NOT NULL DEFAULT '45'," +
                    " PRIMARY KEY (`"+COLUMN_PERSONAL_SKILLS_ID+"`)," +
                    " UNIQUE KEY `uk_table_name` (`"+COLUMN_TABLE_NAME+"`)," +
                    " KEY `fk_companies_course` (`"+COLUMN_FOR_COURSE+"`)" +
                    ")";*/

        sqLiteDatabase.execSQL(CREATE_PERSONAL_SKILLS_TABLE);
        sqLiteDatabase.execSQL(CREATE_TECHNICAL_SKILLS_TABLE);
        sqLiteDatabase.execSQL(CREATE_STUDENTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL_SKILLS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TECHNICAL_SKILLS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public void createPersonalSkillsTable()
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL_SKILLS);
        sqLiteDatabase.execSQL(CREATE_PERSONAL_SKILLS_TABLE);
    }

    public void createTechnicalSkillsTable()
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TECHNICAL_SKILLS);
        sqLiteDatabase.execSQL(CREATE_TECHNICAL_SKILLS_TABLE);
    }

    public void createStudentsTable()
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        sqLiteDatabase.execSQL(CREATE_STUDENTS_TABLE);
    }

    // Adding fresh data
    public boolean insertPersonalSkillsData(LinkedHashMap<String, String> linkedHashMap) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for(Map.Entry entry : linkedHashMap.entrySet())
        {
            values.put(COLUMN_PERSONAL_SKILLS_ID,entry.getKey().toString());
            values.put(COLUMN_PERSONAL_SKILL_NAME, entry.getValue().toString()); // Skill Name
            if(sqLiteDatabase.insert(TABLE_PERSONAL_SKILLS, null, values) == -1)
            {
                inserted = false;
            }
            else
            {
                inserted = true;
            }
        }
        sqLiteDatabase.close(); // Closing database connection
        return inserted;
    }

    public boolean insertTechnicalSkillsData(LinkedHashMap<String, String> linkedHashMap) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for(Map.Entry entry : linkedHashMap.entrySet())
        {
            values.put(COLUMN_TECHNICAL_SKILLS_ID,entry.getKey().toString());
            values.put(COLUMN_TECHNICAL_SKILL_NAME, entry.getValue().toString()); // Skill Name
            if(sqLiteDatabase.insert(TABLE_TECHNICAL_SKILLS, null, values) == -1)
            {
                inserted = false;
            }
            else
            {
                inserted = true;
            }
        }
        sqLiteDatabase.close(); // Closing database connection
        return inserted;
    }

    public boolean insertStudentData(LinkedHashMap<String,String> linkedHashMap){
        sqLiteDatabase = this.getWritableDatabase();

        Functions functions = new Functions(context);
        String student_id,name,college,contact,achievements,strengths,weaknesses,studentImage,personal_skills,technical_skills,gender, email, registered_on,last_logged_on,roll_no,student_cv,number_of_kt;
        ContentValues values = new ContentValues();

        student_id = linkedHashMap.get("student_id");
        name = linkedHashMap.get("name_of_student");
        achievements = linkedHashMap.get("achievements");
        strengths = linkedHashMap.get("strengths");
        weaknesses = linkedHashMap.get("weaknesses");
        college = linkedHashMap.get("college");
        studentImage = linkedHashMap.get("studentImage");
        personal_skills = linkedHashMap.get("personal_skills");
        technical_skills = linkedHashMap.get("technical_skills");
        gender = linkedHashMap.get("gender");
        email = linkedHashMap.get("email");
        contact = linkedHashMap.get("contact");
        registered_on = linkedHashMap.get("registered_on");
        roll_no = linkedHashMap.get("roll_no");
        student_cv = linkedHashMap.get("student_cv");
        number_of_kt = linkedHashMap.get("number_of_kt");

        values.put(COLUMN_STUDENT_ID,student_id);
        values.put(COLUMN_NAME_OF_STUDENT,name);
        values.put(COLUMN_USERNAME,functions.getUsername());
        values.put(COLUMN_COLLEGE,college);
        values.put(COLUMN_ACHIEVEMENTS, achievements);
        values.put(COLUMN_CONTACT_NUMBER, contact);
        values.put(COLUMN_STRENGTHS,strengths);
        values.put(COLUMN_WEAKNESSES, weaknesses);
        values.put(COLUMN_STUDENT_IMAGE,studentImage);
        values.put(COLUMN_PERSONAL_SKILLS, personal_skills);
        values.put(COLUMN_TECHNICAL_SKILLS, technical_skills);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_REGISTERED_ON, registered_on);
        values.put(COLUMN_STUDENT_IMAGE, studentImage);
        values.put(COLUMN_STUDENT_CV, student_cv);
        values.put(COLUMN_ROLL_NO, roll_no);
        values.put(COLUMN_NUMBER_OF_KT, number_of_kt);

        inserted = sqLiteDatabase.insert(TABLE_STUDENTS, null, values) != -1;

        sqLiteDatabase.close(); // Closing database connection
        return inserted;
    }

    // Getting single company name
    public List getPersonalSkills(String skill) {

        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_PERSONAL_SKILLS, new String[] { COLUMN_PERSONAL_SKILL_NAME}, COLUMN_PERSONAL_SKILL_NAME + " like ?",
                new String[] {"%"+String.valueOf(skill)+"%"}, null, null, null, null);
        List<String> list = new ArrayList<>();

        while (cursor.moveToNext())
        {
            list.add(cursor.getString(0));
        }

        cursor.close();
        sqLiteDatabase.close();
        return list;
    }

    public List getTechnicalSkills(String skill) {


        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_TECHNICAL_SKILLS, new String[] { COLUMN_TECHNICAL_SKILL_NAME}, COLUMN_TECHNICAL_SKILL_NAME + " like ?",
                new String[] {"%"+String.valueOf(skill)+"%"}, null, null, null, null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            list.add(cursor.getString(0));
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }

    public String getStudentDetails(String columnName) {

        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_STUDENTS, new String[]{"*"}, null,
                null, null, null, null, null);
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        if(cursor.moveToFirst())
        {
            String data = cursor.getString(cursor.getColumnIndex(columnName));
            cursor.close();
            sqLiteDatabase.close();
            return data;
        }
        else
        {
            return "";
        }
    }

    String getPersonalSkillsMaxId() {

        sqLiteDatabase = this.getReadableDatabase();
        String result = "";
        String selectQuery = "SELECT  max(id) FROM " + TABLE_PERSONAL_SKILLS;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        while (cursor.moveToNext())
        {
            result = cursor.getString(0);
        }
        cursor.close();
        sqLiteDatabase.close();

        return result;
    }


    // Getting number of rows
    public long getPersonalSkillsCount() {

        sqLiteDatabase = this.getReadableDatabase();

        long count  = DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_PERSONAL_SKILLS);
        sqLiteDatabase.close();

        // return count
        return count;


    }

    public long getTechnicalSkillsCount()
    {
        sqLiteDatabase = this.getReadableDatabase();
        long count  = DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_TECHNICAL_SKILLS);
        sqLiteDatabase.close();

        // return count
        return count;
    }

    public long getStudentDataCount()
    {
        sqLiteDatabase = this.getReadableDatabase();
        long count  = DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_STUDENTS);
        sqLiteDatabase.close();
        return count;
    }

    public void dropTheDatabase()
    {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_PERSONAL_SKILLS);
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_TECHNICAL_SKILLS);
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_STUDENTS);
    }

}