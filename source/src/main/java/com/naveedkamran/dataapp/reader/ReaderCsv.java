package com.naveedkamran.dataapp.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Naveed Kamran
 */
public class ReaderCsv implements LogicalRelation {

    public List<List<String>> getData(String dataSourceUri, String rowSplitter, String cellSplitter) {
        List<List<String>> dataRelation = new ArrayList();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dataSourceUri));
            String line = br.readLine();
            while (line != null) {
                List<String> row = new ArrayList();
                String[] dataRow = line.split(cellSplitter);
                for (String dataCell : dataRow) {
                    row.add(dataCell);
                }
                dataRelation.add(row);
            }
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(ReaderCsv.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ReaderCsv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dataRelation;
    }
}
