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
public class BugsPatches {
	
	public ArrayList<String> getBugs() throws IOException{
		ArrayList<String> bugs = new ArrayList<String>();
		
		Connection connection = Jsoup.connect("https://bugzilla.redhat.com/buglist.cgi?component=xfig&product=Fedora");
		Document doc = connection.get();

        //Elements bugs = doc.getElementsByTag("a");
        Elements ele = doc.getElementsByClass("bz_bugitem");
        
        for (int i = 0; i < ele.size(); i++) {     	
        	bugs.add(ele.get(i).getAllElements().get(0).text());
        }
        
        return bugs;
	}
	
	public ArrayList<String> getBugsLinks() throws IOException{
		ArrayList<String> bugsLinks = new ArrayList<String>();
		
		Connection connection = Jsoup.connect("https://bugzilla.redhat.com/buglist.cgi?component=xfig&product=Fedora");
		Document doc = connection.get();
		
		Pattern pattern = Pattern.compile("(https://bugzilla.*show_bug.*)");

		Elements links = doc.select("a[href]");
        
        boolean naoDuplicado = true;
		
        for(Element l: links){
            String link = l.attr("abs:href");
            
            Matcher matcher = pattern.matcher(link);
    		if(matcher.find()) {
    			if(naoDuplicado){
    				String result = matcher.group();
        			bugsLinks.add(result);
        			System.out.println(result);
        			naoDuplicado = false;
    			} else{
    				naoDuplicado = true;
    			}
    		}
        }
        
        return bugsLinks;
	}
	
	public ArrayList<String> getAttachments() throws IOException{
		ArrayList<String> attachmentsLinks = new ArrayList<String>();
		ArrayList<String> bugsLinks = getBugsLinks();
		
		for(String bl: bugsLinks){
			Connection connection = Jsoup.connect(bl);
			Document doc = connection.get();

			Pattern pattern = Pattern.compile("(https://bugzilla.*com/attachment.*)");

			Elements links = doc.select("a[href]");

			for(Element al: links){
				String link = al.attr("abs:href");

				Matcher matcher = pattern.matcher(link);
				if(matcher.find()) {
					String result = matcher.group();
					attachmentsLinks.add(result);
					System.out.println(result);
				}
			}
		}

		return attachmentsLinks;
	}
}
