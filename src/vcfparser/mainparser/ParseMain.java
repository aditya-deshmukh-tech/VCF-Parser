package vcfparser.mainparser;

import java.io.IOException;
import java.util.List;

import vcfparser.conmodels.ContactModel;
import vcfparser.parser.VcfParser;

public class ParseMain {

	public static void main(String[] args) throws IOException {
		
		int cont =0;
		VcfParser p = new VcfParser();
		p.setVcfFile("C:/Users/admin/Desktop/VCF files/lava.vcf");
		List<ContactModel> l = p.getParsedVCF();
		
		for(ContactModel c : l) {
			cont++;
			System.out.println(c.getName()+"  "+c.getCellnum()+"  "+c.getWorknum());
		}
		

	}

}
