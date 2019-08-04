package PentathlonScoring;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class App {
	static Gson gson = new Gson();
    public static void main(String[] args) throws IOException{
    	
    	String file =  Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/PentathlonScoring/Athlete_Results.csv";
    	ScoresRead scores = new ScoresRead(file);
    	CountScores countScores = new CountScores();
    	
    	List<Athlete> athletesScore = scores.athletesList;
    	List<Athlete> listForRunning = new ArrayList<Athlete>();
    	List<Athlete> finalList = new ArrayList<Athlete>();
    	
    	Comparator<Athlete> athleteFinalScoresComparatorLambda  =
    		    (a1, a2) -> a1.getFinalPoints() - a2.getFinalPoints();
    		    
    	Comparator<Athlete> athleteLastCompetitionTimeComparatorLambda  =
    	        (a1, a2) -> a1.getConcludingEvent() - a2.getConcludingEvent();
    	
    	for (Athlete athlete : athletesScore) {
    		
    		athlete.setFinalPoints(countScores.countShootingPoints(athlete.getShootingTargetScore()) + countScores.countFencingPoints(athlete.getFencingVictories(), scores.athletesList.size()) 
    		+countScores.countRidingPoints(athlete.getRidingKnockingDown(), athlete.getRidingRefusal(), athlete.getRidingDiobedienceLeading()) + countScores.countSwimmingPoints(athlete.getSwimmingTime()));
    		
    		listForRunning.add(athlete);
    	
    	}
    	Collections.sort(listForRunning, athleteFinalScoresComparatorLambda);	
    	
    	int bestPoints = listForRunning.get(listForRunning.size()-1).getFinalPoints();
    	for (int i = listForRunning.size()-1; i>= 0; i--) {
    		
    		int time = (int) (((bestPoints - listForRunning.get(i).getFinalPoints()) * 1000) -  countScores.timeStrToMilis(listForRunning.get(i).getRunTime()));
    		listForRunning.get(i).setConcludingEvent(time);
    		finalList.add(listForRunning.get(i));
    		long milisecond = ((bestPoints - listForRunning.get(i).getFinalPoints()) * 1000)  % 1000;
    		long second = (((bestPoints - listForRunning.get(i).getFinalPoints()) * 1000) / 1000) % 60;
    		long minute = (((bestPoints - listForRunning.get(i).getFinalPoints()) * 1000) / (1000 * 60)) % 60;
    		listForRunning.get(i).setConcludingEventTime(String.format("%02d:%02d.%d", minute, second, milisecond));
    	}
    	Collections.sort(finalList, athleteLastCompetitionTimeComparatorLambda);	
    	
    	for(int i=0; i<=finalList.size()-1; i++) {
       		finalList.get(i).setPlace(i + 1);
    	}
    	
    	final int serverPort = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/results", (exchange -> {
        	Headers reqHeaders = exchange.getRequestHeaders();
        	
        	if("GET".equals(exchange.getRequestMethod()) &&  "v1".equals(reqHeaders.getFirst("Accept-version"))) {
        		
        		String resultsJsonString = gson.toJson(finalList);
        		StringBuilder str = new StringBuilder("application/results.pentathlonscoring.");
        		str.append(reqHeaders.getFirst("Accept-version"));
        		str.append("+json");
        		exchange.getResponseHeaders().add("Content-Type",   str.toString());
	        	
        		exchange.sendResponseHeaders(200, resultsJsonString.getBytes().length);
	        	
	        	OutputStream output =  exchange.getResponseBody();
	        	output.write(resultsJsonString.getBytes());
	        	output.flush();
        	} else {
        		exchange.sendResponseHeaders(405, -1);     	}
        		exchange.close();
        }));
        server.setExecutor(null);
        server.start();
    
    }
}