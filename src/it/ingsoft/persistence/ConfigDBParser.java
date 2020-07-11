package it.ingsoft.persistence;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ConfigDBParser extends DefaultHandler {
	
	private Map<DBInstance,ConfigDB> dbs;
	
	private ConfigDB newDB;
	
	private boolean flagDbInstance;
	private boolean flagUrl;
	private boolean flagUsername;
	private boolean flagPassword;
	
	public ConfigDBParser() {
		this.dbs = new HashMap<>();
		
		this.flagDbInstance = false;
		this.flagUrl = false;
		this.flagUsername = false;
		this.flagPassword = false;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.flagDbInstance = qName.equals("db-instance");
		if(this.flagDbInstance)
		{
			this.newDB = new ConfigDB();
			this.dbs.put(DBInstance.valueOf(attributes.getValue("type")), this.newDB);
		}
		this.flagUrl = qName.equals("db-url");
		this.flagUsername = qName.equals("db-username");
		this.flagPassword = qName.equals("db-password");
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String str = new String(ch,start,length);
		
		if(this.flagUrl) this.newDB.setUrl(str);
		else if(this.flagUsername) this.newDB.setUsername(str);
		else if(this.flagPassword) this.newDB.setPassword(str);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		this.flagDbInstance = this.flagDbInstance && !qName.equals("db-instance");
		this.flagUrl = this.flagUrl && !qName.equals("db-url");
		this.flagUsername = this.flagUsername && !qName.equals("db-username");
		this.flagPassword = this.flagPassword && !qName.equals("db-password");
	}

	public ConfigDB getDB(DBInstance dbInstance)
	{
		return this.dbs.get(dbInstance);
	}
}
