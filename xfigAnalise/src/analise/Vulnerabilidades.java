package analise;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

//xfig
public class Vulnerabilidades {
	private ArrayList<String> temp;

	public Vulnerabilidades() throws Exception {
		temp = new ArrayList<>();

		getCVE("http://www.cvedetails.com/vulnerability-list.php?vendor_id=9795&product_id=&version_id=&page=1&hasexp=0&opdos=0&opec=0&opov=0&opcsrf=0&opgpriv=0&opsqli=0&opxss=0&opdirt=0&opmemc=0&ophttprs=0&opbyp=0&opfileinc=0&opginf=0&cvssscoremin=0&cvssscoremax=0&year=0&month=0&cweid=0&order=1&trc=4&sha=217fa5c283ec36c50ae41501298ed7d933ada4e1");

	}

	public ArrayList<String> getTemp() {
		return temp;
	}

	private void getCVE(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();

		Elements cves = doc.getElementsByClass("srrowns");

		for (int i = 0; i < cves.size(); i++) {     	
			temp.add(cves.get(i).getAllElements().get(0).text());
		}
	}
}
