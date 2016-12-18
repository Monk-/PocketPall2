package com.example.aleksander.pocketpall2.RestoreAndBackup;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ExpoStrategy implements ImpoExpoStrategy
{
    @Override
    public void moveDb()
    {

        try
        {
            File direct = new File(Environment.getExternalStorageDirectory() + "/BackupFolder");

            if (!direct.exists())
            {
                if (direct.mkdir())
                {
                    //directory is created;
                }

            }
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite())
            {
                String currentDBPath = "//data//" + "com.example.user.pocketpall"
                        + "//databases//" + "BudgetDB";
                String backupDBPath = "/BackupFolder/BudgetDB";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

            }
        } catch (Exception e)
        {


        }
    }
}