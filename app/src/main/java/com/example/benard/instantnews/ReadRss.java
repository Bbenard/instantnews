package com.example.benard.instantnews;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
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
    String address="http://www.sciencemag.org/rss/news_current.xml";
    URL url;

    public ReadRss(Context context){
        this.context=context;
        progressDialog = new ProgressDialog(context);
      progressDialog.setMessage("Loading....");

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
    protected Void doInBackground(Void... voids) {
        processXml (GetData());
        return null;
        }

    private void processXml(Document data) {
        if (data != null) {
            if (data != null) {
                Element root=data.getDocumentElement();
                Node channel=root.getChildNodes().item(1);
                NodeList items=channel.getChildNodes();
                for (int i=0;i<items.getLength();i++){
                    Node cureentchild=items.item(i);
                    if (cureentchild.getNodeName().equalsIgnoreCase("item")){
                        NodeList itemchilds=cureentchild.getChildNodes();
                        for (int j=0;j<itemchilds.getLength();j++){
                            Node cureent=itemchilds.item(j);
                            Log.d("textcontent",cureent.getTextContent());
                        }
                    }
                }
            }
        }
    }

    public Document GetData(){
        try {
            url=new URL(address);
            HttpURLConnection connection= (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("Get");
            InputStream inputStream=connection.getInputStream();
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=builderFactory.newDocumentBuilder();
            Document xmlDoc=builder.parse(inputStream);
            return xmlDoc;

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

        }


