package org.shapelogic.streamlogic;

import static org.shapelogic.logic.CommonLogicExpressions.ASPECT_RATIO;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.IQueryCalc;
import org.shapelogic.calculation.QueryCalc;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.color.ColorFactory;
import org.shapelogic.color.ColorUtil;
import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.color.IColorDistance;
import org.shapelogic.imageutil.SLImage;
import org.shapelogic.streams.CalcNumberedStream1;
import org.shapelogic.streams.ListCalcStream1;
import org.shapelogic.streams.ListStream;
import org.shapelogic.streams.NumberedStream;
import org.shapelogic.streams.StreamFactory;

/** Create particles streams.<br />
 * 
 * Definition of colors is still not developed properly.
 * This class contains ways of defining color streams, but they might be 
 * supplemented with more efficient ones.
 * 
 * @author Sami Badawi
 *
 */
public class LoadParticleStreams {
	IQueryCalc queryCalc = QueryCalc.getInstance();
	public RecursiveContext recursiveContext;
	private StreamFactory streamFactory;
	LoadLetterStreams loadLetterStreams;

	public LoadParticleStreams(RecursiveContext recursiveContext) {
		this.recursiveContext = recursiveContext;
		streamFactory = new StreamFactory(recursiveContext);
		loadLetterStreams = new LoadLetterStreams(recursiveContext);
	}

	/** Helper method to create one rule in one letter. 
	 * 
	 * @param letter to define rule for
	 * @param streamName what the required stream for this rule is called in RootMap
	 * @param value constraint value
	 * @param letterFilter if only one rule should be generated this should be set to a letter 
	 */
	public void rule(String letter, String streamName, int value, String letterFilter) {
		if (letterFilter != null && !letterFilter.equalsIgnoreCase(letter))
			return;
		streamFactory.addToAndListStream0(letter, streamName, "==",	value);
	}
	
	/** Load all the required streams for the letter matcher to work.
	 * <br />
	 * In order for this to work the polygons have to be defined first.
	 */
	public void loadColorStreams(NumberedStream<IColorAndVariance> particles, 
			SLImage image) 
	{
		loadColorRStream(particles);
		loadColorGStream(particles);
		loadColorBStream(particles);
		loadColorGrayValueStream(particles, image.isRgb());
	}

	/** Load all the required streams for the letter matcher to work.
	 * <br />
	 * In order for this to work the particles have to be defined first.
	 */
	public void loadStreamsRequiredForParticleMatch(NumberedStream<IColorAndVariance> particles, 
			SLImage image) 
	{
		loadColorStreams(particles, image);
	}
	
	public void loadStreamsRequiredForParticleMatch(SLImage image) {
		//In order for this to work the particles have to be defined first
		NumberedStream<IColorAndVariance> particles = StreamFactory.findNumberedStream(StreamNames.PARTICLES, recursiveContext);
		loadStreamsRequiredForParticleMatch(particles, image);
	}
	
	private void loadColorRStream(NumberedStream<IColorAndVariance> particle) {
		Calc1<IColorAndVariance, Integer> redCalc1 = new Calc1<IColorAndVariance, Integer>() {
			@Override
			public Integer invoke(IColorAndVariance input) {
				int color = input.getMeanColor();
				return ColorUtil.splitRed(color);
			}
		};
		NumberedStream<Integer> colorRStream =
			new CalcNumberedStream1<IColorAndVariance, Integer>(redCalc1,particle);
		queryCalc.put(StreamNames.COLOR_R,colorRStream, recursiveContext);
	}

	private void loadColorGStream(NumberedStream<IColorAndVariance> particles) {
		Calc1<IColorAndVariance, Integer> greenCalc1 = new Calc1<IColorAndVariance, Integer>() {
			@Override
			public Integer invoke(IColorAndVariance input) {
				int color = input.getMeanColor();
				return ColorUtil.splitGreen(color);
			}
		};
		ListStream<Integer> colorGStream = 
			new ListCalcStream1<IColorAndVariance, Integer>(greenCalc1,particles); 
		queryCalc.put(StreamNames.COLOR_G,colorGStream, recursiveContext);
	}

