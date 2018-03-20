package com.imaginary.poc.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;


public class TikaUtil {
		
	
	public  String parser2(String resourceLocation) throws IOException, SAXException, TikaException {
	    AutoDetectParser parser = new AutoDetectParser();
	    BodyContentHandler handler = new BodyContentHandler(-1);
	    Metadata metadata = new Metadata();
	    try (InputStream stream =  new FileInputStream(resourceLocation) ) {
	        parser.parse(stream, handler, metadata);
	        return handler.toString();
	    }
	}
	
	
	public  String parser(String resourceLocation) throws IOException, SAXException, TikaException {
	    AutoDetectParser parser = new AutoDetectParser();
	    BodyContentHandler handler = new BodyContentHandler(-1);
	    Metadata metadata = new Metadata();
	    InputStream stream =  new FileInputStream(resourceLocation);
	    parser.parse(stream, handler, metadata);
	    stream.close();
	    return handler.toString();

	}
	
	
	public  void parserHandler(String file , BodyContentHandler handler) throws IOException, SAXException, TikaException {
	    AutoDetectParser parser = new AutoDetectParser();
	    Metadata metadata = new Metadata();
	    try (InputStream stream =  new FileInputStream(file) ) {
	        parser.parse(stream, handler, metadata);
	     
	    }
	    
	}
	
	
	

}
