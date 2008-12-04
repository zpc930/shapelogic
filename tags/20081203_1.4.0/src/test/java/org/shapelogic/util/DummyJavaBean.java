package org.shapelogic.util;

/** DummyJavaBean to test setting values.<br />
 *
 * @author Sami Badawi
 */
public class DummyJavaBean {
    private String name;
    private int number;
    public double version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
