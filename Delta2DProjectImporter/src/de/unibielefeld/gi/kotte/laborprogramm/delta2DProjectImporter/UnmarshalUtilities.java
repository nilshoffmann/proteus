/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 * @author hoffmann
 */
public class UnmarshalUtilities {

	public static <T> T unmarshal(final Class<T> targetClass, final String contextPath, final String schemaPath, File document) {
		System.out.println("Trying to unmarshal "+targetClass.getName()+" with dtd at "+contextPath+ " and schemaPath: "+schemaPath+" for document: "+document);
		URL schemaURL = UnmarshalUtilities.class.getResource(schemaPath);
		try {
			InputStream is = schemaURL.openStream();
		} catch (IOException ex) {
			Logger.getLogger(UnmarshalUtilities.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(document);
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
//			parserFactory.setValidating(true);
//			parserFactory.setNamespaceAware(true);  
			SAXParser saxParser = parserFactory.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();
			InputSource inputSource = new InputSource(fis);
			EntityResolver entityResolver = new EntityResolver() {
				@Override
				public InputSource resolveEntity(String publicId, String systemId) {
					return new InputSource(UnmarshalUtilities.class.getResourceAsStream(schemaPath));//"com/decodon/dtd/delta2d/GelData.dtd"));
				}
			};
			xmlReader.setEntityResolver(entityResolver);
			SAXSource saxSource = new SAXSource(xmlReader, inputSource);
			Unmarshaller unmarshaller = JAXBContext.newInstance(contextPath).createUnmarshaller();
			ValidationEventCollector eventCollector = new ValidationEventCollector();
			unmarshaller.setEventHandler(eventCollector);
			Object unmarshalledData = unmarshaller.unmarshal(saxSource);
			if (eventCollector.hasEvents()) {
				for (ValidationEvent event : eventCollector.getEvents()) {
//					log.error(event.getMessage(), event.getLinkedException());
					System.err.println(event.getMessage());
					System.err.println(event.getLinkedException());
				}
//				return null;
			}
			return targetClass.cast(unmarshalledData);
		} catch (ParserConfigurationException ex) {
			Logger.getLogger(UnmarshalUtilities.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		} catch (SAXException ex) {
			Logger.getLogger(UnmarshalUtilities.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		} catch (JAXBException ex) {
			Logger.getLogger(UnmarshalUtilities.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		} catch (FileNotFoundException ex) {
			Logger.getLogger(UnmarshalUtilities.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				}catch(IOException e) {
					System.err.println(e);
				}
			}
		}

	}
}
