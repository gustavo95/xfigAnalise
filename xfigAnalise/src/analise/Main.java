package analise;

import java.util.ArrayList;

//xfig
public class Main {

	public static void main(String[] args) throws Exception {
		ColetarFluxo cf = new ColetarFluxo();
		
		ArrayList<String> vulnerabilidades = cf.getVulnerabilidadesLinks();
		ArrayList<String> bugs;
		ArrayList<String> patch;
		ArrayList<String> functions;
		String diff = "";
		
		for(String vLink : vulnerabilidades){
			System.out.println("\nVulnerabilidade: " + vLink);
			bugs = cf.getBugsLinks(vLink);
			for(String bLink : bugs){
				System.out.println("Bug: " + bLink);
				patch = cf.getPatchLink(bLink);
				for(String pLink : patch){
					System.out.println("Patch: " + pLink);
					diff = cf.getPatchDiff(pLink);
					System.out.println("Diff: " + diff);
					System.out.println("File: " + cf.getFile(diff));
					
					functions = cf.getFunction(diff);
					for(String f : functions){
						System.out.println("Function: "  + f);
					}
				}
			}
		}
	}

}
