package org.shapelogic.reporting;

import junit.framework.TestCase;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.calculation.SimpleRecursiveContext;

/** BaseTableBuilder is the Builder design pattern.<br />
 *
 * For convenience the Director is combined with the Builder.
 *
 * @author Sami Badawi
 */
public class BaseTableBuilderTest extends TestCase {

    public void testOneColumnWithContext() {
        RecursiveContext recursiveContext = new SimpleRecursiveContext(null);
        TableDefinition tableDefinition = TableDefinitionTest.makeTableDefinition(recursiveContext);
        assertNotNull(tableDefinition);
        assertEquals(3, tableDefinition.getRawColumnDefinition().size());
        tableDefinition.findNonEmptyColumns(recursiveContext);
        assertEquals(2, tableDefinition.getColumnDefinition().size());

        ColumnDefinition columnDefinition0 = tableDefinition.getColumnDefinition().get(0);
        assertNotNull(columnDefinition0);
        assertEquals(TableDefinitionTest.streamName0, columnDefinition0.getStreamName());
        assertEquals(TableDefinitionTest.columnName0, columnDefinition0.getColumnName());
        ColumnDefinition columnDefinition1 = tableDefinition.getColumnDefinition().get(1);
        assertNotNull(columnDefinition1);
        assertEquals(TableDefinitionTest.streamName2, columnDefinition1.getStreamName());
        assertEquals(TableDefinitionTest.streamName2, columnDefinition1.getColumnName());
    }
}
