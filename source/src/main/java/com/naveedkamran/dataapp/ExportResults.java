package com.naveedkamran.dataapp;

import com.datenc.commons.exception.DALException;
import com.datenc.commons.persistance.DbConnectionUtil;
import com.datenc.commons.persistance.DbQueryUtil;
import com.datenc.commons.transform.TransformUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Naveed Kamran
 */
public class ExportResults {

    private static final ExportResults instance = new ExportResults();
    static Logger logger = LogManager.getLogger(ExportResults.class);

    public static ExportResults getInstance() {
        return instance;
    }

    public void exportResults() throws DALException {
        try {
            Connection connection = DbConnectionUtil.getInstance().getConnection();

            //Read the source as per the format required in target
            List<List<String>> result = DbQueryUtil.getInstance().getData(connection, "select recid, raddress, rcity, rstate, rzip from tar_table");
            DbConnectionUtil.getInstance().closeConnection(connection);

            FileUtils.writeStringToFile(new File("C:\\home\\naveed\\work\\projects\\tub\\datainteg\\trunk\\source\\file-repo\\output.csv"),
                    TransformUtil.getInstance().toCsv(result, '\n', ','));

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ExportResults.class.getName()).log(Level.SEVERE, null, ex);
            throw new DALException(ex);
        }
    }

    private void test() throws IOException {
        String[] processArgs = new String[]{"C:\\Program Files\\Java\\jdk1.8.0_91\\bin\\java.exe", "-jar C:\\home\\naveed\\work\\projects\\tub\\datainteg\\trunk\\source\\lib\\CleaningEvaluator.jar C:\\home\\naveed\\work\\projects\\tub\\datainteg\\trunk\\source\\file-repo\\csv.csv"};
        Process process = new ProcessBuilder(processArgs).start();

//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    //I'am using Win7 with PL encoding in console -> "CP852"
//                    process.getInputStream(), "CP852"));
//
//            String line;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }

        input.close();

        System.out.println("process ended");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Initializing app");
        try {
            ExportResults.getInstance().exportResults();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
