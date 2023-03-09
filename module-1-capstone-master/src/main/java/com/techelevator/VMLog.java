package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VMLog {
    /*gets the current date and time using the LocalDateTime class,
    formats it using the DateTimeFormatter class, and stores it in
    a timeStamp string.It then opens the Log.txt file and writes
    the timeStamp and message to it using a PrintWriter. If the file
    cannot be found, a FileNotFoundException is thrown.*/
    public static void logTransactions(String message) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyy hh:mm:ss");
        String timeStamp = dateTime.format(dateTimeFormat);

        File logFile = new File("Log.txt");

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(logFile, true))) {
            writer.println(timeStamp + " " + message);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

}
