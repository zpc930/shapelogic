package org.shapelogic.calculation;

/** Compose Calculations.
 * <br />
 * compose(calc1,calc0).invoke(x) == calc1.invoke(calc0.invoke(x))<br />
 * So they are composed from the right. <br />
 * <br />
 * Not sure how these would work with more complicated classes.<br />
 * 
 * Say compose a Stream with another stream. <br />
 * I think that this is a stream compose. <br />
 * 
 * What about an CalcValue with a Calc1 I think that should give a CalcInvoke. <br /> 
 * 
 * But this is ambiguous if added to this class. <br />
 * So either give these a different name. Or add to a different class. <br />
 * What makes this worse is that a Stream implementation is also both a Calc1 
 * and a CalcValue. <br />
 * So do not add this now. <br />
 * 
 * @author Sami Badawi
 *
 */
public class CalcComposition {
	
	/** Composition of calculations / closures with 1 input and 1 output.
	 * 
	 * @param <In0> 
	 * @param <In1>
	 * @param <Out>
	 * @param calc1 second calculation
	 * @param calc0 first calculation
	 * @return
	 */
	static public <In0,In1,Out> Calc1<In0,Out> compose(
			final Calc1<In1,Out> calc1, final Calc1<In0,In1> calc0) 
	{
		return new Calc1<In0,Out>() {
			@Override
			public Out invoke(In0 input) {
				return calc1.invoke(calc0.invoke(input));
			}
		};
	}
	
	/** Composition of calculations with 0 input and 1 input.
	 * 
	 * @param <In>
	 * @param <Out>
	 * @param calc1
	 * @param calc0
	 * @return
	 */
	static public <In,Out> Calc0<Out> compose(
			final Calc1<In,Out> calc1, final Calc0<In> calc0) 
	{
		return new Calc0<Out>() {
			@Override
			public Out invoke() {
				return calc1.invoke(calc0.invoke());
			}
		};
	}

	/** Compose calculation with index and calculation with 1 input 
	 * 
	 * @param <In>
	 * @param <Out>
	 * @param calc1
	 * @param calc0
	 * @return
	 */
	static public <In,Out> CalcIndex0<Out> compose(
			final Calc1<In,Out> calc1, final CalcIndex0<In> calc0) 
	{
		return new CalcIndex0<Out>() {
			@Override
			public Out invoke(int index) {
				return calc1.invoke(calc0.invoke(index));
			}
		};
	}

   /** Compose calculation with 1 input and one index and calculation with 1 input.
    * 
    * @param <In0>
    * @param <In1>
    * @param <Out>
    * @param calc1
    * @param calc0
    * @return
    */
	static public <In0,In1,Out> CalcIndex1<In0,Out> compose(
			final Calc1<In1,Out> calc1, final CalcIndex1<In0,In1> calc0) 
	{
		return new CalcIndex1<In0,Out>() {
			@Override
			public Out invoke(In0 input, int index) {
				return calc1.invoke(calc0.invoke(input, index));
			}
		};
	}
	
   /** Compose 2 calculations with 1 input and one index.
    * 
    * @param <In0>
    * @param <In1>
    * @param <Out>
    * @param calc1
    * @param calc0
    * @return
    */
	static public <In0,In1,Out> CalcIndex1<In0,Out> compose(
			final CalcIndex1<In1,Out> calc1, final CalcIndex1<In0,In1> calc0) 
	{
		return new CalcIndex1<In0,Out>() {
			@Override
			public Out invoke(In0 input, int index) {
				return calc1.invoke(calc0.invoke(input, index),index);
			}
		};
	}
}
