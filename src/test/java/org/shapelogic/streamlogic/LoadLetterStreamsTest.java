package org.shapelogic.streamlogic;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.RootMap;
import org.shapelogic.polygon.GeometricShape2D;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.polygon.SVGReader;
import org.shapelogic.streams.ListCalcStream1;
import org.shapelogic.streams.ListStream;
import org.shapelogic.streams.NamedNumberedStream;

/** Test LoadLetterStreams.<br />
 * 
 * Based on LetterTaskTest that is task based.<br />
 * 
 * How should the rules be translated?<br />
 * 
 * I could generate the Letter XOr stream or maybe the individual Boolean streams.
 * 
 * @author Sami Badawi
 *
 */
public class LoadLetterStreamsTest extends TestCase
{
	private static final String POLYGON = "polygon";
	private static final String RAW_POLYGON = "rawPolygon";
	public static final String FILE_NAME_KEY = "fileName";
	private static final String FILE_NAME = "./src/test/resources/svg/letter/A.svg";
	private static final String FILE_DIR = "./src/test/resources/svg/letter";
	public static final String QUOTE = "\"";
	boolean runSlowTests = false;
	
	public static final String[] LETTERS_TO_TEST = 
	{"A", "E", "F", "H", "I", "K", "L", "M", "N", "T", "V",  "X", "Y", "Z"};
	
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
	}
	
	public void testLetterAMatch() {
		RootMap.clear();
		String fileName = FILE_NAME;
		ListStream<Polygon> rawPolygons = makeRawPolygonsStream(fileName);
		Polygon rawPolygon = rawPolygons.next();
		assertNotNull(rawPolygon);
		System.out.println("rawPolygon: " + rawPolygon);
		assertTrue(rawPolygon instanceof Polygon);
		LoadPolygonStreams.loadStreamsRequiredForLetterMatch();
		LoadLegacyLetterStreams.makeStraightLetterStream(null); 
		//LoadLetterStreams.makeStraightLetterStream("A"); //To only test one letter
    	NamedNumberedStream<Polygon> rawPolygonsFromRoot = 
    		new NamedNumberedStream<Polygon>(RAW_POLYGON);
    	assertSame(rawPolygon, rawPolygonsFromRoot.get(0));
    	NamedNumberedStream<Boolean> aStreamFromRoot = 
    		new NamedNumberedStream<Boolean>("A");
    	assertTrue(aStreamFromRoot.get(0));
	}

	public void printAnnotaions(Polygon polygon) {
		System.out.println("Print annotations:");
		Map<Object, Set<GeometricShape2D>> map = polygon.getAnnotatedShape().getMap();
		for (Entry<Object, Set<GeometricShape2D>> entry: map.entrySet())
			System.out.println(entry.getKey() +":\n" + entry.getValue());
	}

	private void oneStraightLetterMatch(final String letter, boolean onlyMatchAgainstSelf) {
		RootMap.clear();
		final String fileName = FILE_DIR + "/" + letter + ".svg";
		makeRawPolygonsStream(fileName);
		LoadPolygonStreams.loadStreamsRequiredForLetterMatch();
		String letterFilter = null;
		if (onlyMatchAgainstSelf)
			letterFilter = letter;
		LoadLegacyLetterStreams.loadStraightLetterStream(letterFilter);
    	NamedNumberedStream<Boolean> aStreamFromRoot = 
    		new NamedNumberedStream<Boolean>(letter);
    	LoadLegacyLetterStreams.makeStraightLetterXOrStream();
    	assertTrue(aStreamFromRoot.get(0));
		ListStream<String> letterString = (ListStream<String>) RootMap.get(StreamNames.LETTERS);
    	assertEquals(letter,letterString.get(0));
	}
	
	public void oneLetterMatch(final String letter, boolean onlyMatchAgainstSelf) {
		RootMap.clear();
		final String fileName = FILE_DIR + "/" + letter + ".svg";
		makeRawPolygonsStream(fileName);
		LoadPolygonStreams.loadStreamsRequiredForLetterMatch();
		String letterFilter = null;
		if (onlyMatchAgainstSelf)
			letterFilter = letter;
		LoadLetterStreams.loadLetterStream(letterFilter);
    	NamedNumberedStream<Boolean> aStreamFromRoot = 
    		new NamedNumberedStream<Boolean>(letter);
    	LoadLetterStreams.makeXOrStream(StreamNames.LETTERS,LETTERS_TO_TEST);
    	assertTrue("Bad match for: " + letter,aStreamFromRoot.get(0));
		ListStream<String> letterString = (ListStream<String>) RootMap.get(StreamNames.LETTERS);
    	assertEquals(letter,letterString.get(0));
	}
	
	/** These rules do not work since SVG does not create annotations yet.
	 */
	public void te_stAllLettersMatchFromRules() {
//		oneLetterMatch("Y",true);
		String[] lettersToTest = 
		{"A", "E", "F", "H", "I", "K", "L", "M", "N", "T", "V",  "X", "Y", "Z"};
		//Missing letters: W
		String[] ambiguousLettersToTest = {};
		for (String letter: ambiguousLettersToTest)
			oneLetterMatch(letter,true);
		for (String letter: lettersToTest)
			oneLetterMatch(letter,false);
	}
	
	public void testAllStraightLettersMatchFromRules() {
//		oneLetterMatch("Y",true);
		String[] lettersToTest = 
		{"A", "E", "F", "H", "I", "K", "L", "M", "N", "T", "V",  "X", "Y", "Z"};
		//Missing letters: W
		String[] ambiguousLettersToTest = {};
		for (String letter: ambiguousLettersToTest)
			oneStraightLetterMatch(letter,true);
		for (String letter: lettersToTest)
			oneStraightLetterMatch(letter,false);
	}
	
//------------------------New methods for streams------------------------

	/** Based on a fileName it creates the lazy streams for polygons and rawPolygons. */
	public ListStream<Polygon> makeRawPolygonsStream(String fileName) {
		
    	SVGReader reader = new SVGReader(fileName);
    	RootMap.put(RAW_POLYGON,reader);
    	
    	Calc1<Polygon, Polygon> cleanUpPolygon = 
    		new Calc1<Polygon, Polygon>() {
				@Override
				public Polygon invoke(Polygon rawPolygon) {
					return rawPolygon.cleanUp(true, 0.02f);
				}
    	}; 
    	
    	ListStream<Polygon> polygons = new ListCalcStream1<Polygon, Polygon>(cleanUpPolygon,reader);
    	RootMap.put(StreamNames.POLYGONS,polygons);
    	return reader;
	}
}
