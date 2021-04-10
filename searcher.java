package week2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.xml.sax.SAXException;

public class searcher {

	void get(String filePath, String query) throws Exception {
		File file = new File("index.post");
		String[] food = {"떡","라면","아이스크림","초밥","파스타"};
		
		//형태소 분석기
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(query, true); 
		for (int j = 0; j < kl.size(); j++) {
			Keyword kwrd = kl.get(j);
			map.put(kwrd.getString(), 1);
		}
		
		
		//hashmap
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashMap hashMap = readPost(filePath);
		Iterator<String> keyset = hashMap.keySet().iterator();
		
		HashMap readPost(String fileName) throws Exception {
		FileInputStream fileStream = new FileInputStream("index.post");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

		Object object = objectInputStream.readObject();
		objectInputStream.close();

		HashMap hashMap = (HashMap)object;
		return hashMap;

		
		double[] name = new double[5];
		
		while(it.hasNext()) {
			String key = keyset.next();
			ArrayList<String> value = (ArrayList<String>)hashMap.get(key);
			int[] p = new int[5];
			double[] h  = new double[5];
			if(map.containsKey(key)) {
				for (int i = 0; i < 5; i++) {
					if(value.contains(Integer.toString(i)))
					{
						p[i] = map.get(key);
						int idx = value.indexOf(Integer.toString(i));
						h[i] = Double.parseDouble(value.get(idx+1));
						name[i] += p[i]*h[i];
					}		
				}
			}
		}
		for (int i = 0; i < 5; i++) {
			name[i] = Math.round(name[i]*100)/100.0;
			System.out.println(name[i]);
		}
		double[] title = new double[5];
		for (int i = 0; i < title.length; i++) {
			title[i] = name[i];
		}
		Arrays.sort(title);
		String[] top = new String[3];
		int a = 0;
		for (int i = 4; i >= 0; i--) {
			for (int j = 0; j < 5; j++) {
				if(a==3)
					break;
				if(title[i]==id[j]) {
					if(!food[j].equals("")) {
						top[a] = food[j];
						food[j] = "";
						if(a<=2) {
							a++;}
						}
					}
			}
			if(a==3)
				break;
		}
		for (int i = 0; i < top.length; i++) {
			System.out.println(top[i]);
		}
	}
	

	
    }
}
