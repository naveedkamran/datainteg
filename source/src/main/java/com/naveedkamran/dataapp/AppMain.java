package com.naveedkamran.dataapp;

import com.datenc.commons.exception.DALException;
import com.datenc.commons.persistance.DbConnectionUtil;
import com.datenc.commons.persistance.DbQueryUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Naveed Kamran
 */
public class AppMain {

    private void loadData()
            throws DALException, SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Connection connection = DbConnectionUtil.getInstance().getConnection();

        //Read the source as per the format required in target
        List<List<String>> result = DbQueryUtil.getInstance().getData(connection, "select recid, upper(rcity) as rcity, upper(rstate) as rstate from src_table");

        //Clean the target (is useful if you want to re run the process.
        DbQueryUtil.getInstance().update("delete from tar_table");

        Map<String, String> mappings = new HashMap();
        mappings.put("rcity_temp", "rcity");
        mappings.put("rstate_temp", "rstate");
        mappings.put("recid", "recid");

        //DbQueryUtil.getInstance().insert("src_table", columnNames,  csvData.subList(1, csvData.size() - 1));
        DbQueryUtil.getInstance().insert("tar_table", mappings, result);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Initializing app");
        try {
            //new AppMain().loadData();

            //Clean the target (is useful if you want to re run the process.
            DbQueryUtil.getInstance().update("update tar_table set rstate = rstate_temp where length( rstate_temp ) = 2");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
