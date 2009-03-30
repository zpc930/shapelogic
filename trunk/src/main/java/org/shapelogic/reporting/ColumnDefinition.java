package org.shapelogic.reporting;

import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.streams.NumberedStream;

/** ColumnDefinition defines one column from the table.<br />
 *
 * @author Sami Badawi
 */
class ColumnDefinition {
    
    String _streamName;
    String _columnName;
    NumberedStream _stream;
    Boolean _empty = null;

    public ColumnDefinition(String streamName, String columnName) {
        _streamName = streamName;
        _columnName = columnName;
    }

    public String getStreamName() {
        return _streamName;
    }

    public String getColumnName() {
        return _columnName;
    }

    public NumberedStream getStream() {
        return _stream;
    }

    public boolean findStream(RecursiveContext _recursiveContext) {
        if (_recursiveContext == null)
            return false;
        Object obj = _recursiveContext.getContext().get(_streamName);
        if (obj != null && obj instanceof NumberedStream) {
            _stream = (NumberedStream) obj;
            _empty = Boolean.FALSE;
        }
        else {
            _empty = Boolean.TRUE;
        }
        return !_empty;
    }

    public Boolean isEmpty() {
        return _empty;
    }
}
