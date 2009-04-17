package org.shapelogic.reporting;

import ij.measure.ResultsTable;
import org.shapelogic.util.Headings;

/** ImageJ ResultTable table builder. <br />
 *
 * @author Sami Badawi
 */
public class IJTableBuilder extends BaseTableBuilder {
    protected ResultsTable _resultsTable;

    public IJTableBuilder(TableDefinition tableDefinition, ResultsTable rt) {
        super(tableDefinition);
        _resultsTable = rt;
    }

    @Override
    public void buildLineElement(int column, int line) {
        ColumnDefinition columnDefinition = _tableDefinition.getColumnDefinition().get(column);
        Object obj = columnDefinition.getStream().get(line);
        if (obj == null)
            return;
        if (obj instanceof Number) {
        Number number = (Number)obj;
        _resultsTable.addValue(columnDefinition.getColumnName(), number.doubleValue());
        }
        else if (obj instanceof String && column == 0) {
            String category = (String )obj;
            if (category != null && !"".equals(category.trim()))
                _resultsTable.addLabel(Headings.CATEGORY, category);
            else
                _resultsTable.addLabel(Headings.CATEGORY, "NA");
        }
    }

    @Override
    public void buildLine(int line) {
        _resultsTable.incrementCounter();
        super.buildLine(line);
    }

    @Override
    public void buildHeadlineElement(int column) {
        if (column != 0) //First column is a the cagegorizer lable, maybe also test heading
            _resultsTable.getFreeColumn(_tableDefinition.getColumnDefinition().get(column).getColumnName());
    }
}
