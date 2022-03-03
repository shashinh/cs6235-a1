package cs6235.a1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import soot.SceneTransformer;

public abstract class AnalysisBase extends SceneTransformer {
	//protected static List<Query> queries;
	protected static Map<String, List<Query>> queriesMap;
	
	protected void loadQueries() {
		queriesMap = new HashMap<String, List<Query>>();
		
		String path = Options.queriesPath;
		File file = new File(path);
		try {
			FileInputStream f = new FileInputStream(file);
			Scanner s = new Scanner(f);
			
			if(s.hasNextLine()) {
				int methodCount = Integer.parseInt(s.nextLine());
				for(int m = 0; m < methodCount; m++) {
					String methodName = s.nextLine();
					int queryCount = Integer.parseInt(s.nextLine());
					List<Query> queries = new LinkedList<Query>();
					for(int q = 0; q < queryCount; q++) {
						String queryLine = s.nextLine();
						String[] ops = queryLine.split(",");
						if(ops.length != 2) {
							throw new IllegalArgumentException("Queries not in acceptable format!");
						} else {
							Query query = new Query(ops[0], ops[1]);
							queries.add(query);
						}
					}

					queriesMap.put(methodName, queries);
				}
				
			}
			
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected Map<String, List<Query>> getQueries() {
		return queriesMap;
	}
	
	
	public abstract String getResultString();
}
