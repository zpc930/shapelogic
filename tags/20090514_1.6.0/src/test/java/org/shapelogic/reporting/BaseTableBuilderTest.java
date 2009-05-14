package org.shapelogic.reporting;

import java.util.List;
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
        assertEquals(TableDefinitionTest.STREAM_NAME0, columnDefinition0.getStreamName());
        assertEquals(TableDefinitionTest.COLUMN_NAME0, columnDefinition0.getColumnName());
        ColumnDefinition columnDefinition1 = tableDefinition.getColumnDefinition().get(1);
        assertNotNull(columnDefinition1);
        assertEquals(TableDefinitionTest.STREAM_NAME2, columnDefinition1.getStreamName());
        assertEquals(TableDefinitionTest.STREAM_NAME2, columnDefinition1.getColumnName());

        BaseTableBuilder baseTableBuilder = new BaseTableBuilder(tableDefinition, true);
        assertNotNull(baseTableBuilder);
        List outputList = baseTableBuilder.getOutputList();
        assertEquals(0, outputList.size());
        baseTableBuilder.buildHeadline();
        assertEquals(2, outputList.size());
        assertEquals(TableDefinitionTest.COLUMN_NAME0 ,outputList.get(0));
        assertEquals(TableDefinitionTest.STREAM_NAME2 ,outputList.get(1));

        baseTableBuilder.buildLine(0);
        assertEquals(4, outputList.size());
        Integer ZERO = new Integer(0);
        assertEquals(ZERO ,outputList.get(2));
        assertEquals(ZERO ,outputList.get(3));

        baseTableBuilder.buildLine(1);
        assertEquals(6, outputList.size());
        Integer ONE = new Integer(1);
        assertEquals(ONE ,outputList.get(4));
        assertEquals(ONE ,outputList.get(5));

        baseTableBuilder.buildLine(2);
        assertEquals(8, outputList.size());
        Integer TWO = new Integer(2);
        assertEquals(TWO ,outputList.get(6));
        assertEquals(TWO ,outputList.get(7));

        baseTableBuilder.buildLine(3);
        assertEquals(10, outputList.size());
        Integer THREE = new Integer(3);
        assertEquals(null ,outputList.get(8));
        assertEquals(THREE ,outputList.get(9));
    }

    public void testTableDefinitionDirectStream() {
        RecursiveContext recursiveContext = new SimpleRecursiveContext(null);
        TableDefinition tableDefinition = TableDefinitionTest.makeTableDefinitionDirectStream(recursiveContext);
        assertNotNull(tableDefinition);
        assertEquals(3, tableDefinition.getRawColumnDefinition().size());
        tableDefinition.findNonEmptyColumns(recursiveContext);
        assertEquals(2, tableDefinition.getColumnDefinition().size());

        ColumnDefinition columnDefinition0 = tableDefinition.getColumnDefinition().get(0);
        assertNotNull(columnDefinition0);
        assertEquals(TableDefinitionTest.STREAM_NAME0, columnDefinition0.getStreamName());
        assertEquals(TableDefinitionTest.COLUMN_NAME0, columnDefinition0.getColumnName());
        ColumnDefinition columnDefinition1 = tableDefinition.getColumnDefinition().get(1);
        assertNotNull(columnDefinition1);
        assertEquals(null, columnDefinition1.getStreamName());
        assertEquals(TableDefinitionTest.STREAM_NAME2, columnDefinition1.getColumnName());

        BaseTableBuilder baseTableBuilder = new BaseTableBuilder(tableDefinition, true);
        assertNotNull(baseTableBuilder);
        List outputList = baseTableBuilder.getOutputList();
        assertEquals(0, outputList.size());
        baseTableBuilder.buildHeadline();
        assertEquals(2, outputList.size());
        assertEquals(TableDefinitionTest.COLUMN_NAME0 ,outputList.get(0));
        assertEquals(TableDefinitionTest.STREAM_NAME2 ,outputList.get(1));

        baseTableBuilder.buildLine(0);
        assertEquals(4, outputList.size());
        Integer ZERO = new Integer(0);
        assertEquals(ZERO ,outputList.get(2));
        assertEquals(ZERO ,outputList.get(3));

        baseTableBuilder.buildLine(1);
        assertEquals(6, outputList.size());
        Integer ONE = new Integer(1);
        assertEquals(ONE ,outputList.get(4));
        assertEquals(ONE ,outputList.get(5));

        baseTableBuilder.buildLine(2);
        assertEquals(8, outputList.size());
        Integer TWO = new Integer(2);
        assertEquals(TWO ,outputList.get(6));
        assertEquals(TWO ,outputList.get(7));

        baseTableBuilder.buildLine(3);
        assertEquals(10, outputList.size());
        Integer THREE = new Integer(3);
        assertEquals(null ,outputList.get(8));
        assertEquals(THREE ,outputList.get(9));
    }

    public void testTableDefinitionDirectCalc() {
        RecursiveContext recursiveContext = new SimpleRecursiveContext(null);
        TableDefinition tableDefinition = TableDefinitionTest.makeTableDefinitionDirectCalc(recursiveContext);
        assertNotNull(tableDefinition);
        assertEquals(3, tableDefinition.getRawColumnDefinition().size());
        tableDefinition.findNonEmptyColumns(recursiveContext);
        assertEquals(2, tableDefinition.getColumnDefinition().size());

        ColumnDefinition columnDefinition0 = tableDefinition.getColumnDefinition().get(0);
        assertNotNull(columnDefinition0);
        assertEquals(TableDefinitionTest.STREAM_NAME0, columnDefinition0.getStreamName());
        assertEquals(TableDefinitionTest.COLUMN_NAME0, columnDefinition0.getColumnName());
        ColumnDefinition columnDefinition1 = tableDefinition.getColumnDefinition().get(1);
        assertNotNull(columnDefinition1);
        assertEquals(null, columnDefinition1.getStreamName());
        assertEquals(TableDefinitionTest.STREAM_NAME2, columnDefinition1.getColumnName());

        BaseTableBuilder baseTableBuilder = new BaseTableBuilder(tableDefinition, true);
        assertNotNull(baseTableBuilder);
        List outputList = baseTableBuilder.getOutputList();
        assertEquals(0, outputList.size());
        baseTableBuilder.buildHeadline();
        assertEquals(2, outputList.size());
        assertEquals(TableDefinitionTest.COLUMN_NAME0 ,outputList.get(0));
        assertEquals(TableDefinitionTest.STREAM_NAME2 ,outputList.get(1));

        baseTableBuilder.buildLine(0);
        assertEquals(4, outputList.size());
        Integer ZERO = new Integer(0);
        assertEquals(ZERO ,outputList.get(2));
        assertEquals(ZERO ,outputList.get(3));

        baseTableBuilder.buildLine(1);
        assertEquals(6, outputList.size());
        Integer ONE = new Integer(1);
        assertEquals(ONE ,outputList.get(4));
        assertEquals(ONE ,outputList.get(5));

        baseTableBuilder.buildLine(2);
        assertEquals(8, outputList.size());
        Integer TWO = new Integer(2);
        assertEquals(TWO ,outputList.get(6));
        assertEquals(TWO ,outputList.get(7));

        baseTableBuilder.buildLine(3);
        assertEquals(10, outputList.size());
        assertEquals(null ,outputList.get(8));
        assertEquals(null ,outputList.get(9));
    }
}
