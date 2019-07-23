package PentathlonScoring;

public class CountScores {

	public int countSwimmingPoints(String time) {
		
		final int swimmingBeginnigPoints = 1000;
		final int pointsAddOrSubstract = 4;
		final String specificTime = "02:30";
		final double differenceInTime = 1000/3;
		String[] specificTimeSplitted = specificTime.split(":");
		String[] timeSplitted = time.split("[:\\.]");
	
		long milisInSpecificTime = Integer.parseInt(specificTimeSplitted[0]) * 1000 * 60 + Integer.parseInt(specificTimeSplitted[1]) * 1000; 
		long milisInTime = Integer.parseInt(timeSplitted[0])* 1000 * 60 + Integer.parseInt(timeSplitted[1]) * 1000 + Integer.parseInt(timeSplitted[2])*100;
		double difference = (milisInTime - milisInSpecificTime) / differenceInTime;
		return (int) (swimmingBeginnigPoints - difference * pointsAddOrSubstract);
	}
	
	public long timeStrToMilis(String time) {
		
		String[] timeSplitted = time.split("[:\\.]");
		long milisInTime = Integer.parseInt(timeSplitted[0])* 1000 * 60 + Integer.parseInt(timeSplitted[1]) * 1000 + Integer.parseInt(timeSplitted[2]) * 100;
		return milisInTime;
	}	
	
	public int countShootingPoints(int targetScore) {
		
		final int beginningPoints = 1000;
		final int targetScoreForThousandPoints = 172;
		final int pointStaticDifferece = 12;
		
		int difference = targetScore - targetScoreForThousandPoints;

		return beginningPoints + (difference * pointStaticDifferece);
	}
	
	public int countFencingPoints(int fencingVictories, int numOfAthletes) {
		
		final int fencingBeginnigPoints = 1000;
		final int pointsAddOrSubstract = 40;
		
		int possibleWins = numOfAthletes - 1;
		
		return fencingBeginnigPoints + (fencingVictories - (int) Math.ceil(possibleWins*0.7)) * pointsAddOrSubstract;
	}
	
	public int countRidingPoints(int ridingKnockingDown, int ridingRefusal, int ridingDisobedienceLeading) {
		
		final int ridingBeginningPoints = 1200;
		final int knockingDownCost = 28;
		final int refusalCost = 40;
		final int disobedienceCost = 60;

		return ridingBeginningPoints - (ridingKnockingDown * knockingDownCost) - (ridingRefusal * refusalCost) - (ridingDisobedienceLeading * disobedienceCost);
	}  
	
}
