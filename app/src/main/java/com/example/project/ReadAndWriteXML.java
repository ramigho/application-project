package com.example.project;

import android.content.Context;
import android.util.Xml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ReadAndWriteXML {

    public static void writeXML(Context context, UserReservation userReservation) {
        File file = context.getFileStreamPath("reservationdata.xml");
        if (!file.exists()) {
            try {
                XmlSerializer serializer = Xml.newSerializer();
                StringWriter writer = new StringWriter();
                serializer.setOutput(writer);
                FileOutputStream fileOutputStream = context.openFileOutput("reservationdata.xml", Context.MODE_APPEND);
                serializer.setOutput(writer);
                serializer.startDocument("UTF-8", true);
                serializer.startTag(null, "reservations");
                serializer.endTag(null, "reservations");
                serializer.endDocument();
                serializer.flush();
                fileOutputStream.write(writer.toString().getBytes());
                fileOutputStream.close();

            }  catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Element root = document.getDocumentElement();
            Element newReservation = document.createElement("reservation");

            Element userid = document.createElement("userid");
            userid.appendChild(document.createTextNode(userReservation.getUserID()));
            newReservation.appendChild(userid);

            Element hall = document.createElement("hall");
            hall.appendChild(document.createTextNode(userReservation.getHall()));
            newReservation.appendChild(hall);

            Element date = document.createElement("date");
            date.appendChild(document.createTextNode(userReservation.getDate()));
            newReservation.appendChild(date);

            Element time = document.createElement("time");
            time.appendChild(document.createTextNode(userReservation.getTime()));
            newReservation.appendChild(time);

            Element infoline = document.createElement("infoline");
            infoline.appendChild(document.createTextNode(userReservation.getInfoline()));
            newReservation.appendChild(infoline);

            root.appendChild(newReservation);

            DOMSource dsource = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file.getPath());
            transformer.transform(dsource, result);


        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList readSpesificXML(Context context, String chosenhall) {

        ArrayList<UserReservation> reservationList = new ArrayList<>();
        try {
            InputStream inputStream = context.openFileInput("reservationdata.xml");
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document XMLDoc = documentBuilder.parse(inputStream);
            NodeList nodeList = XMLDoc.getElementsByTagName("reservation");
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (XMLDoc.getElementsByTagName("hall").item(i).getTextContent().equals(chosenhall)) {
                    String id = XMLDoc.getElementsByTagName("userid").item(i).getTextContent();
                    String hall = XMLDoc.getElementsByTagName("hall").item(i).getTextContent();
                    String date = XMLDoc.getElementsByTagName("date").item(i).getTextContent();
                    String time = XMLDoc.getElementsByTagName("time").item(i).getTextContent();
                    String infoline = XMLDoc.getElementsByTagName("infoline").item(i).getTextContent();
                    UserReservation userReservation = new UserReservation(id, hall, date, time, infoline);
                    reservationList.add(userReservation);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
            return reservationList;
    }


    public static ArrayList readXML(Context context) {

        ArrayList<UserReservation> reservationList = new ArrayList<>();
        try {
            InputStream inputStream = context.openFileInput("reservationdata.xml");
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document XMLDoc = documentBuilder.parse(inputStream);
            NodeList nodeList = XMLDoc.getElementsByTagName("reservation");
            for (int i = 0; i < nodeList.getLength(); i++) {
                String id = XMLDoc.getElementsByTagName("userid").item(i).getTextContent();
                String hall = XMLDoc.getElementsByTagName("hall").item(i).getTextContent();
                String date = XMLDoc.getElementsByTagName("date").item(i).getTextContent();
                String time = XMLDoc.getElementsByTagName("time").item(i).getTextContent();
                String infoline = XMLDoc.getElementsByTagName("infoline").item(i).getTextContent();
                UserReservation userReservation = new UserReservation(id, hall, date, time, infoline);
                   reservationList.add(userReservation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return reservationList;
    }


    public static void  deleteRes(Context context, int position){
        File file = context.getFileStreamPath("reservationdata.xml");
        if (!file.exists()){
            try {
                XmlSerializer serializer = Xml.newSerializer();
                StringWriter writer = new StringWriter();
                serializer.setOutput(writer);
                FileOutputStream fileOutputStream = context.openFileOutput("reservationdata.xml", Context.MODE_APPEND);
                serializer.setOutput(writer);
                serializer.startDocument("UTF-8", true);
                serializer.startTag(null, "reservations");
                serializer.endTag(null, "reservations");
                serializer.endDocument();
                serializer.flush();
                fileOutputStream.write(writer.toString().getBytes());
                fileOutputStream.close();

            }  catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(context.openFileInput("reservationdata.xml"));

            NodeList nodeList = document.getElementsByTagName("reservation");
            Element element = null;

            element = (Element) nodeList.item(position);
            element.getParentNode().removeChild(element);

            DOMSource domSource = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file.getPath());
            transformer.transform(domSource, result);

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }
}