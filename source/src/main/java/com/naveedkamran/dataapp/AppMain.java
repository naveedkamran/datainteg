package com.naveedkamran.dataapp;

import com.datenc.commons.exception.DALException;
import com.datenc.commons.persistance.DbConnectionUtil;
import com.datenc.commons.persistance.DbQueryUtil;
import com.datenc.commons.persistance.SqlQueryBuilderUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Naveed Kamran
 */
public class AppMain {

    private void performLoadTargetTable()
            throws DALException, SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Connection connection = DbConnectionUtil.getInstance().getConnection();

        //Read the source as per the format required in target
        List<List<String>> result = DbQueryUtil.getInstance().getData(connection, "select recid, upper(rcity) as rcity, upper(rstate) as rstate, rzip from src_table");

        //Clean the target (is useful if you want to re run the process.
        DbQueryUtil.getInstance().update("delete from tar_table");

        Map<String, String> mappings = new HashMap();
        mappings.put("rcity_temp", "rcity");
        mappings.put("recid", "recid");
        mappings.put("rstate_temp", "rstate");
        mappings.put("rzip_temp", "rzip");

        //DbQueryUtil.getInstance().insert("src_table", columnNames,  csvData.subList(1, csvData.size() - 1));
        DbQueryUtil.getInstance().insert("tar_table", mappings, result);
    }

    private void performStateNormalization()
            throws DALException, SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        //Clean the target (is useful if you want to re run the process.
        DbQueryUtil.getInstance().update("update tar_table set rstate = rstate_temp where length( rstate_temp ) = 2;");
        DbQueryUtil.getInstance().update("update tar_table set rstate = (select name_short from tstate where upper(trim(name))=rstate ) where rstate = null;");
    }

    private void performZipBasicNormalization()
            throws DALException, SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Connection connection = DbConnectionUtil.getInstance().getConnection();

        //Read the source as per the format required in target
        List<List<String>> relationData = DbQueryUtil.getInstance().getData(connection, "select id, rzip_temp from tar_table where rstate is null order by id;");

        List<String> headerRow = (List<String>) relationData.get(0);
        int colsCount = headerRow.size();

        Map<String, String> valuesMap = null;
        Map<String, String> updateMap = null;

        String rawZip = null;

        Pattern p = Pattern.compile("\\d+");
        Matcher m = null;
        Long newVal = null;

        for (List<String> dataRow : relationData) {
            newVal = null;

            valuesMap = SqlQueryBuilderUtil.getInstance().convertToMapForSqlUpdate(headerRow, dataRow);
            rawZip = valuesMap.get("rzip_temp");

            m = p.matcher(rawZip);

            if (m.find()) {
                newVal = new Long(m.group());
                if (newVal.longValue() < 1000) {
                    if (m.find()) {
                        newVal = new Long(m.group());
                    }
                }

                System.out.println("Zip code: " + newVal);
            }

            if (newVal != null) {
                updateMap = new HashMap();
                updateMap.put("rzip", newVal.toString());

                if (newVal != null) {
                    DbQueryUtil.getInstance().update("tar_table", "id", new Long(valuesMap.get("id")), updateMap);
                }
            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Initializing app");
        try {
            //LoadInputs.getInstance().loadInputs();

            AppMain app = new AppMain();
            //app.performLoadTargetTable();
            //app.performStateNormalization();

            app.performZipBasicNormalization();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
