package com.dawngregg.ucd.roostspaintballwishlist;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FileReadWrite {
    private static final String TAG = "FileReadWrite";

    // Write to a File
    void writeFile(String JSONData, Activity curActivity, String fileName)
    {
        try
        {
            // Open file for output - throws FileNotFoundException
            FileOutputStream ostream = curActivity.openFileOutput(fileName, Context.MODE_PRIVATE);

            PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(ostream)));

            // Print JSON data string to file
            pw.println(JSONData);

            // Close output file
            pw.close();
        }
        catch (FileNotFoundException e)
        {
            Log.e(TAG, "FileNotFoundException");
        }
    }

    // Read from a file
    String readFile(Activity curActivity, String fileName)
    {
        String line = "";

        StringBuffer data = new StringBuffer("");

        try
        {
            // Open file for input - throws FileNotFoundException
            FileInputStream instream = curActivity.openFileInput(fileName);

            BufferedReader br = new BufferedReader(new InputStreamReader(instream));

            while (null != (line = br.readLine()))
            {
                data.append(line);
            }

            // Close input file
            br.close();
        }
        catch (FileNotFoundException e)
        {
            Log.e(TAG, "FileNotFoundException");
        }
        catch (IOException e)
        {
            Log.e(TAG, "IOException");
        }

        return data.toString();
    }
}
