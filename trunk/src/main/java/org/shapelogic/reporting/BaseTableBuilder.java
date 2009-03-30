package org.shapelogic.reporting;

import java.util.ArrayList;
import java.util.List;

/** BaseTableBuilder is the Builder design pattern.<br />
 *
 * For convenience the Director is combined with the Builder.
 *
 * @author Sami Badawi
 */
public class BaseTableBuilder {

    protected List _outputList;
    protected TableDefinition _tableDefinition;

    public BaseTableBuilder(TableDefinition tableDefinition) {
        this(tableDefinition, false);
    }
    
    public BaseTableBuilder(TableDefinition tableDefinition, boolean createOutputList) {
        _tableDefinition = tableDefinition;
        if (createOutputList)
            makeOutputList();
    }

    public void buildHeadlineElement(int column) {
        _outputList.set(column, _tableDefinition.getColumnDefinition().get(column).getColumnName());
    }

    public void buildHeadline() {
        for (int i = 0; i < _tableDefinition.getColumnDefinition().size(); i++) {
            buildHeadlineElement(i);
        }
    }

    public void buildLineElement(int column, int line) {
        Object element = _tableDefinition.getColumnDefinition().get(column).getStream().get(line);
        int index = (line + 1) * getColumns() + column;
        _outputList.add(index, element);
    }

    /** Write out next lime. */
    public void buildLine(int line) {
        for (int column = 0; column < getColumns(); column++) {
            buildLineElement(column, line);
        }
    }

    /** This should have been done by a director. */
    public void buildTable(int startLine, int endLine) {
        buildHeadline();
        for (int i = startLine; i < endLine; i++) //Maybe start with 1
            buildLine(i);
    }

    /** For testing purpose you can write the table out to an OutputList. */
    public void makeOutputList() {
        _outputList = new ArrayList();
    }

    public List getOutputList() {
        return _outputList;
    }

    public int getColumns() {
        return _tableDefinition.getColumnDefinition().size();
    }
}
