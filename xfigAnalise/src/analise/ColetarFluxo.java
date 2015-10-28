package analise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//xfig
public class ColetarFluxo {
	
	public ArrayList<String> getVulnerabilidadesLinks() throws IOException{
		ArrayList<String> vulnerabilitiesLinks = new ArrayList<String>();
		
		Connection connection = Jsoup.connect("http://www.cvedetails.com/vulnerability-list/vendor_id-9795/Xfig.html").timeout(300000);
		Document doc = connection.get();
		
		Pattern pattern = Pattern.compile("(http.*cvedetails.*CVE-[0-9]*-[0-9]*)");

		Elements links = doc.select("a[href]");
		
        for(Element l: links){
            String link = l.attr("abs:href");
            
            Matcher matcher = pattern.matcher(link);
            if(matcher.find()) {
            	String result = matcher.group();
            	vulnerabilitiesLinks.add(result);
            	//System.out.println("Vulnerabilidade: " + result);

            }
        }
        
        return vulnerabilitiesLinks;
	}
	
	public ArrayList<String> getBugsLinks(String url) throws IOException{
		ArrayList<String> bugsLinks = new ArrayList<String>();
		
		Connection connection = Jsoup.connect(url);
		Document doc = connection.get();
		
		Pattern pattern = Pattern.compile("(https.*bugzilla.*show_bug.*)");

		Elements links = doc.select("a[href]");
		
        for(Element l: links){
            String link = l.attr("abs:href");
            
            Matcher matcher = pattern.matcher(link);
            if(matcher.find()) {
            	String result = matcher.group();
            	bugsLinks.add(result);
            	//System.out.println("Bugs: " + result);

            }
        }
        
        return bugsLinks;
	}
	
	public ArrayList<String> getPatchLink(String url) throws IOException{
		ArrayList<String> bugsLinks = new ArrayList<String>();
		
		Connection connection = Jsoup.connect(url).timeout(300000);
		Document doc = connection.get();
		
		Pattern pattern = Pattern.compile("(https.*attachment.*diff)");

		Elements links = doc.select("a[href]");
		
        for(Element l: links){
            String link = l.attr("abs:href");
            
            Matcher matcher = pattern.matcher(link);
            if(matcher.find()) {
            	String result = matcher.group();
            	
            	int stringSize = result.length()-12;
            	result = result.substring(0,stringSize);
            	
            	if(checarLista(bugsLinks, result)){
            		bugsLinks.add(result);
                	//System.out.println("Patch links: " + result);
            	}

            }
        }
        
        return bugsLinks;
	}
	
	public boolean checarLista(ArrayList<String> al, String s){
		boolean b = true;
		
		for(int i = 0; i < al.size(); i++){
			if(al.get(i).equals(s)){
				b = false;
			}
		}
		
		return b;
	}
	
	public String getPatchDiff(String url) throws IOException{
		String patchDiff = "nada";
		
		Connection connection = Jsoup.connect(url);
		Document doc = connection.get();

		Elements result = doc.getAllElements();
		     	
		patchDiff = result.get(0).getAllElements().get(0).text();
		
		//System.out.println("Diff: " + patchDiff);
		
        //patchDiff = result;
        //System.out.println(result);
        
        return patchDiff;
	}
	
	public String getFile(String patch){
		String file = "";
		
		Pattern pattern = Pattern.compile("(\\+\\+\\+ .*\\.c)");
		
		Matcher matcher = pattern.matcher(patch);
        if(matcher.find()) {
        	String result = matcher.group();
        	file = result.substring(4, result.length());
        }
		
		return file;
	}
	
	public ArrayList<String> getFunction(String patch){
		ArrayList<String> function = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile("(?<=\\s+@@).*?\\s*@@(.*?\\()");
		
		Matcher matcher = pattern.matcher(patch);
        while(matcher.find()) {
        	String result = matcher.group();
        	function.add(result.substring(4, result.length()));
        }
		
		return function;
	}

}
