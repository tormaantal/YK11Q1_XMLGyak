package hu.domparse.yk11q1;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DomModifyYk11q1 {
    public static void main(String[] args) {
        try {

            //XML file betöltése
            File inputFile = new File("XMLYk11q1.xml");

            //DocumentBuilderFactory példányosítása a newInstance() metódussal, ebből megkapjuk a DocumentBuildert
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            //A parse() metódussal megkapjuk a DOM dokumentumot az XML-ból
            Document doc = dBuilder.parse(inputFile);

            //Dokumantum normalizálása
            doc.getDocumentElement().normalize();

            //Gyökér elem kiírása
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");

            //Minden játékos posztját módosítjuk egy véletlenszerűen kiválasztottra!
            System.out.println("Postok módosítása minden játékosnál!\n");
            NodeList playerList = doc.getElementsByTagName("player");
            StringBuilder result = new StringBuilder();
            int random;
            for (int i = 0; i < playerList.getLength(); i++) {
                Node pNode = playerList.item(i);
                if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element pElem = (Element) pNode;
                    Node post = pElem.getElementsByTagName("post").item(0);
                    String name = pElem.getElementsByTagName("name").item(0).getTextContent();
                    String[] posts = {"Kapus", "Szélső", "Beálló", "Irányító", "Átlövő"};
                    random = (int) (Math.random() * posts.length);
                    result.append(name).append(" [eredeti post: ").append(post.getTextContent());
                    post.setTextContent(posts[random]);
                    result.append(", új post: ").append(post.getTextContent()).append("]\n");
                }
            }
            System.out.print(result);
            System.out.println("----------------------------");

            //Bírói licence vizsga: eggyel magasabb kategóriába kerül, ha a legmagasabban van akkor azt jelzi
            System.out.println("Bírói licence vizsga!\n");
            NodeList refereeList = doc.getElementsByTagName("referee");
            for (int i = 0; i < refereeList.getLength(); i++) {
                Node rNode = refereeList.item(i);
                if (rNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) rNode;
                    Node licence = elem.getElementsByTagName("licence").item(0);
                    switch (licence.getTextContent()) {
                        case "A":
                            System.out.println(elem.getElementsByTagName("name").item(0).getTextContent() + " már a legmagasabb licenccel rendelkezik!");
                            break;
                        case "B":
                            licence.setTextContent("A");
                            System.out.println(elem.getElementsByTagName("name").item(0).getTextContent() + " " + licence.getTextContent() + " licencet kapott!");
                            break;
                        case "C":
                            licence.setTextContent("B");
                            System.out.println(elem.getElementsByTagName("name").item(0).getTextContent() + " " + licence.getTextContent() + " licencet kapott!");
                            break;
                        case "D":
                            licence.setTextContent("C");
                            System.out.println(elem.getElementsByTagName("name").item(0).getTextContent() + " " + licence.getTextContent() + " licencet kapott!");
                            break;
                    }
                }
            }
            System.out.println("----------------------------");

            //Támogatás meghosszabbítása 1 és 10 közötti évek számával
            System.out.println("Minden támogató szerződés hosszabítása!\n");
            NodeList supporterList = doc.getElementsByTagName("supporter");
            result = new StringBuilder();
            for (int i = 0; i < supporterList.getLength(); i++) {
                Node sNode = supporterList.item(i);
                if (sNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element sElem = (Element) sNode;
                    NodeList date = sElem.getElementsByTagName("validity");
                    result.append(sElem.getElementsByTagName("name").item(0).getTextContent());
                    for (int j = 0; j < date.getLength(); j++) {
                        Node nDate = date.item(j);
                        if (nDate.getNodeType() == Node.ELEMENT_NODE) {
                            Element dElem = (Element) nDate;
                            Node year = dElem.getElementsByTagName("year").item(0);
                            String month = dElem.getElementsByTagName("month").item(0).getTextContent();
                            String day = dElem.getElementsByTagName("day").item(0).getTextContent();
                            result.append(" [előtte: ").append(year.getTextContent()).append("-").append(month).append("-").append(day);
                            random = 1 + (int) (Math.random() * 11);
                            year.setTextContent(String.valueOf(Integer.parseInt(year.getTextContent()) + random));
                            result.append(", utánna: ").append(year.getTextContent()).append("-").append(month).append("-").append(day).append("]\n");
                        }
                    }
                }
            }
            System.out.println(result);
            System.out.println("----------------------------");

            //Átigazolás 2 random játékosnál random csapathoz
            System.out.println("Két játékos átigazolása!\n");
            String[] idTs = {"t1", "t2", "t3", "t4", "t5"};
            result = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                random = (int) (Math.random() * playerList.getLength());
                Node pNode = playerList.item(random);
                if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element pElem = (Element) pNode;
                    result.append(pElem.getElementsByTagName("name").item(0).getTextContent()).append(" [előtte: ").append(pElem.getAttribute("p_t"));
                    pElem.setAttribute("p_t", idTs[(int) (Math.random() * idTs.length)]);
                    result.append(", utánna: ").append(pElem.getAttribute("p_t")).append("]\n");
                }
            }
            System.out.println(result);
            System.out.println("----------------------------");

                //DVTK csapat rank módosítás
            NodeList temaList = doc.getElementsByTagName("team");
            result = new StringBuilder();
            for (int i = 0; i < temaList.getLength(); i++) {
                Node tNode = temaList.item(i);
                if (tNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element tElem = (Element) tNode;
                    Node rank = tElem.getElementsByTagName("rank").item(0);
                    String name = tElem.getElementsByTagName("name").item(0).getTextContent();
                    if ("DVTK".equals(name)){
                        result.append(name).append(" [előtte: ").append(rank.getTextContent());
                        rank.setTextContent("NBI/B");
                        result.append(", utánna: ").append(rank.getTextContent()).append("]\n");
                    }
                }
            }
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
