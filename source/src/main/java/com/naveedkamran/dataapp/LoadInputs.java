package com.naveedkamran.dataapp;

import com.datenc.commons.exception.DALException;
import com.datenc.commons.persistance.DbQueryUtil;
import com.naveedkamran.dataapp.reader.LogicalRelation;
import com.naveedkamran.dataapp.reader.ReaderCsv;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Naveed Kamran
 */
public class LoadInputs {

    private static final LoadInputs instance = new LoadInputs();
    static Logger logger = LogManager.getLogger(LoadInputs.class);

    public static LoadInputs getInstance() {
        return instance;
    }

    public void loadInputs() throws DALException {
        LogicalRelation logicalRelation = new ReaderCsv();

        List<List<String>> csvData = logicalRelation.getData("C:\\home\\naveed\\work\\projects\\tub\\datainteg\\trunk\\resc\\test-data\\inputDB.csv", null, ",");

        Map<String, String> mappings = new HashMap();
        mappings.put("rcity", "City(String)");
        mappings.put("recid", "RecID(String)");
        mappings.put("rstate", "State(String)");
        mappings.put("rzip", "ZIP(String)");
        mappings.put("raddress", "Address(String)");

        //DbQueryUtil.getInstance().insert("src_table", columnNames,  csvData.subList(1, csvData.size() - 1));
        DbQueryUtil.getInstance().insert("src_table", mappings, csvData);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Initializing app");
        try {
            LoadInputs.getInstance().loadInputs();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
