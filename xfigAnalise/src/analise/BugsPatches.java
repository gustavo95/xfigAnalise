package analise;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
	
	public ArrayList<String> getPatches() throws IOException{
		ArrayList<String> patchs = new ArrayList<String>();
		
		Connection connection = Jsoup.connect("https://bugzilla.redhat.com/show_bug.cgi?id=1048127");
		Document doc = connection.get();

        //Elements ele = doc.getElementsByTag("a");
        Elements ele = doc.getElementsByClass("bz_patch");
        for (int i = 0; i < ele.size(); i++) {     	
        	patchs.add(ele.get(i).getAllElements().get(0).text());
        }
        
        return patchs;
	}
}
