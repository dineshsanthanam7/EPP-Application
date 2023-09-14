package com.example.epp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThiruunivActivity extends AppCompatActivity {
    private static final int PICK_PDF_REQUEST = 1;

    private Button selectPdfButton;

    private FirebaseFirestore db;
    String sem="null";
    Spinner s;

    private CollectionReference collectionReference;
    private DocumentReference documentReference;
    String [] sem_array={"Nov-Dec2020","April-May2021","Nov-Dec2021","April-May2022","Nov-Dec2022","April-May2023",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiruuniv);
        selectPdfButton = findViewById(R.id.select_pdf_button);

        db = FirebaseFirestore.getInstance();
        //Data for populating in Spinner


        s = findViewById(R.id.sem);
        ArrayAdapter adapter2= new
                ArrayAdapter(
                        ThiruunivActivity.this,android.R.layout.simple_spinner_item,sem_array);
        s.setAdapter(adapter2);


        selectPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            new PdfTextExtractorTask().execute(uri);
        }
    }

    private class PdfTextExtractorTask extends AsyncTask<Uri, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected String doInBackground(Uri... uris) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(uris[0]);
                PdfReader pdfReader = new PdfReader(inputStream);
                StringBuilder textBuilder = new StringBuilder();
                int numberOfPages = pdfReader.getNumberOfPages();

                for (int i = 1; i <= numberOfPages; i++) {
                    String text = PdfTextExtractor.getTextFromPage(pdfReader, i).trim();
                    textBuilder.append(text);
                    textBuilder.append(" ");
                }

                String strr=textBuilder.toString();
                String strNew=strr.replace("No of","");
                strr=strNew.replace("*********** End of statement *********","");
                String[]s1=strr.split("\\n");
                for(int y=0;y<s1.length;y++) {
                    if (s1[y].startsWith("College")) {
                        s1[y] = "";
                    }
                    if (s1[y].startsWith("SCIENCE")) {
                        s1[y] = "";
                    }

                    if (s1[y].startsWith("SubCode")) {
                        s1[y] = "";
                    }

                    if (s1[y].startsWith("Result")) {
                        s1[y] = "";
                    }
                    if (s1[y].startsWith("Course")) {
                        s1[y] = "";
                    }
                    if (s1[y].startsWith("NOTE")) {
                        s1[y] = "";
                    }
                    if (s1[y].startsWith("UNIVERSITY")) {

                        s1[y] = "";
                    }

                    if (s1[y].startsWith("No")) {

                        s1[y] = "";
                    }

                    if (s1[y].startsWith("Page:")) {

                        s1[y] = "";
                    }

                }


                List<String> list1=new ArrayList<String>();

                for(int i = 0; i < s1.length; i++){
                    list1.add(s1[i]) ;
                }

                list1.removeAll(Arrays.asList("", null));
                List<String> list2=new ArrayList<>();


                List<String> dataList = new ArrayList<>();
                Button sendButton = findViewById(R.id.button19);
                sendButton.setEnabled(true);
                sendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendButton.setVisibility(View.INVISIBLE);
                        //Toast.makeText(getBaseContext(),"processsing please wait ....", Toast.LENGTH_LONG).show();

                        String s;

                        int k = 0;
                        int p = 0;


                        for (String s1 : list1) {
                            s = s1;
                            p++;
                            if (s.startsWith("407")) {
                                k++;
                                p--;

                            }
                            dataList.add(s);


                            if (k == 2) {


                                dataList.remove(s);
                                k = 0;
                                break;
                            }
                        }
                        int m = dataList.size();
                        list1.subList(0, m).clear();

                        FirestoreAsyncTask task = new FirestoreAsyncTask();

                        // Execute the FirestoreAsyncTask
                        task.execute(dataList);
                        try {
                            Thread.sleep(500);
                            dataList.clear();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (!list1.isEmpty()) {

                            sendButton.performClick();
                        }
                        else{

                            Toast.makeText(getBaseContext(),"Data Stored Successfully check database", Toast.LENGTH_LONG).show();
                       sendButton.setVisibility(View.VISIBLE);

                        }


                        // Clear the dataList for the next iteration


                    }


                });

                pdfReader.close();
                return "null";
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String text) {
            if (text != null) {
               String j="ok";

            }

        }

        class FirestoreAsyncTask extends AsyncTask<List<String>, Void, Void> {

            @Override
            protected Void doInBackground(List<String>... lists) {
                List<String> dataList = lists[0];
                  sem=s.getSelectedItem().toString();
                String firstString = dataList.get(0);
                if (firstString.startsWith("407")) {
                    // Create a new document
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection(sem).document(firstString);

                    // Store the rest of the data under that document
                    Map<String, Object> dataMap = new HashMap<>();
                    for (int i = 1; i < dataList.size(); i++) {
                        String data = dataList.get(i);
                        dataMap.put("data" + i, data);
                    }
                    docRef.set(dataMap);
                   // Toast.makeText(getBaseContext(),"processsing please wait", Toast.LENGTH_LONG).show();

                }
                return null;
            }
        }


    }
}