package com.example.benard.instantnews;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by benard on 9/27/16.
 */
public class ReadRss extends AsyncTask<Void,Void,Void> {
    Context context;
    ProgressDialog progressDialog;
    String address="http://rss.cnn.com/rss/edition.rss";
    URL url;

    //the following context is used to implement the progress Dialog
    //as shown below
    public ReadRss(Context context){
        this.context=context;
        progressDialog = new ProgressDialog(context);
        //the context gets the message to display on the progress dialog
      progressDialog.setMessage(context.getString(R.string.progressDialogText));

    }
    @Override
    protected void onPreExecute() {
      progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... params) {
        processXml (GetData());
        return null;
        }

    private void processXml(Document data) {
            if (data != null) {
                Log.d("Root",data.getDocumentElement().getNodeName());
                Element root=data.getDocumentElement();
                Node channel=root.getChildNodes().item(1);
                NodeList items=channel.getChildNodes();
                for (int i=0;i<items.getLength();i++){
                    Node currentchild=items.item(i);
                    if (currentchild.getNodeName().equalsIgnoreCase("item")){NodeList itemchilds=currentchild.getChildNodes();
                        for (int j=0;j<itemchilds.getLength();j++){
                            Node current=itemchilds.item(j);
                            Log.d("textcontent",currentchild.getTextContent());
                        }
                    }
                }
            }
        }

//making a HttpURLConnection
    public Document GetData(){
        try {
            url=new URL(address);
            //openning connection using URL object
            HttpURLConnection connection= (HttpURLConnection)url.openConnection();
                Log.d(ReadRss.class.getSimpleName(),connection.toString());
            connection.setRequestMethod("Get");
            InputStream inputStream=connection.getInputStream();
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=builderFactory.newDocumentBuilder();
            Document xmlDoc=builder.parse(inputStream);
            Log.d("ping",String.valueOf(xmlDoc));

            return xmlDoc;

        }
        catch (Exception e){
            e.printStackTrace();

            return null;
        }

    }

        }


