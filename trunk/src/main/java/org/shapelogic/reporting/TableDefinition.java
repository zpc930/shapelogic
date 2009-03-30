package org.shapelogic.reporting;

import java.util.ArrayList;
import java.util.List;
import org.shapelogic.calculation.RecursiveContext;

/** TableDefinition contains a list of ColumnDefinition. <br />
 *
 * @author Sami Badawi
 */
public class TableDefinition {

    protected List<ColumnDefinition> _columnDefinition;
    protected List<ColumnDefinition> _rawColumnDefinition;

    public TableDefinition(String[] doubleArray) {
        _rawColumnDefinition = new ArrayList<ColumnDefinition>();
        if (doubleArray == null)
            return;
        for (int i = 0; i < doubleArray.length/2; i++) {
            String streamName = doubleArray[i*2];
            String columnName = doubleArray[i*2 + 1];
            if (columnName == null)
                columnName = streamName;
            ColumnDefinition columnDefinition = new ColumnDefinition(streamName, columnName);
            _rawColumnDefinition.add(columnDefinition);
        }
    }

    public List<ColumnDefinition> getColumnDefinition() {
        return _columnDefinition;
    }

    public List<ColumnDefinition> getRawColumnDefinition() {
        return _rawColumnDefinition;
    }

    public void findNonEmptyColumns(RecursiveContext recursiveContext) {
        _columnDefinition = new ArrayList<ColumnDefinition>();
        if (recursiveContext == null)
            return;
        for (ColumnDefinition columnDefinition : _rawColumnDefinition) {
            if (columnDefinition.findStream(recursiveContext))
                _columnDefinition.add(columnDefinition);
        }
    }
}
