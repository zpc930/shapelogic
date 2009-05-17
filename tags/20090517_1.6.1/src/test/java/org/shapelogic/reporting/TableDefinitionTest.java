package org.shapelogic.reporting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.calculation.SimpleRecursiveContext;
import org.shapelogic.mathematics.NaturalNumberStream;
import org.shapelogic.streams.NumberedStream;

/** Test of TableDefinition.
 *
 * @author Sami Badawi
 */
public class TableDefinitionTest extends TestCase {
    public static final String STREAM_NAME0 = "streamName0";
    public static final String STREAM_NAME1 = "streamName1";
    public static final String STREAM_NAME2 = "streamName2";
    public static final String STREAM_NAME3 = "streamName3";
    public static final String COLUMN_NAME0 = "columnName0";
    public static final String COLUMN_NAME1 = "columnName1";

    public void testOneColumnNoContext() {
        String[] inputArray = {"streamName", "columnName"};
        TableDefinition tableDefinition = new TableDefinition(Arrays.asList(inputArray));
        assertNotNull(tableDefinition);
        ColumnDefinition columnDefinition = tableDefinition.getRawColumnDefinition().get(0);
        assertNotNull(columnDefinition);
        assertNull(tableDefinition.getColumnDefinition());
        tableDefinition.findNonEmptyColumns(null);
        assertNotNull(tableDefinition.getColumnDefinition());
    }

    static public TableDefinition makeTableDefinition(RecursiveContext recursiveContext) {
        String[] inputArray = {
            STREAM_NAME0, COLUMN_NAME0,
            STREAM_NAME1, COLUMN_NAME1,
            STREAM_NAME2, null,
        };
        NumberedStream<Integer> naturalNumberStream0 = new NaturalNumberStream(2);
        recursiveContext.getContext().put(STREAM_NAME0, naturalNumberStream0);
        NumberedStream<Integer> naturalNumberStream2 = new NaturalNumberStream(3);
        recursiveContext.getContext().put(STREAM_NAME2, naturalNumberStream2);
        TableDefinition tableDefinition = new TableDefinition(Arrays.asList(inputArray));
        return tableDefinition;
    }

    static public TableDefinition makeTableDefinitionDirectStream(RecursiveContext recursiveContext) {
        String[] inputArray = {
            STREAM_NAME0, COLUMN_NAME0,
            STREAM_NAME1, COLUMN_NAME1,
        };
        List inputList = new ArrayList();
        inputList.addAll(Arrays.asList(inputArray));
        NumberedStream<Integer> naturalNumberStream0 = new NaturalNumberStream(2);
        recursiveContext.getContext().put(STREAM_NAME0, naturalNumberStream0);
        NumberedStream<Integer> naturalNumberStream2 = new NaturalNumberStream(3);
        inputList.add(naturalNumberStream2);
        inputList.add(STREAM_NAME2);
        TableDefinition tableDefinition = new TableDefinition(inputList);
        return tableDefinition;
    }

    static public TableDefinition makeTableDefinitionDirectCalc(RecursiveContext recursiveContext) {
        String[] inputArray = {
            STREAM_NAME0, COLUMN_NAME0,
            STREAM_NAME1, "columnName2",
        };
        NumberedStream<Integer> naturalNumberStream0 = new NaturalNumberStream(2);
        recursiveContext.getContext().put(STREAM_NAME0, naturalNumberStream0);
        TableDefinition tableDefinition = new TableDefinition(Arrays.asList(inputArray));
        Calc1 identity = new Calc1<Integer,Integer>() {
            public Integer invoke(Integer input) {
                return input;
            }
        };
        tableDefinition.addClosureDefinition(naturalNumberStream0, identity, STREAM_NAME2);
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
        assertEquals(STREAM_NAME0, columnDefinition0.getStreamName());
        assertEquals(COLUMN_NAME0, columnDefinition0.getColumnName());
        ColumnDefinition columnDefinition1 = tableDefinition.getColumnDefinition().get(1);
        assertNotNull(columnDefinition1);
        assertEquals(STREAM_NAME2, columnDefinition1.getStreamName());
        assertEquals(STREAM_NAME2, columnDefinition1.getColumnName());
    }

    public void testOneColumnWithContextDirectStream() {
        RecursiveContext recursiveContext = new SimpleRecursiveContext(null);
        TableDefinition tableDefinition = makeTableDefinitionDirectStream(recursiveContext);
        assertNotNull(tableDefinition);
        assertEquals(3, tableDefinition.getRawColumnDefinition().size());
        tableDefinition.findNonEmptyColumns(recursiveContext);
        assertEquals(2, tableDefinition.getColumnDefinition().size());

        ColumnDefinition columnDefinition0 = tableDefinition.getColumnDefinition().get(0);
        assertNotNull(columnDefinition0);
        assertEquals(STREAM_NAME0, columnDefinition0.getStreamName());
        assertEquals(COLUMN_NAME0, columnDefinition0.getColumnName());
        ColumnDefinition columnDefinition1 = tableDefinition.getColumnDefinition().get(1);
        assertNotNull(columnDefinition1);
        assertEquals(null, columnDefinition1.getStreamName());
        assertEquals(STREAM_NAME2, columnDefinition1.getColumnName());
    }

