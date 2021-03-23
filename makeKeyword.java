package week2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makeKeyword {
	public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException  {
		// TODO Auto-generated method stub

		File path = new File("src/2주차 실습 html");
		File[] files = path.listFiles();
		int fileNum = files.length;

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document = docBuilder.newDocument();

		document.setXmlStandalone(true);

		Element docs = document.createElement("docs");
		document.appendChild(docs);
	
		for (int i = 0; i < fileNum; i++) {

			String fileName = files[i].getName();
			int pos = fileName.lastIndexOf(".");
			String _fileName = fileName.substring(0, pos);

			String number = Integer.toString(i);

			Element doc = document.createElement("doc");
			docs.appendChild(doc);

			doc.setAttribute("id", number);

			Element title = document.createElement("title");
			title.appendChild(document.createTextNode(_fileName));
			doc.appendChild(title);

			org.jsoup.nodes.Document origin = Jsoup.parse(files[i], "UTF-8");

			org.jsoup.nodes.Element bodyText = origin.body();

			Element body = document.createElement("body");

			

			doc.appendChild(body);

			KeywordExtractor ke = new KeywordExtractor();
			KeywordList kl = ke.extractKeyword(bodyText.text(), true);
			for (int k = 0; k < kl.size(); k++) {
				Keyword kwrd = kl.get(k);
				body.appendChild(document.createTextNode("#" + kwrd.getString() + kwrd.getCnt()));

			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new FileOutputStream(new File("src/data/index.xml")));

			transformer.transform(source, result);

		}
	}
}