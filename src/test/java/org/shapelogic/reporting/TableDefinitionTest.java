package org.shapelogic.reporting;

import junit.framework.TestCase;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.calculation.SimpleRecursiveContext;
import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.streams.NumberedStream;

/** Test of TableDefinition.
 *
 * @author Sami Badawi
 */
public class TableDefinitionTest extends TestCase {
    public static final String streamName0 = "streamName0";
    public static final String streamName1 = "streamName1";
    public static final String streamName2 = "streamName2";
    public static final String columnName0 = "columnName0";

    public void testOneColumnNoContext() {
        String[] inputArray = {"streamName", "columnName"};
        TableDefinition tableDefinition = new TableDefinition(inputArray);
        assertNotNull(tableDefinition);
        ColumnDefinition columnDefinition = tableDefinition.getRawColumnDefinition().get(0);
        assertNotNull(columnDefinition);
        assertNull(tableDefinition.getColumnDefinition());
        tableDefinition.findNonEmptyColumns(null);
        assertNotNull(tableDefinition.getColumnDefinition());
    }

    static public TableDefinition makeTableDefinition(RecursiveContext recursiveContext) {
        String[] inputArray = {
            streamName0, columnName0,
            streamName1, "columnName2",
            streamName2, null,
        };
        NumberedStream<Integer> naturalNumberStream0 = new NaturalNumberStream(2);
        recursiveContext.getContext().put(streamName0, naturalNumberStream0);
        NumberedStream<Integer> naturalNumberStream2 = new NaturalNumberStream(2);
        recursiveContext.getContext().put(streamName2, naturalNumberStream2);
        TableDefinition tableDefinition = new TableDefinition(inputArray);
        return tableDefinition;
    }

    public void testOneColumnWithContext() {
        RecursiveContext recursiveContext = new SimpleRecursiveContext(null);
        TableDefinition tableDefinition = makeTableDefinition(recursiveContext);
        assertNotNull(tableDefinition);
        assertEquals(3, tableDefinition.getRawColumnDefinition().size());
        tableDefinition.findNonEmptyColumns(recursiveContext);
        assertEquals(2, tableDefinition.getColumnDefinition().size());

        ColumnDefinition columnDefinition0 = tableDefinition.getColumnDefinition().get(0);
        assertNotNull(columnDefinition0);
        assertEquals(streamName0, columnDefinition0.getStreamName());
        assertEquals(columnName0, columnDefinition0.getColumnName());
        ColumnDefinition columnDefinition1 = tableDefinition.getColumnDefinition().get(1);
        assertNotNull(columnDefinition1);
        assertEquals(streamName2, columnDefinition1.getStreamName());
        assertEquals(streamName2, columnDefinition1.getColumnName());
    }

}