    public void testOneColumnWithContextDirectStreamSortedSameOrder() {
        RecursiveContext recursiveContext = new SimpleRecursiveContext(null);
        TableDefinition tableDefinition = makeTableDefinitionDirectStream(recursiveContext);
        assertNotNull(tableDefinition);
        assertEquals(3, tableDefinition.getRawColumnDefinition().size());
        tableDefinition.findNonEmptyColumns(recursiveContext);
        String[] sortOrder = {STREAM_NAME0, STREAM_NAME2}; 
        tableDefinition.sort(Arrays.asList(sortOrder), recursiveContext);
        assertEquals(2, tableDefinition.getColumnDefinition().size());

        ColumnDefinition columnDefinition0 = tableDefinition.getColumnDefinition().get(0);
        assertNotNull(columnDefinition0);
        assertEquals(STREAM_NAME0, columnDefinition0.getStreamName());
        assertEquals(COLUMN_NAME0, columnDefinition0.getColumnName());
        ColumnDefinition columnDefinition1 = tableDefinition.getColumnDefinition().get(1);
        assertNotNull(columnDefinition1);
        assertEquals(null, columnDefinition1.getStreamName());
        assertEquals(STREAM_NAME2, columnDefinition1.getColumnName());
    }

    public void testOneColumnWithContextDirectStreamSortedReverseOrder() {
        RecursiveContext recursiveContext = new SimpleRecursiveContext(null);
        TableDefinition tableDefinition = makeTableDefinitionDirectStream(recursiveContext);
        assertNotNull(tableDefinition);
        assertEquals(3, tableDefinition.getRawColumnDefinition().size());
        tableDefinition.findNonEmptyColumns(recursiveContext);
        String[] sortOrder = {STREAM_NAME2, STREAM_NAME0}; 
        tableDefinition.sort(Arrays.asList(sortOrder), recursiveContext);
        assertEquals(2, tableDefinition.getColumnDefinition().size());

        ColumnDefinition columnDefinition0 = tableDefinition.getColumnDefinition().get(0);
        assertNotNull(columnDefinition0);
        assertEquals(null, columnDefinition0.getStreamName());
        assertEquals(STREAM_NAME2, columnDefinition0.getColumnName());
        ColumnDefinition columnDefinition1 = tableDefinition.getColumnDefinition().get(1);
        assertNotNull(columnDefinition1);
        assertEquals(STREAM_NAME0, columnDefinition1.getStreamName());
        assertEquals(COLUMN_NAME0, columnDefinition1.getColumnName());
    }

    public void testOneColumnWithContextDirectStreamSortedSelelectOne() {
        RecursiveContext recursiveContext = new SimpleRecursiveContext(null);
        TableDefinition tableDefinition = makeTableDefinitionDirectStream(recursiveContext);
        assertNotNull(tableDefinition);
        assertEquals(3, tableDefinition.getRawColumnDefinition().size());
        tableDefinition.findNonEmptyColumns(recursiveContext);
        String[] sortOrder = {STREAM_NAME0}; 
        tableDefinition.sort(Arrays.asList(sortOrder), recursiveContext);
        assertEquals(1, tableDefinition.getColumnDefinition().size());

        ColumnDefinition columnDefinition0 = tableDefinition.getColumnDefinition().get(0);
        assertNotNull(columnDefinition0);
        assertEquals(STREAM_NAME0, columnDefinition0.getStreamName());
        assertEquals(COLUMN_NAME0, columnDefinition0.getColumnName());
    }

    public void testOneColumnWithContextDirectStreamSortedSelelectOneNew() {
        RecursiveContext recursiveContext = new SimpleRecursiveContext(null);
        TableDefinition tableDefinition = makeTableDefinitionDirectStream(recursiveContext);
        NumberedStream<Integer> naturalNumberStream3 = new NaturalNumberStream(2);
        recursiveContext.getContext().put(STREAM_NAME3, naturalNumberStream3);
        assertNotNull(tableDefinition);
        assertEquals(3, tableDefinition.getRawColumnDefinition().size());
        tableDefinition.findNonEmptyColumns(recursiveContext);
        String[] sortOrder = {STREAM_NAME3}; 
        tableDefinition.sort(Arrays.asList(sortOrder), recursiveContext);
        assertEquals(1, tableDefinition.getColumnDefinition().size());

        ColumnDefinition columnDefinition0 = tableDefinition.getColumnDefinition().get(0);
        assertNotNull(columnDefinition0);
        assertEquals(STREAM_NAME3, columnDefinition0.getStreamName());
        assertEquals(STREAM_NAME3, columnDefinition0.getColumnName());
    }

    public void testOneColumnWithContextDirectCalc() {
        RecursiveContext recursiveContext = new SimpleRecursiveContext(null);
        TableDefinition tableDefinition = makeTableDefinitionDirectCalc(recursiveContext);
        assertNotNull(tableDefinition);
        assertEquals(3, tableDefinition.getRawColumnDefinition().size());
        tableDefinition.findNonEmptyColumns(recursiveContext);
        assertEquals(2, tableDefinition.getColumnDefinition().size());

        ColumnDefinition columnDefinition0 = tableDefinition.getColumnDefinition().get(0);
        assertNotNull(columnDefinition0);
        assertEquals(STREAM_NAME0, columnDefinition0.getStreamName());
        assertEquals(COLUMN_NAME0, columnDefinition0.getColumnName());
        ColumnDefinition columnDefinition1 = tableDefinition.getColumnDefinition().get(1);
        assertNotNull(columnDefinition1);
        assertEquals(null, columnDefinition1.getStreamName());
        assertEquals(STREAM_NAME2, columnDefinition1.getColumnName());
    }
}