	private void loadColorBStream(NumberedStream<IColorAndVariance> particles) {
		Calc1<IColorAndVariance, Integer> blueCalc1 = new Calc1<IColorAndVariance, Integer>() {
			@Override
			public Integer invoke(IColorAndVariance input) {
				int color = input.getMeanColor();
				return ColorUtil.splitBlue(color);
			}
		};
		ListStream<Integer> colorBStream = 
			new ListCalcStream1<IColorAndVariance, Integer>(blueCalc1,particles); 
		queryCalc.put(StreamNames.COLOR_B,colorBStream, recursiveContext);
	}
	
	/** I should probably make a stream for each HSB channel. */
	private void loadColorGrayValueStream(NumberedStream<IColorAndVariance> particles, 
			final boolean rgb) {
		Calc1<IColorAndVariance, Integer> grayCalc1 = new Calc1<IColorAndVariance, Integer>() {
			@Override
			public Integer invoke(IColorAndVariance input) {
				int color = input.getMeanColor();
				if (rgb)
					color = ColorUtil.rgbToGray(color);
				return color;
 			}
		};
		ListStream<Integer> colorGrayStream = 
			new ListCalcStream1<IColorAndVariance, Integer>(grayCalc1,particles); 
		queryCalc.put(StreamNames.COLOR_GRAY,colorGrayStream, recursiveContext);
	}
	
	/** Make stream for the distance to a reference color.<br />
	 * 
	 * @param particle particle stream
	 * @param referenceColor 
	 * @param image
	 * @param streamName What to call the stream that is created
	 * @return 
	 */
	private ListStream<Double> loadCustomColorDistanceStream(NumberedStream<IColorAndVariance> particle, 
			int referenceColor, SLImage image, String streamName) {
		final IColorDistance colorDistance = ColorFactory.makeColorDistance(image);
		colorDistance.setReferenceColor(referenceColor);
		Calc1<IColorAndVariance, Double> blueCalc1 = new Calc1<IColorAndVariance, Double>() {
			@Override
			public Double invoke(IColorAndVariance input) {
				int color = input.getMeanColor();
				return colorDistance.distanceToReferenceColor(color);
			}
		};
		ListStream<Double> colorStream = 
			new ListCalcStream1<IColorAndVariance, Double>(blueCalc1,particle); 
		if (streamName != null)
			queryCalc.put(streamName,colorStream, recursiveContext);
		return colorStream;
	}

// Example code
	
	final static public String[] EXAMPLE_PARTICLE_ARRAY = 
	{"Flat","Tall","Light round", "Dark round"};	
	
	/** This shows what to do to define rules for the color particle analyzer.<br />
	 * 
	 * This is not useful.<br /> 
	 * Light and dark is turned around if inverted LUT is used.<br /> 
	 */
	public void exampleMakeParticleStream() {
		loadLetterStreams.rule("Flat", ASPECT_RATIO, ">", 1.1, null);
		
		loadLetterStreams.rule("Tall", ASPECT_RATIO, "<", 0.9, null);

		loadLetterStreams.rule("Light round", StreamNames.COLOR_GRAY, ">", 150, null);
		loadLetterStreams.rule("Light round", ASPECT_RATIO, "<", 1.1, null);
		loadLetterStreams.rule("Light round", ASPECT_RATIO, ">", 0.9, null);

		loadLetterStreams.rule("Dark round", StreamNames.COLOR_GRAY, "<", 120, null);
		loadLetterStreams.rule("Dark round", ASPECT_RATIO, "<", 1.1, null);
		loadLetterStreams.rule("Dark round", ASPECT_RATIO, ">", 0.9, null);
	}
}
