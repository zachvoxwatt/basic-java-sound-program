package main;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.stream.JsonReader;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class JSONDataParser 
{
	private Map<String, List<String>> dirmap;
	
	public JSONDataParser()
	{
		this.dirmap = null;
		
		InputStream is = this.getClass().getResourceAsStream("/data/data.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		JsonReader jr = new JsonReader(br);
		
		try 
		{ 
			dirmap = readJSONFile(jr);
			is.close();
			br.close();
			jr.close();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	   
	private Map<String, List<String>> readJSONFile(JsonReader reader) throws IOException
	{
		Map<String, List<String>> returner = new HashMap<>();
		
		String key = null;
		List<String> values = null;
		
		reader.beginArray();
		while (reader.hasNext())
		{
			reader.beginObject();
			
			while (reader.hasNext())
			{
				String name = reader.nextName();
				
				if (name.equals("id")) key = reader.nextString();
				else if (name.equals("various"))
				{
					if (reader.nextBoolean())
					{
						name = reader.nextName();
						if (name.equals("variants")) values = processArray(reader);
					}
					else values = null;
				}
				else reader.skipValue();
				
				returner.put(key, values);
			}
			reader.endObject();
		}
		
		return returner;
	}
	
	private List<String> processArray(JsonReader reader) throws IOException
	{
		List<String> returner = new ArrayList<>();
		reader.beginArray();
		while (reader.hasNext()) returner.add(reader.nextString());
		reader.endArray();
		
		return returner;
	}
	
	public Map<String, List<String>> getDirectoryMap() { return this.dirmap; }
}
