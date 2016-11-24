package com.naveedkamran.dataapp.reader;

import java.util.List;

/**
 *
 * @author Naveed Kamran
 */
public interface LogicalRelation {

    public List<List<String>> getData(String dataSourceUri, String cellSplitter);
}
