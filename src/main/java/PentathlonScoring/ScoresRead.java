package PentathlonScoring;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

public class ScoresRead {

	public static List<Athlete> athletesList; 
	
	public ScoresRead(String file){
		try {
			redDataLineByLine(file);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void redDataLineByLine(String file) throws FileNotFoundException
	{
		CSVReader csvReader = null;
		
		try {
		
			csvReader = new CSVReader(new FileReader(file));
			ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
			mappingStrategy.setType(Athlete.class);
			
			String[] columns = new String[] {"name", "fencingVictories", "swimmingTime", "ridingKnockingDown", "ridingRefusal", "ridingDiobedienceLeading", "shootingTargetScore", "runTime"};
			
			mappingStrategy.setColumnMapping(columns);
			
			CsvToBean csvToBean = new CsvToBean();
			
			athletesList = csvToBean.parse(mappingStrategy, csvReader);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				csvReader.close();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
