package hu.domparse.yk11q1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DomReadYk11q1 {
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        //XML fájl betöltése
        File xmlFile = new File("XMLYk11q1.xml");

        //Egy txt fájl létrehozása amibe kiíratjuk az XML fájl adatait
        FileWriter fileWriter = new FileWriter("XMLYk11q1.txt");

        //DocumentBuilderFactory példányosítása a newInstance() metódussal, ebből megkapjuk a DocumentBuildert
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        //A parse() metódussal megkapjuk a DOM dokumentumot az XML-ból
        Document doc = dBuilder.parse(xmlFile);

        //Dokumantum normalizálása
        doc.getDocumentElement().normalize();

        //Gyökér elem kiírása
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName() + "\n");
        System.out.println("----------------------------");

        //Team elem nodelistjének lekérdezése
        NodeList nList = doc.getElementsByTagName("team");

        //Végig megyünk a listán
        for (int i = 0; i < nList.getLength(); i++) {
            Node tNode = nList.item(i);
            System.out.println("Current element: " + tNode.getNodeName());
            if (tNode.getNodeType() == Node.ELEMENT_NODE) {

                // Elementé alakítjuk az aktuális elemet
                Element elem = (Element) tNode;

                //Lekérjük az aktuális elem atribútumának és gyerekelemeinek tartalmát
                String idT = elem.getAttribute("idT");
                String name = elem.getElementsByTagName("name").item(0).getTextContent();
                NodeList foundationList = elem.getElementsByTagName("foundation");
                String foundation = "";
                for (int j = 0; j < foundationList.getLength(); j++) {
                    Node dNode = foundationList.item(j);
                    if (dNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element dElem = (Element) dNode;
                        foundation = dElem.getElementsByTagName("year").item(0).getTextContent();
                        foundation += "-" + dElem.getElementsByTagName("month").item(0).getTextContent();
                        foundation += "-" + dElem.getElementsByTagName("day").item(0).getTextContent();
                    }
                }
                String rank = elem.getElementsByTagName("rank").item(0).getTextContent();

                //Adatok kiírása konzolra és a txt fájlba
                System.out.println("Team ID: " + idT);
                System.out.println("Name: " + name);
                System.out.println("Foundation: " + foundation);
                System.out.println("Rank: " + rank + "\n");
                fileWriter.write("Team ID: " + idT + "\nName: " + name + "\nFoundation: " + foundation + "\nRank: " + rank + "\n\n");
            }

        }

        System.out.println("------------------\n");
        fileWriter.write("\n------------------\n");
        //Supporter elem nodelistjének lekérdezése
        nList = doc.getElementsByTagName("supporter");

        //Végig megyünk a listán
        for (int i = 0; i < nList.getLength(); i++) {
            Node sNode = nList.item(i);
            System.out.println("Current element: " + sNode.getNodeName());
            if (sNode.getNodeType() == Node.ELEMENT_NODE) {

                // Elementé alakítjuk az aktuális elemet
                Element elem = (Element) sNode;

                //Lekérjük az aktuális elem atribútumának és gyerekelemeinek tartalmát
                String idT = elem.getAttribute("idS");
                String s_t = elem.getAttribute("s_t");
                String name = elem.getElementsByTagName("name").item(0).getTextContent();
                String amount = elem.getElementsByTagName("amount").item(0).getTextContent();
                NodeList nlValidity = elem.getElementsByTagName("validity");
                String validity = "";
                for (int j = 0; j < nlValidity.getLength(); j++) {
                    Node dNode = nlValidity.item(j);
                    if (dNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element dElem = (Element) dNode;
                        validity = dElem.getElementsByTagName("year").item(0).getTextContent();
                        validity += "-" + dElem.getElementsByTagName("month").item(0).getTextContent();
                        validity += "-" + dElem.getElementsByTagName("day").item(0).getTextContent();
                    }
                }

                //Adatok kiírása konzolra és a txt fájlba
                System.out.println("Supporter ID: " + idT);
                System.out.println("S_T FK: " + s_t);
                System.out.println("Name: " + name);
                System.out.println("Amount: " + amount);
                System.out.println("Validity: " + validity + "\n");
                fileWriter.write("Supporter ID: " + idT + "\nS_T FK: " + s_t + "\nName: " + name + "\nAmount: " + amount + "\nValidity: " + validity + "\n\n");
            }
        }

        System.out.println("------------------\n");
        fileWriter.write("\n------------------\n");

        //Supporter elem nodelistjének lekérdezése
        nList = doc.getElementsByTagName("player");

        //Végig megyünk a listán
        for (int i = 0; i < nList.getLength(); i++) {
            Node pNode = nList.item(i);
            System.out.println("Current element: " + pNode.getNodeName());
            if (pNode.getNodeType() == Node.ELEMENT_NODE) {

                // Elementé alakítjuk az aktuális elemet
                Element elem = (Element) pNode;

                //Lekérjük az aktuális elem atribútumának és gyerekelemeinek tartalmát
                String idP = elem.getAttribute("idP");
                String p_t = elem.getAttribute("p_t");
                String name = elem.getElementsByTagName("name").item(0).getTextContent();
                NodeList nlBirthdate = elem.getElementsByTagName("birthdate");
                String birthdate = "";
                for (int j = 0; j < nlBirthdate.getLength(); j++) {
                    Node dNode = nlBirthdate.item(j);
                    if (dNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eDate = (Element) dNode;
                        birthdate = eDate.getElementsByTagName("year").item(0).getTextContent();
                        birthdate += "-" + eDate.getElementsByTagName("month").item(0).getTextContent();
                        birthdate += "-" + eDate.getElementsByTagName("day").item(0).getTextContent();
                    }
                }
                String post = elem.getElementsByTagName("post").item(0).getTextContent();

                //Adatok kiírása konzolra és a txt fájlba
                System.out.println("Player ID: " + idP);
                System.out.println("P_T FK: " + p_t);
                System.out.println("Name: " + name);
                System.out.println("Birthdate: " + birthdate);
                System.out.println("Post: " + post + "\n");
                fileWriter.write("Player ID: " + idP + "\nP_T FK: " + p_t + "\nName: " + name + "\nBirthdate: " + birthdate + "\nPost: " + post + "\n\n");
            }
        }

        System.out.println("------------------\n");
        fileWriter.write("\n------------------\n");

        //Coach elem nodelistjének lekérdezése
        nList = doc.getElementsByTagName("coach");

        //Végig megyünk a listán
        for (int i = 0; i < nList.getLength(); i++) {
            Node cNode = nList.item(i);
            System.out.println("Current element: " + cNode.getNodeName());
            if (cNode.getNodeType() == Node.ELEMENT_NODE) {

                // Elementé alakítjuk az aktuális elemet
                Element cElem = (Element) cNode;

                //Lekérjük az aktuális elem atribútumának és gyerekelemeinek tartalmát
                String idC = cElem.getAttribute("idC");
                String c_t = cElem.getAttribute("c_t");
                String name = cElem.getElementsByTagName("name").item(0).getTextContent();
                NodeList nlBirthdate = cElem.getElementsByTagName("birthdate");
                String birthdate = "";
                for (int j = 0; j < nlBirthdate.getLength(); j++) {
                    Node nDate = nlBirthdate.item(j);
                    if (nDate.getNodeType() == Node.ELEMENT_NODE) {
                        Element eDate = (Element) nDate;
                        birthdate = eDate.getElementsByTagName("year").item(0).getTextContent();
                        birthdate += "-" + eDate.getElementsByTagName("month").item(0).getTextContent();
                        birthdate += "-" + eDate.getElementsByTagName("day").item(0).getTextContent();
                    }
                }
                String licence = cElem.getElementsByTagName("licence").item(0).getTextContent();

                //Adatok kiírása konzolra és a txt fájlba
                System.out.println("Coach ID: " + idC);
                System.out.println("C_T FK: " + c_t);
                System.out.println("Name: " + name);
                System.out.println("Birthdate: " + birthdate);
                System.out.println("Licence: " + licence + "\n");
                fileWriter.write("Coach ID: " + idC + "\nC_T FK: " + c_t + "\nName: " + name + "\nBirthdate: " + birthdate + "\nLicence: " + licence + "\n\n");
            }
        }

        System.out.println("------------------\n");
        fileWriter.write("\n------------------\n");

        //Match elem nodelistjének lekérdezése
        nList = doc.getElementsByTagName("match");

        //Végig megyünk a listán
        for (int i = 0; i < nList.getLength(); i++) {
            Node mNode = nList.item(i);
            System.out.println("Current element: " + mNode.getNodeName());
            if (mNode.getNodeType() == Node.ELEMENT_NODE) {

                // Elementé alakítjuk az aktuális elemet
                Element mElem = (Element) mNode;

                //Lekérjük az aktuális elem atribútumának és gyerekelemeinek tartalmát
                String m_t = mElem.getAttribute("m_t");
                String m_r = mElem.getAttribute("m_r");
                String location = mElem.getElementsByTagName("location").item(0).getTextContent();
                String date = mElem.getElementsByTagName("date").item(0).getTextContent();

                //Adatok kiírása konzolra és a txt fájlba
                System.out.println("M_T FK: " + m_t);
                System.out.println("M_R FK: " + m_r);
                System.out.println("Location: " + location);
                System.out.println("Date: " + date + "\n");
                fileWriter.write("M_T FK: " + m_t + "\nM_R FK: " + m_r + "\nLocation: " + location + "\nDate: " + date + "\n\n");
            }
        }

        System.out.println("------------------\n");
        fileWriter.write("\n------------------\n");

        //Referee elem nodelistjének lekérdezése
        nList = doc.getElementsByTagName("referee");

        //Végig megyünk a listán
        for (int i = 0; i < nList.getLength(); i++) {
            Node rNode = nList.item(i);
            System.out.println("Current element: " + rNode.getNodeName());
            if (rNode.getNodeType() == Node.ELEMENT_NODE) {

                // Elementé alakítjuk az aktuális elemet
                Element rElem = (Element) rNode;

                //Lekérjük az aktuális elem atribútumának és gyerekelemeinek tartalmát
                String idR = rElem.getAttribute("idR");
                String name = rElem.getElementsByTagName("name").item(0).getTextContent();
                NodeList nlBirthdate = rElem.getElementsByTagName("birthdate");
                String birthdate = "";
                for (int j = 0; j < nlBirthdate.getLength(); j++) {
                    Node nDate = nlBirthdate.item(j);
                    if (nDate.getNodeType() == Node.ELEMENT_NODE) {
                        Element eDate = (Element) nDate;
                        birthdate = eDate.getElementsByTagName("year").item(0).getTextContent();
                        birthdate += "-" + eDate.getElementsByTagName("month").item(0).getTextContent();
                        birthdate += "-" + eDate.getElementsByTagName("day").item(0).getTextContent();
                    }
                }

                //Adatok kiírása konzolra és a txt fájlba
                String licence = rElem.getElementsByTagName("licence").item(0).getTextContent();
                System.out.println("Referee ID: " + idR);
                System.out.println("Name: " + name);
                System.out.println("Birthdate: " + birthdate);
                System.out.println("Licence: " + licence + "\n");
                fileWriter.write("Referee ID: " + idR + "\nName: " + name + "\nBirthdate: " + birthdate + "\nLicence: " + licence + "\n\n");
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }
}