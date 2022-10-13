package saxyk11q1;
import javax.xml.parsers.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;

public class SaxYk11q1 {
    public static void main(String[] args) {
        try {
            SAXParserFactory fact = SAXParserFactory.newInstance();
            SAXParser saxParser = fact.newSAXParser();
            SaxHandler handler = new SaxHandler();
            saxParser.parse(new File("TA_orarend.xml"), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
class SaxHandler extends DefaultHandler{
    private int indent = 0;

    public String formatAttributes(Attributes attributes){
        int attLength = attributes.getLength();
        if (attLength == 0){
            return "";
        }
        StringBuilder sb = new StringBuilder(" {");
        for (int i = 0; i < attLength; i++) {
            sb.append(attributes.getLocalName(i) + "=" + attributes.getValue(i));
            if (i < attLength - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public void indent(){
        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }
    }

    public void startElement(String uri, String localName, String qname, Attributes attributes){
        indent++;
        indent();
        System.out.println(qname + formatAttributes(attributes) + " start");
    }

    public void endElement(String uri, String localName, String qname){
        indent();
        indent--;
        System.out.println(qname + " end");
    }

    public void characters(char ch[], int star, int length){
        String chars = new String(ch, star, length).trim();
        if(!chars.isEmpty()){
            indent++;
            indent();
            indent--;
            System.out.println(chars);
        }
    }
}

