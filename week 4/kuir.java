package week2;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

public class kuir {

	public static void main(String[] args)
			throws ParserConfigurationException, IOException, TransformerException, SAXException {
		// TODO Auto-generated method stub

		System.out.println(args.length);
		indexer ind = new indexer();

		if (args.length == 2) {
			System.out.println(args[0]);
			System.out.println(args[1]);
			if (args[0].equals("-c")) {
				@SuppressWarnings("unused")
				makeCollection mc = new makeCollection(args[1]);
			} else if (args[0].equals("-k")) {
				@SuppressWarnings("unused")
				makeKeyword mk = new makeKeyword(args[1]);
			}
			else if (args[0].equals("-i")) {
		
				
			}

			
			
		} else {
			System.err.println("棵官弗 可记阑 涝仿窍技夸");
			System.exit(1);
		}

	}
	}



