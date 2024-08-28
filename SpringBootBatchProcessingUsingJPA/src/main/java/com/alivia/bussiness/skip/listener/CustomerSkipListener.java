package com.alivia.bussiness.skip.listener;

import org.springframework.batch.core.SkipListener;
import com.alivia.bussiness.entity.Customer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomerSkipListener implements SkipListener<Customer, Number> {

    private final String skipFilePath = "skipped_customers.csv";
    private PrintWriter writer;

    public CustomerSkipListener() {
        try {
            // Initialize PrintWriter to append to the file, adding a header if needed
            writer = new PrintWriter(new FileWriter(skipFilePath, true));
            // Write header if the file is empty
            if (new java.io.File(skipFilePath).length() == 0) {
                writer.println("ID,First Name,Last Name,Email,Error Message");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSkipInRead(Throwable t) {
        System.err.println("Error While reading " + t.getMessage());
        // Optionally, you can log the error to the CSV file
    }

    @Override
    public void onSkipInProcess(Customer item, Throwable t) {
        System.err.println("Error while processing " + item.getId() + " message " + t.getMessage());
        writeSkippedItemToFile(item, t);
    }

    @Override
    public void onSkipInWrite(Number item, Throwable t) {
        System.err.println("On write " + t.getMessage() + " number " + item);
        // Assuming item corresponds to the ID of Customer, adjust if necessary
    }

    private void writeSkippedItemToFile(Customer item, Throwable t) {
        if (writer != null) {
            writer.println(String.format("%d,%s,%s,%s,%s",
                item.getId(),
                item.getFirstName(),
                item.getLastName(),
                item.getEmail(),
                t.getMessage()
            ));
            writer.flush();  // Ensure data is written to the file
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (writer != null) {
            writer.close();  // Close the writer when done
        }
        super.finalize();
    }
}
