package com.naveedkamran.dataapp;

import com.datenc.commons.persistance.DbConnectionUtil;
import com.datenc.commons.persistance.DbQueryUtil;
import com.naveedkamran.dataapp.reader.LogicalRelation;
import com.naveedkamran.dataapp.reader.ReaderCsv;
import java.sql.Connection;
import java.util.ArrayList;
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

            List<List<String>> csvData = logicalRelation.getData("C:\\home\\naveed\\work\\projects\\tubit\\datainteg\\trunk\\resc\\test-data\\inputDB.csv", null, ",");

            List<String> columnNames = new ArrayList();
            columnNames.add("rcity");
            columnNames.add("rstate");

            DbQueryUtil.getInstance().insert("src_table", columnNames, csvData.subList(1, csvData.size() - 1));

            Connection connection = DbConnectionUtil.getInstance().getConnection();
            List<List<String>> result = DbQueryUtil.getInstance().getData(connection, "");
        } catch (Exception ex) {
            Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
