package PentathlonScoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CountScoresTest{

	private final CountScores countScores = new CountScores();
	
	@Test 
	void countShootingPoints() {
		assertEquals(1336, countScores.countShootingPoints(200));
		assertEquals(1324, countScores.countShootingPoints(199));
		assertEquals(4936, countScores.countShootingPoints(500));
	}
	
	@Test
	void countFencingPoints() {
		assertEquals(1080, countScores.countFencingPoints(9,  10));
		assertEquals(1000, countScores.countFencingPoints(7,  10));
	}
	
	@Test
	void countRidingPoints(){
		assertEquals(1200, countScores.countRidingPoints(0, 0, 0));
		assertEquals(1172, countScores.countRidingPoints(1, 0, 0));
		assertEquals(1160, countScores.countRidingPoints(0, 1, 0));
		assertEquals(1140, countScores.countRidingPoints(0, 0, 1));
	}
	
	@Test 
	void countSwimmingPoints() {
		assertEquals(1000, countScores.countSwimmingPoints("02:30.0"));
		assertEquals(996, countScores.countSwimmingPoints("02:30.3"));
		assertEquals(992, countScores.countSwimmingPoints("02:30.6"));
		assertEquals(1004, countScores.countSwimmingPoints("02:29.6"));
	}
	
	@Test
	void timeStrToMilis() {
		assertEquals(61100, countScores.timeStrToMilis("01:01.1"));
		assertEquals(1100, countScores.timeStrToMilis("00:01.1"));
		assertEquals(121200, countScores.timeStrToMilis("02:01.2"));
	}
}
