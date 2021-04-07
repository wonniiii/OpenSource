package week2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class indexer {
	
	HashMap<String, ArrayList<String>> makeInvertedFile(String filePath) throws Exception {
		
	
		File file = new File("src/data/index.xml");
		String[] array = new String[5];
		ArrayList<String> word = new ArrayList<String>();
		HashMap<String, int[]> TF = new HashMap<>();
		HashMap<String, double[]> TFIDF = new HashMap<>();
		HashMap<String, ArrayList<String>> map = new HashMap<>();

		
		String str = Files.readString(file.toPath());
		StringBuffer sb = new StringBuffer();

		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(str));
		Document doc = db.parse(is);

		for (int i = 0; i < 5; i++) {

			array[i] = doc.getElementsByTagName("body").item(i).getChildNodes().item(0).getNodeValue();
		}
		String[] sp, sp2 = {};

		for (int i = 0; i < 5; i++) {
			sp = array[i].split("#");
			for (int z = 0; z < sp.length; z++) {
				if (sp[z].length() >= 1) {
					sp2 = sp[z].split(":");
					word.add(sp2[0]);
					int cnt = Integer.parseInt(sp2[1]);
					if (!TF.containsKey(sp2[0])) {
						int[] list = new int[5];
						list[i] = cnt;
						TF.put(sp2[0], list);
					}

				}
			}

		}
		
		for(int i=0; i<word.size(); i++) {
			int[] tf = TF.get(word.get(i));
			double[] tf_idf = new double[5];
			int dfx =0;
			
			for(int j=0; j<tf.length; j++) {
				if(tf[j]!=0)
					dfx++;
				
			}
			
			for(int z=0; z<tf.length; z++) {
				Double wxy = tf[z]*Math.log((double)5/dfx); //계산
				tf_idf[z] = Math.round(wxy*100)/100.0; //소수점 둘째자리까지
				
			}
			
			TFIDF.put(word.get(i), tf_idf);
			
			
		}
		
		for(int i=0; i<word.size(); i++) {
			ArrayList<String> colle = new ArrayList<String>();
			double[] ans = TFIDF.get(word.get(i));
			for(int j=0; j<ans.length; j++) {
				if(ans[j]!=0) {
					colle.add(Integer.toString(j));
					colle.add(Double.toString(ans[j]));
				}
			}
			map.put(word.get(i), colle);
		}
		return map;

	}
	
	void makePost(String fileName, HashMap<String, ArrayList<String>> map) throws Exception {
		FileOutputStream fileStream = new FileOutputStream(fileName+".post");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		
		objectOutputStream.writeObject(map);
		objectOutputStream.close();
		
	}
	
	void readPost(String fileName) throws Exception {
		FileInputStream fileStream = new FileInputStream(fileName+".post");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		
		HashMap hashMap = (HashMap)object;
		Iterator<String> it = hashMap.keySet().iterator();
		
		while(it.hasNext()) {
			String key = it.next();
			ArrayList<String> value = (ArrayList<String>)hashMap.get(key);
			System.out.println(key+" -> "+value);
		}
}
}
