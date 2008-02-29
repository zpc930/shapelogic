package org.shapelogic.streamlogic;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.RootMap;
import org.shapelogic.entities.NumericRule;
import org.shapelogic.logic.BaseTask;
import org.shapelogic.logic.CalcAndSetTasks;
import org.shapelogic.logic.ContextCalculation;
import org.shapelogic.logic.ExistTask;
import org.shapelogic.logic.ExistTasks;
import org.shapelogic.logic.LetterTaskFactory;
import org.shapelogic.logic.LetterTaskLegacyFactory;
import org.shapelogic.logic.LogicState;
import org.shapelogic.logic.RootTask;
import org.shapelogic.logic.Task;
import org.shapelogic.polygon.GeometricShape2D;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.polygon.SVGReader;
import org.shapelogic.streams.ListCalcStream1;
import org.shapelogic.streams.ListStream;
import org.shapelogic.streams.NamedNumberedStream;


import junit.framework.TestCase;

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
	RootTask rootTask;
	boolean runSlowTests = false;
	
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		//There are 3 different ways to do the setup 
//		setUpByaddingValuesInTask();
//		setUpByaddingLazyCalculations();
		setUpByaddingBaseTask("A");
	}
	
	void setUpByaddingLazyCalculations() {
		rootTask = RootTask.getInstance();
		new ContextCalculation(FILE_NAME_KEY, rootTask) {
			@Override
			public Object calculation(Task context)
					throws Exception {
				return FILE_NAME;
			}
		};
		
		ContextCalculation contextCalculationY = new ContextCalculation() {
			//Constructor
			{
				name = RAW_POLYGON;
			}
			@Override
			public Object calculation(Task currentTask)
					throws Exception {
				String fileName = (String) currentTask.findNamedValue(FILE_NAME_KEY);
		    	SVGReader reader = new SVGReader(fileName);
		        Polygon polygon = reader.getPolygon();
				return polygon;
			}
		};
		rootTask.setContextCalculation(contextCalculationY.name, contextCalculationY);

		new ContextCalculation(POLYGON, rootTask) {
			@Override
			public Object calculation(Task currentTask)
					throws Exception {
				Polygon rawPolygon = (Polygon) currentTask.findNamedValue(RAW_POLYGON);
		        Polygon polygon = rawPolygon.cleanUp(true, 0.02f);
				return polygon;
			}
		};
	}

	LogicState setUpByaddingValuesInTask() {
		rootTask = RootTask.getInstance();
		CalcAndSetTasks setupTask = new CalcAndSetTasks(rootTask,
			FILE_NAME_KEY, QUOTE + FILE_NAME + QUOTE,
			"SVGReader", SVGReader.class,
			"svgReader","SVGReader.makeInstance(fileName)",
			RAW_POLYGON,"svgReader.getPolygon()",
			POLYGON,"rawPolygon.cleanUp(true, 0.02)"
		); 
		setupTask.invoke();
		return setupTask.getState();
	}
	
	LogicState setUpByaddingBaseTask(final String letter) {
		rootTask = RootTask.getInstance();
		final String fileName = FILE_DIR + "/" + letter + ".svg";
		BaseTask setupTask = new BaseTask(rootTask) {
			@Override
			public boolean match() throws Exception {
				rootTask.setNamedValue(FILE_NAME_KEY, fileName);
				Polygon rawPolygon = new SVGReader(fileName).getPolygon();
				Polygon polygon1 = rawPolygon.cleanUp(true, 0.02);
				Polygon polygon = polygon1.improve();
				rootTask.setNamedValue(RAW_POLYGON, rawPolygon);
				rootTask.setNamedValue(POLYGON, polygon);
				return true;
			}
		}; 
		setupTask.invoke();
		return setupTask.getState();
	}
	
	/** just one variable x, that is set by a ContextCalculation 
	 * 
	 * How should the question be phrased?
	 * */ 
	public void testFindFileName() {
		Object x = rootTask.findNamedValue(FILE_NAME_KEY);
		assertEquals(FILE_NAME, x);
	}

	public void testFileNameWithExist() {
		ExistTask letterTask = new ExistTask(rootTask,FILE_NAME_KEY);
		letterTask.invoke();
		Object x = rootTask.getNamedValue(FILE_NAME_KEY);
		assertEquals(FILE_NAME, x);
	}

	public void testPolygonWithExist() {
		ExistTasks letterTask = new ExistTasks(rootTask,FILE_NAME_KEY,RAW_POLYGON,POLYGON);
		letterTask.invoke();
		Object rawPolygon = rootTask.getNamedValue(RAW_POLYGON);
		assertNotNull(rawPolygon);
		Object polygon = rootTask.getNamedValue(POLYGON);
		assertNotNull(polygon);
	}

	public void testLetterAMatch() {
		RootMap.clear();
		String fileName = FILE_NAME;
		ListStream<Polygon> rawPolygons = makeRawPolygonsStream(fileName);
		Polygon rawPolygon = rawPolygons.next();
		assertNotNull(rawPolygon);
		System.out.println("rawPolygon: " + rawPolygon);
		assertTrue(rawPolygon instanceof Polygon);
		LoadLetterStreams.loadStreamsRequiredForLetterMatch();
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

	public void testCreateLetterATaskFromRule() {
		ExistTasks letterTask = new ExistTasks(rootTask,FILE_NAME_KEY,RAW_POLYGON,POLYGON);
		letterTask.invoke();
		BaseTask letterATask = LetterTaskLegacyFactory.createLetterATaskFromRule(rootTask);
		Object result = letterATask.invoke();
		assertEquals("A",result);
	}
	
	private void oneStraightLetterMatch(final String letter, boolean onlyMatchAgainstSelf) {
		RootMap.clear();
		final String fileName = FILE_DIR + "/" + letter + ".svg";
		ListStream<Polygon> rawPolygons = makeRawPolygonsStream(fileName);
		LoadLetterStreams.loadStreamsRequiredForLetterMatch();
		if (onlyMatchAgainstSelf)
			LoadLegacyLetterStreams.makeStraightLetterStream(letter);
		else
			LoadLegacyLetterStreams.makeStraightLetterStream(null); 
    	NamedNumberedStream<Polygon> rawPolygonsFromRoot = 
    		new NamedNumberedStream<Polygon>(RAW_POLYGON);
    	NamedNumberedStream<Boolean> aStreamFromRoot = 
    		new NamedNumberedStream<Boolean>(letter);
    	LoadLegacyLetterStreams.makeStraightLetterXOrStream();
    	assertTrue(aStreamFromRoot.get(0));
		ListStream<String> letterString = (ListStream<String>) RootMap.get("Letter");
    	assertEquals(letter,letterString.get(0));
	}
	
	public void oneLetterMatch(final String letter, boolean onlyMatchAgainstSelf) {
		setUpByaddingBaseTask(letter);
		NumericRule[] rulesArray = LetterTaskLegacyFactory.getSimpleNumericRuleForAllStraightLetters(LetterTaskFactory.POLYGON);
		List<NumericRule> rulesList = Arrays.asList(rulesArray);
		String onlyMatchLetter = null;
		if (onlyMatchAgainstSelf)
			onlyMatchLetter = letter;
		BaseTask letterTask = LetterTaskFactory.createLetterTasksFromRule(rootTask, rulesList, onlyMatchLetter);
		Object result = letterTask.invoke();
		assertEquals(letter,result);
	}
	
	public void testAllLetterMatchFromRules() {
		ExistTasks polygonTask = new ExistTasks(rootTask,FILE_NAME_KEY,RAW_POLYGON,POLYGON);
		polygonTask.invoke();
		NumericRule[] rulesArray = LetterTaskLegacyFactory.getSimpleNumericRuleForAllStraightLetters(LetterTaskFactory.POLYGON);
		List<NumericRule> rulesList = Arrays.asList(rulesArray);
		BaseTask letterTask = LetterTaskFactory.createLetterTasksFromRule(rootTask, rulesList, null);
		Object result = letterTask.invoke();
		assertEquals("A",result);
	}
	
	public void testAllLettersMatchFromRules() {
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
	
	
	public void testAllLetterMatchFromRulesFromHibernate() {
		if (!runSlowTests) 
			return;
		// Start EntityManagerFactory
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloworld");
		
		// First unit of work
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		for (NumericRule rule: LetterTaskLegacyFactory.getSimpleNumericRuleForAllStraightLetters(LetterTaskFactory.POLYGON)) {
			em.persist(rule);
		}
		
		tx.commit();
		em.close();
		
		// Second unit of work
		EntityManager newEm = emf.createEntityManager();
		EntityTransaction newTx = newEm.getTransaction();
		newTx.begin();
		
		String queryForAll = "select m from NumericRule m order by m.id asc";
		List rules = newEm.createQuery(queryForAll).getResultList();
		
		assertEquals(75, rules.size());
		
		String queryForA = "select m from NumericRule m where m.parentOH = 'A' order by m.id asc";

		List rulesForA = newEm.createQuery(queryForA).getResultList();
		
		assertEquals(5, rulesForA.size());

		newTx.commit();
		newEm.close();
		
		// Shutting down the application
		emf.close();
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
