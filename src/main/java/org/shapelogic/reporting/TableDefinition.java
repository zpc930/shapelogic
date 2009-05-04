package org.shapelogic.reporting;

import java.util.ArrayList;
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
    protected List<ColumnDefinition> _sortedColumnDefinition;

    public TableDefinition(List doubleList) {
        _rawColumnDefinition = new ArrayList<ColumnDefinition>();
        addDefinition(doubleList);
    }

    public void addDefinition(Object streamObject, String columnName) {
        String streamName = null;
        NumberedStream stream = null;
        if (streamObject == null)
            throw new RuntimeException("Missing stream for column name: " + columnName);
        if (streamObject instanceof String)
            streamName = (String) streamObject;
        else if (streamObject instanceof NumberedStream)
            stream = (NumberedStream) streamObject;
        else
            throw new RuntimeException("Bad type column name: " + columnName +
                    " class: " + streamObject.getClass().getCanonicalName());
        if (columnName == null)
            columnName = streamName;
        ColumnDefinition columnDefinition = null;
        if (streamName != null)
            columnDefinition = new ColumnDefinition(streamName, columnName);
        else
            columnDefinition = new ColumnDefinition(stream, columnName);
        _rawColumnDefinition.add(columnDefinition);
    }

    public ColumnDefinition makeColumnDefinition(String streamName, RecursiveContext recursiveContext) {
        ColumnDefinition columnDefinition = null;
        if (streamName == null)
            throw new RuntimeException("Missing stream for stream name = null");
        columnDefinition = new ColumnDefinition(streamName, streamName);
        if (!columnDefinition.findStream(recursiveContext))
            throw new RuntimeException("Missing stream for stream name: " + streamName);
        _rawColumnDefinition.add(columnDefinition);
        return columnDefinition;
    }

    public void addDefinition(List doubleList) {
        if (doubleList == null)
            return;
        for (int i = 0; i < doubleList.size()/2; i++) {
            Object streamObject = doubleList.get(i*2);
            Object columnObject = doubleList.get(i*2 + 1);
            addDefinition(streamObject, (String) columnObject);
        }
    }

    /** Create a stream based on another stream and a calc.<br />
     *
     * @param baseStream
     * @param calc
     * @param columnName
     */
    public NumberedStream addClosureDefinition(NumberedStream baseStream, Calc1 calc, String columnName) {
        CalcNumberedStream1 stream = new CalcNumberedStream1(calc, baseStream);
        ColumnDefinition columnDefinition = new ColumnDefinition(stream, columnName);
        _rawColumnDefinition.add(columnDefinition);
        return stream;
    }

    public List<ColumnDefinition> getColumnDefinition() {
    	if (_sortedColumnDefinition != null)
    		return _sortedColumnDefinition;
    	else
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
    
    /** Give a print list and sort accordingly.<br />
     * 
     * @param sortOrderList
     * @param recursiveContext
     */
    public void sort(List<String> sortOrderList, RecursiveContext recursiveContext) {
    	_sortedColumnDefinition = new ArrayList<ColumnDefinition>();
    	for (String columnName: sortOrderList) {
    		ColumnDefinition foundColumnDef = null;
    		//Try 1 find in stream name
    		for (ColumnDefinition columnDef: _columnDefinition) {
    			String streamName = columnDef.getStreamName();
    			if (streamName != null && streamName.equalsIgnoreCase(columnName)) {
    				foundColumnDef = columnDef;
    			}
    		}
    		//Try 2 find in column name
    		if (foundColumnDef == null) {
	    		for (ColumnDefinition columnDef: _columnDefinition) {
	    			if (columnDef.getColumnName().equalsIgnoreCase(columnName)) {
	    				foundColumnDef = columnDef;
	    			}
	    		}
    		}
    		//Try 3 look up in context
    		if (foundColumnDef == null) {
    			foundColumnDef = makeColumnDefinition(columnName,recursiveContext);
    		}
    		_sortedColumnDefinition.add(foundColumnDef);
    	}
    }
}
