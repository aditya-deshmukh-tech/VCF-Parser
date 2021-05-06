package vcfparser.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vcfparser.conmodels.ContactModel;


public class VcfParser {
	
	private String filePath;
	//array for splited contacts
	private String[] cont;
	private List<ContactModel> l = new ArrayList<>();
	private int index = 0;
	
	//set file path through constructor
	public VcfParser(String filePath) {
		this.filePath = filePath;
	}
	
	public VcfParser() {
		
	}
	//set filepath through setter injection
	public void setVcfFile(String Filepath) {
		this.filePath = Filepath;
	}
	
	//convert vcf file into splitable format
	private void loadToTextFile() throws IOException {
		String t = "";
		String data = "";
		File f = new File(this.filePath);
		BufferedReader br = new BufferedReader(new FileReader(f));
		while((t = br.readLine()) != null) {
			if(t.contains("END:VCARD")) {
				data = data + t + "\n";
			}else {
				data = data+t+"\t";
			}
		}
		br.close();
		this.cont = data.split("\n");
	}
	
	//old parsing method using nested for loop
	/*public List<ContactModel> parseVCF() throws IOException{
		List<ContactModel> contacts = new ArrayList<>();
		
		for(String cont: sarr) {
			carr = cont.split("\t");
			ContactModel c = new ContactModel();
			for(String j:carr) {
				if(j.contains("FN:")) {
					c.setName(j.substring(j.indexOf("FN:")+3));
				}
				if(j.contains("TEL;CELL;PREF")) {
					c.setCellnum(j.substring(j.indexOf("TEL;CELL;PREF")+14));
				}else if(j.contains("TEL;CELL")) {
					c.setCellnum(j.substring(j.indexOf("TEL;CELL")+9));
				}
				
				if(j.contains("TEL;WORK")) {
					c.setWorknum(j.substring(j.indexOf("TEL;WORK")+9));
				}
			}
			contacts.add(c);
			carr = null;
		}
		
		return contacts;
	}*/
	
	//new parsing method using recursion technique
	private int ParseVCF(int index){
		if(index > this.cont.length-1) {
			return index;
		}
		ContactModel c = new ContactModel();
		for(String d:cont[index].split("\t")) {
			if(d.contains("FN:")) {
				c.setName(d.substring(d.indexOf("FN:")+3));
			}
			if(d.contains("TEL;CELL;PREF")) {
				if(d.contains("+91")) {
					c.setCellnum(d.substring(d.indexOf("TEL;CELL;PREF")+17));
				}else {
				c.setCellnum(d.replaceAll("[^0-9]", ""));
				}
			}else if(d.contains("TEL;CELL")) {
				if(d.contains("+91")) {
					c.setCellnum(d.substring(d.indexOf("TEL;CELL")+12));
				}else {
					c.setCellnum(d.replaceAll("[^0-9]", ""));
				}
			}
			
			if(d.contains("TEL;WORK")) {
				if(d.contains("+91")) {
					c.setWorknum(d.substring(d.indexOf("TEL;WORK")+12));
				}else {
					c.setCellnum(d.replaceAll("[^0-9]", ""));	
				}
			}
			
		}
		this.l.add(c);
		return this.ParseVCF(index+1);
	}
	
	//get list of contactmodel objects  
	public List<ContactModel> getParsedVCF() throws IOException{
		this.loadToTextFile();
		this.ParseVCF(index);
		return this.l;
	}
	
	
	

}
