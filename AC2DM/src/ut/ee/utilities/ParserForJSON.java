package ut.ee.utilities;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParserForJSON {

	HashMap<String, String> values = new HashMap<String, String>();

	public ParserForJSON() {

	}

	private HashMap<String, String> parseJSON(String jsonText) {
		HashMap<String, String> ret = new HashMap<String, String>();
		JSONParser parser = new JSONParser();
		ContainerFactory containerFactory = new ContainerFactory() {
			public List creatArrayContainer() {
				return new LinkedList();
			}

			public Map createObjectContainer() {
				return new LinkedHashMap();
			}

		};

		try {
			Map json = (Map) parser.parse(jsonText, containerFactory);
			Iterator iter = json.entrySet().iterator();
			System.out.println("==iterate result==");
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				ret.put((String) entry.getKey(), (String) entry.getValue());
			}

		} catch (ParseException pe) {
			System.out.println(pe);
		}
		return ret;
	}

	public HashMap parse(String jsonText) {
		return parseJSON(jsonText);
	}

	public HashMap getValues() {
		return values;
	}

}
