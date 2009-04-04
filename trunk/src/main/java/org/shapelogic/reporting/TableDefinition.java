package org.shapelogic.reporting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.streams.CalcNumberedStream1;
import org.shapelogic.streams.NumberedStream;

/** TableDefinition contains a list of ColumnDefinition. <br />
 *
 * @author Sami Badawi
 */
public class TableDefinition {

    protected List<ColumnDefinition> _columnDefinition;
    protected List<ColumnDefinition> _rawColumnDefinition;

    public TableDefinition(String[] doubleArray) {
        this(Arrays.asList(doubleArray));
    }

    public TableDefinition(List doubleList) {
        _rawColumnDefinition = new ArrayList<ColumnDefinition>();
        addDefinition(doubleList);
    }

    public void addDefinition(List doubleList) {
        if (doubleList == null)
            return;
        for (int i = 0; i < doubleList.size()/2; i++) {
            Object streamObject = doubleList.get(i*2);
            Object columnObject = doubleList.get(i*2 + 1);
            String streamName = null;
            NumberedStream stream = null;
            String columnName = null;
            if (streamObject == null)
                throw new RuntimeException("Missing stream for element: " + i);
            if (streamObject instanceof String)
                streamName = (String) streamObject;
            else if (streamObject instanceof NumberedStream)
                stream = (NumberedStream) streamObject;
            else
                throw new RuntimeException("Bad type for element: " + i +
                        " class: " + streamObject.getClass().getCanonicalName());
            if (columnObject == null)
                columnName = streamName;
            else if (columnObject instanceof String)
                columnName = (String) columnObject;
            ColumnDefinition columnDefinition = null;
            if (streamName != null)
                columnDefinition = new ColumnDefinition(streamName, columnName);
            else
                columnDefinition = new ColumnDefinition(stream, columnName);
            _rawColumnDefinition.add(columnDefinition);
        }
    }

    /** Create a stream based on another stream and a calc.<br />
     *
     * @param baseStream
     * @param calc
     * @param columnName
     */
    public void addClosureDefinition(NumberedStream baseStream, Calc1 calc, String columnName) {
        CalcNumberedStream1 stream = new CalcNumberedStream1(calc, baseStream);
        ColumnDefinition columnDefinition = new ColumnDefinition(stream, columnName);
        _rawColumnDefinition.add(columnDefinition);
    }

    public List<ColumnDefinition> getColumnDefinition() {
        return _columnDefinition;
    }

    public List<ColumnDefinition> getRawColumnDefinition() {
        return _rawColumnDefinition;
    }

    public void findNonEmptyColumns(RecursiveContext recursiveContext) {
        _columnDefinition = new ArrayList<ColumnDefinition>();
        for (ColumnDefinition columnDefinition : _rawColumnDefinition) {
            if (columnDefinition.findStream(recursiveContext))
                _columnDefinition.add(columnDefinition);
        }
    }
}
