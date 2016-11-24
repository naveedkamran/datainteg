package com.naveedkamran.dataapp;

import com.naveedkamran.dataapp.reader.LogicalRelation;
import com.naveedkamran.dataapp.reader.ReaderCsv;
import com.poginato.commons.persistance.DBUtil;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Naveed Kamran
 */
public class AppMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Initializing app");

        try {
            LogicalRelation logicalRelation = new ReaderCsv();

            List<List<String>> csvData = logicalRelation.getData("", cellSplitter);

            Connection connection = DBUtil.getInstance().getConnection();
            List<List<String>> result = DBUtil.getInstance().getData(connection, "");
        } catch (Exception ex) {
            Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
