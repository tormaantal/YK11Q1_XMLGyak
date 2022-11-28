package hu.domparse.yk11q1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DomQueryYk11q1 {
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

            //40 évnél régebben alapított csapatok adatai
            System.out.println("40 évnél régebben alapított csapatok adatai:\n");
            NodeList teamList = doc.getElementsByTagName("team");
            for (int i = 0; i < teamList.getLength(); i++) {
                Node nNode = teamList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nNode;
                    NodeList nlFoundation = elem.getElementsByTagName("foundation");
                    String foundation = "";
                    for (int j = 0; j < nlFoundation.getLength(); j++) {
                        Node nDate = nlFoundation.item(j);
                        if (nDate.getNodeType() == Node.ELEMENT_NODE) {
                            Element eDate = (Element) nDate;
                            if (Integer.parseInt(eDate.getElementsByTagName("year").item(0).getTextContent()) < 1982) {
                                String idT = elem.getAttribute("idT");
                                String name = elem.getElementsByTagName("name").item(0).getTextContent();
                                foundation = eDate.getElementsByTagName("year").item(0).getTextContent();
                                foundation += "-" + eDate.getElementsByTagName("month").item(0).getTextContent();
                                foundation += "-" + eDate.getElementsByTagName("day").item(0).getTextContent();
                                String rank = elem.getElementsByTagName("rank").item(0).getTextContent();
                                System.out.println("Team ID: " + idT);
                                System.out.println("Name: " + name);
                                System.out.println("Foundation: " + foundation);
                                System.out.println("Rank: " + rank);
                                System.out.println();
                            }
                        }
                    }
                }
            }
            System.out.println("----------------------------");

            //Azon játékosok nevei, akik kapus poszton játszanak
            System.out.println("Kapusok nevei!\n");
            NodeList playerList = doc.getElementsByTagName("player");
            for (int i = 0; i < playerList.getLength(); i++) {
                Node nNode = playerList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nNode;
                    if (elem.getElementsByTagName("post").item(0).getTextContent().equals("Kapus")) {
                        System.out.println("Név: " + elem.getElementsByTagName("name").item(0).getTextContent());
                    }
                }
            }
            System.out.println("----------------------------");

            //Teljes támogatási összeg csapatonként
            System.out.println("Csapatok összes támogatása:\n");
            for (int i = 0; i < teamList.getLength(); i++) {
                Node teamNode = teamList.item(i);
                if (teamNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element teamElem = (Element) teamNode;
                    String teamId = teamElem.getAttribute("idT");
                    int amount = 0;
                    NodeList suppList = doc.getElementsByTagName("supporter");
                    for (int j = 0; j < suppList.getLength(); j++) {
                        Node suppNode = suppList.item(j);
                        if (suppNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element suppElem = (Element) suppNode;
                            String s_t = suppElem.getAttribute("s_t");
                            if (teamId.equals(s_t)) {
                                amount += Integer.parseInt(suppElem.getElementsByTagName("amount").item(0).getTextContent());
                            }
                        }
                    }
                    System.out.println("A(z) " + teamElem.getElementsByTagName("name").item(0).getTextContent() + " csapat támogatása: " + amount + " Ft!");
                }
            }
            System.out.println("----------------------------");

            //Mérkőzések helyszínei amit egyes bírók fújnak
            System.out.println("Bírók mérkőzéseinek helyszínei:\n");
            NodeList refereeList = doc.getElementsByTagName("referee");
            for (int i = 0; i < refereeList.getLength(); i++) {
                Node rNode = refereeList.item(i);
                if (rNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element rElem = (Element) rNode;
                    String idR = rElem.getAttribute("idR");
                    NodeList matchList = doc.getElementsByTagName("match");
                    for (int j = 0; j < matchList.getLength(); j++) {
                        Node mNode = matchList.item(j);
                        if (mNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element mElem = (Element) mNode;
                            String m_r = mElem.getAttribute("m_r");
                            if (idR.equals(m_r)) {
                                String name = rElem.getElementsByTagName("name").item(0).getTextContent();
                                String location = mElem.getElementsByTagName("location").item(0).getTextContent();
                                System.out.println(name + ": " + location);
                            }
                        }
                    }
                }
            }
            System.out.println("----------------------------");

            //Mennyi NBII-es csapat van és melyek azok?
            System.out.println("Mennyi NBII-es csapat van és melyek azok?\n");
            int counter = 0;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < teamList.getLength(); i++) {
                Node tNode = teamList.item(i);
                if (tNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element tElem = (Element) tNode;
                    String rank = tElem.getElementsByTagName("rank").item(0).getTextContent();
                    if ("NBII".equals(rank)) {
                        counter++;
                        result.append("-").append(tElem.getElementsByTagName("name").item(0).getTextContent()).append("\n");
                    }
                }
            }
            System.out.print(counter + " db van: \n" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
