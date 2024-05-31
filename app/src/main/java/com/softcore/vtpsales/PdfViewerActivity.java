package com.softcore.vtpsales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.print.PrintManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.BuildConfig;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.softcore.vtpsales.Adaptors.PdfPrintDocumentAdapter;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.databinding.ActivityPdfViewerBinding;
import com.softcore.vtpsales.databinding.ActivityQrScreenBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class PdfViewerActivity extends AppCompatActivity {

    private PDFView pdfView;
    private int currentPage = 0;
    private int totalPages = 0;
    ImageView btnPrevious;
    ImageView btnNext;

    ActivityPdfViewerBinding binding;

    String pdfUrl = "https://www.tutorialspoint.com/java/java_tutorial.pdf";

    String DocEntry = "";
    String CusName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPdfViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DocEntry = getIntent().getStringExtra("DocEntry");
        CusName = getIntent().getStringExtra("CusName");
        String DbKey = "";
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        switch (DbName) {
            case "ARRHUM_LIVE":
                DbKey = "A";
                break;
            case "ENVIIRO_LIVE":
                DbKey = "E";
                break;
            case "VTP_LIVE":
                DbKey = "V";
                break;
            case "WELWORTH_LIVE":
                DbKey = "W";
                break;
        }

        pdfUrl = "http://103.96.42.106:7279/api/sap/"+DbKey+"_Live_OpenARInvoice?DocEntry="+DocEntry+"&ObjectId=13";

        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        pdfView = findViewById(R.id.pdfView);

        binding.laybar.appbarTextView.setText("PDF View");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage < totalPages - 1) {
                    currentPage++;
                    pdfView.jumpTo(currentPage, true);
                } else {
                    Toast.makeText(PdfViewerActivity.this, "Already on the last page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 0) {
                    currentPage--;
                    pdfView.jumpTo(currentPage, true);
                } else {
                    Toast.makeText(PdfViewerActivity.this, "Already on the first page", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.laybar.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
                    String jobName = getString(R.string.app_name) + " Document";
                    printManager.print(jobName, new PdfPrintDocumentAdapter(getApplicationContext(), pdfUrl), null);
                } else {
                    Toast.makeText(getApplicationContext(), "Printing is not supported on this device", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.laybar.shareId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf();
            }
        });


        new Thread(() -> {
            try {
                downloadAndDisplayPdf(pdfUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sharePdf() {
        File pdfFile = new File(getCacheDir(), "Tax invoice.pdf");
        Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Share PDF using"));
    }


    private void downloadAndDisplayPdf(String pdfUrl) throws IOException {
        URL url = new URL(pdfUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.connect();

        File outputFile = new File(getCacheDir(), "temp.pdf");
        OutputStream outputStream = new FileOutputStream(outputFile);

        try (InputStream inputStream = connection.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } finally {
            outputStream.close();
        }

        ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(outputFile, ParcelFileDescriptor.MODE_READ_ONLY);
        PdfRenderer pdfRenderer = new PdfRenderer(parcelFileDescriptor);
        totalPages = pdfRenderer.getPageCount();

        runOnUiThread(() -> {
            pdfView.fromFile(outputFile)
                    .defaultPage(currentPage)
                    .onPageChange((page, pageCount) -> currentPage = page)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .enableSwipe(true)
                    .swipeHorizontal(true)
                    .enableDoubletap(true)
                    .enableAnnotationRendering(true)
                    .password(null)
                    .spacing(20)
                    .scrollHandle(null)
//                    .pageSnap(true)
//                    .autoSpacing(true)
//                    .pageFling(true)
                    .load();

            System.out.println("currentPage:"+currentPage);
        });

        pdfRenderer.close();
        parcelFileDescriptor.close();
    }







}
