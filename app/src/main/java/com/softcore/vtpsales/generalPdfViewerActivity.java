package com.softcore.vtpsales;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.databinding.ActivityPdfViewerBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class generalPdfViewerActivity extends AppCompatActivity {

    private PDFView pdfView;
    private int currentPage = 0;
    private int totalPages = 0;
    ImageView btnPrevious;
    ImageView btnNext;

    ActivityPdfViewerBinding binding;


    String TYPE = "";
    String SortBy ="";
    List<CusReportWiseModel> Mainlist ;

     String CompnyName;
     String CompnyAddr;
     String Building;
     String IntrntAdrs;
     String Phone1;
     String E_Mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPdfViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SortBy = getIntent().getStringExtra("SortBy");
        TYPE = getIntent().getStringExtra("ReportTYPE");
         CompnyName = AppUtil.getStringData(getApplicationContext(),"ComName","");
         CompnyAddr = AppUtil.getStringData(getApplicationContext(),"ComAddr","");
         Building = AppUtil.getStringData(getApplicationContext(),"ComBuilding","");
         IntrntAdrs = AppUtil.getStringData(getApplicationContext(),"ComWebsite","");
         Phone1 = AppUtil.getStringData(getApplicationContext(),"ComPhone1","");
         E_Mail = AppUtil.getStringData(getApplicationContext(),"ComE_Mail","");

        Mainlist = new ArrayList<>();
        List<CusReportWiseModel> list = (List<CusReportWiseModel>) getIntent().getSerializableExtra("ReportList");
        Mainlist = list;

        System.out.println("Mainlist Size"+String.valueOf(Mainlist.size())+" "+ SortBy+" "+TYPE);

        fetchData();

        createPDF2();


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
                    Toast.makeText(generalPdfViewerActivity.this, "Already on the last page", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(generalPdfViewerActivity.this, "Already on the first page", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.laybar.print.setVisibility(View.GONE);

        binding.laybar.shareId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf();
            }
        });

    }

    private void fetchData() {

    }


    private void sharePdf() {
        File pdfFile = new File(getCacheDir(), "Invoice.pdf");
        Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Share PDF using"));
    }

    private void downloadAndDisplayPdf(File file) throws IOException {
//        URL url = new URL(pdfUrl);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.setDoOutput(true);
//        connection.connect();
//
//        File outputFile = new File(getCacheDir(), "temp.pdf");
       // OutputStream outputStream = new FileOutputStream(file);

//        try (InputStream inputStream = connection.getInputStream()) {
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//        } finally {
//            outputStream.close();
//        }

        ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        PdfRenderer pdfRenderer = new PdfRenderer(parcelFileDescriptor);
        totalPages = pdfRenderer.getPageCount();

        runOnUiThread(() -> {
            pdfView.fromFile(file)
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

    private void createPDF2() {
        // Create a new document
        PdfDocument document = new PdfDocument();

        // Page properties
        int pageWidth = 595;
        int pageHeight = 842;
        int margin = 30;
        int headerHeight = 120;
        int itemHeight = 55;
        int itemsPerPage = 600 / itemHeight;

        // Create a Paint object
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(16);

        // Initialize random and company information
        Random random = new Random();
        String companyAddress = "Company Address: " + CompnyAddr;
        String contact = "Contact: " + Phone1;
        String email = "Email: " + E_Mail;
        String CompanyName = CompnyName;
        String WebSite = "Company Website: " + IntrntAdrs;

        int totalAmt = 0;
        int itemCount = Mainlist.size(); // Total number of items

        int currentY = 0; // Track current Y position across pages

        for (int pageIndex = 0; pageIndex <= (itemCount / itemsPerPage); pageIndex++) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageIndex + 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();

            // Draw the company information header
            int startY = margin;
//            canvas.drawText(CompanyName, margin, startY, paint);
//            canvas.drawText(companyAddress, margin, startY + 20, paint);
//            canvas.drawText(contact, margin, startY + 40, paint);
//            canvas.drawText(email, margin, startY + 60, paint);
//            canvas.drawText(WebSite, margin, startY + 80, paint);

            startY = drawTextWithWrapping(canvas, CompanyName, margin, startY, paint, pageWidth - margin * 2);
            startY = drawTextWithWrapping(canvas, companyAddress, margin, startY, paint, pageWidth - margin * 2);
            startY = drawTextWithWrapping(canvas, contact, margin, startY, paint, pageWidth - margin * 2);
            startY = drawTextWithWrapping(canvas, email, margin, startY, paint, pageWidth - margin * 2);
            startY = drawTextWithWrapping(canvas, WebSite, margin, startY, paint, pageWidth - margin * 2);


            // Draw item list header
            canvas.drawText("Item Name", margin, startY +30, paint);
            canvas.drawText("Amount", pageWidth - margin - 100, startY+30 , paint);

            // Reset currentY for each page
            currentY = startY + +30+ itemHeight;

            // Draw items for the current page
            for (int i = pageIndex * itemsPerPage; i < Math.min((pageIndex + 1) * itemsPerPage, itemCount); i++) {
                CusReportWiseModel model = Mainlist.get(i);
                String itemName = getItemName(model);

                double Tamt = getItemAmount(model);
                DecimalFormat df = new DecimalFormat("0.00");
                String formattedTamt = df.format(Tamt);

                totalAmt += Tamt;

                // Split the item name if it's too long
                List<String> itemNameLines = splitItemName(i+1 + ". " + itemName, pageWidth - margin - 100);

                // Draw item name and amount
                currentY = drawItemNameLines(canvas, itemNameLines, margin, currentY, paint, pageWidth, formattedTamt);
            }

            // Check if this is the last page
            if (pageIndex == itemCount / itemsPerPage) {
                paint.setFakeBoldText(true);
                canvas.drawText("Total Amount", margin, currentY, paint);
                canvas.drawText("₹ " + totalAmt, pageWidth - margin - 100, currentY, paint);
            }

            document.finishPage(page);
        }

        // Write the document content to a temporary file
        File file = new File(getCacheDir(), "Invoice.pdf");

        try {
            document.writeTo(new FileOutputStream(file));
            Log.d("MainActivity", "PDF created at: " + file.getAbsolutePath());
            viewPDF(file);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing PDF: " + e.toString());
        } finally {
            // Close the document
            document.close();
        }
    }


    private String getItemName(CusReportWiseModel model) {
        if (model.getCustomerName() == null || model.getCustomerName().equals("")) {
            if ((model.getCustomer_Name() == null || model.getCustomer_Name().equals(""))) {
                if ((model.getVendor_Name() == null || model.getVendor_Name().equals(""))) {
                    return "-";
                } else {
                    return model.getVendor_Name();
                }
            } else {
                return model.getCustomer_Name();
            }
        } else {
            return model.getCustomerName();
        }
    }

    private double getItemAmount(CusReportWiseModel model) {
        double Tamt = 0;
        switch (SortBy) {
            case "Net":
                if (TYPE.equals("Sales")) {
                    Tamt += Double.parseDouble(model.getNetAmtINV_CRN());
                } else if (TYPE.equals("Customer Outstanding")) {
                    Tamt += Double.parseDouble(model.getNetAmtINV_ARCRN());
                } else if (TYPE.equals("Purchase") || TYPE.equals("Vendor Outstanding")) {
                    Tamt += Double.parseDouble(model.getNetAmtApCrn());
                }
                break;
            case "Gross":
                if (TYPE.equals("Sales")) {
                    Tamt += Double.parseDouble(model.getGrossAmtINV_CRN());
                } else if (TYPE.equals("Customer Outstanding")) {
                    Tamt += Double.parseDouble(model.getGrossAmtINV_ARCRN());
                } else if (TYPE.equals("Purchase") || TYPE.equals("Vendor Outstanding")) {
                    Tamt += Double.parseDouble(model.getGrossAmtApCrn());
                }
                break;
        }
        return Tamt;
    }

    private List<String> splitItemName(String itemName, int maxLength) {
        List<String> lines = new ArrayList<>();
        while (itemName.length() > maxLength) {
            int spaceIndex = itemName.lastIndexOf(' ', maxLength);
            if (spaceIndex == -1) {
                spaceIndex = maxLength;
            }
            lines.add(itemName.substring(0, spaceIndex));
            itemName = itemName.substring(spaceIndex + 1);
        }
        lines.add(itemName);
        return lines;
    }

    private int drawItemNameLines(Canvas canvas, List<String> lines, int x, int y, Paint paint, int pageWidth, String formattedTamt) {
        int itemHeight = 25; // Assuming item height is fixed at 30 pixels
        int itemWidth = pageWidth - x - 160; // Width available for item name
        boolean amountDrawn = false;

        for (String line : lines) {
            if (paint.measureText(line) <= itemWidth) {
                // If the entire line can be drawn within the available width
                canvas.drawText(line, x, y, paint);
                y += itemHeight; // Move to the next line
            } else {
                // If the line needs to be split
                String remainingText = line;
                while (paint.measureText(remainingText) > itemWidth) {
                    int splitIndex = paint.breakText(remainingText, true, itemWidth, null);
                    String splitText = remainingText.substring(0, splitIndex);
                    canvas.drawText(splitText, x, y, paint);
                    y += itemHeight; // Move to the next line
                    remainingText = remainingText.substring(splitIndex);
                }
                canvas.drawText(remainingText, x, y, paint);
                y += itemHeight; // Move to the next line
            }

            // Draw the amount on the right if it hasn't been drawn yet
            if (!amountDrawn) {
                canvas.drawText("₹ " + formattedTamt, pageWidth - x - 110, y - itemHeight * lines.size(), paint);
                amountDrawn = true;
            }


            // Add margin above the line
             // Adjust this value as needed for the margin
            // Draw a horizontal line after each item
            canvas.drawLine(x, y-10, pageWidth - x, y-10, paint);

            // Add some spacing below the line
            y += 20; // Adjust this value as needed for spacing between items
        }

        return y;
    }
    private int drawTextWithWrapping(Canvas canvas, String text, int x, int y, Paint paint, int maxWidth) {
        int lineHeight = (int) (paint.descent() - paint.ascent());
        int start = 0;
        int end;

        while (start < text.length()) {
            end = paint.breakText(text, start, text.length(), true, maxWidth, null);
            canvas.drawText(text, start, start + end, x, y, paint);
            start += end;
            y += lineHeight;
        }

        return y;
    }

//
//    private void createPDF2() {
//        // Create a new document
//        PdfDocument document = new PdfDocument();
//
//        // Page properties
//        int pageWidth = 595;
//        int pageHeight = 842;
//        int margin = 80;
//        int headerHeight = 120;
//        int itemHeight = 30;
//        int itemsPerPage = (pageHeight - margin - headerHeight) / itemHeight;
//
//        // Create a Paint object
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(16);
//
//        // Initialize random and company information
//        Random random = new Random();
//        String companyAddress = "Company Address: " +CompnyAddr;
//        String contact = "Contact: " + Phone1;
//        String email = "Email: "+E_Mail;
//        String CompanyName = CompnyName;
//        String WebSite = "Company Website: "+IntrntAdrs;
//
//        int totalAmt = 0;
//        int itemCount = Mainlist.size(); // Total number of items
//
//
//        for (int pageIndex = 0; pageIndex <= (itemCount / itemsPerPage); pageIndex++) {
//            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageIndex + 1).create();
//            PdfDocument.Page page = document.startPage(pageInfo);
//
//            Canvas canvas = page.getCanvas();
//
//            // Draw the company information header
//            int startY = margin;
//            canvas.drawText(CompanyName, margin, startY, paint);
//            canvas.drawText(companyAddress, margin, startY + 20, paint);
//            canvas.drawText(contact, margin, startY + 40, paint);
//            canvas.drawText(email, margin, startY + 60, paint);
//            canvas.drawText(WebSite, margin, startY + 80, paint);
//
//            // Draw item list header
//            canvas.drawText("Item Name", margin, startY + headerHeight, paint);
//            canvas.drawText("Amount", pageWidth - margin - 100, startY + headerHeight, paint);
//
//            // Draw items for the current page
//            int currentY = startY + headerHeight + itemHeight;
//            for (int i = pageIndex * itemsPerPage; i < Math.min((pageIndex + 1) * itemsPerPage, itemCount); i++) {
////                String itemName = "Item " + i;
////                int amount = random.nextInt(500) + 1;
////                totalAmt += amount;
//
//                CusReportWiseModel model = Mainlist.get(i);
//                String itemName = "";
//                if(model.getCustomerName() == null || model.getCustomerName().equals("")){
//
//                    if((model.getCustomer_Name() == null || model.getCustomer_Name().equals(""))){
//                        if((model.getVendor_Name() == null || model.getVendor_Name().equals(""))){
//                            itemName = "-";
//                        }else{
//                            itemName = model.getVendor_Name();
//                        }
//                    }else{
//                        itemName = model.getCustomer_Name();
//                    }
//                }
//                else {
//                    itemName =  model.getCustomerName();
//                }
//
//                double Tamt = 0;
//                switch (SortBy) {
//                    case "Net":
//                        if(TYPE.equals("Sales")){
//                            Tamt += Double.parseDouble(model.getNetAmtINV_CRN());
//                        }
//                        else if(TYPE.equals("Customer Outstanding")){
//                            Tamt += Double.parseDouble(model.getNetAmtINV_ARCRN());
//                        }
//                        else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
//                            Tamt += Double.parseDouble(model.getNetAmtApCrn());
//                        }
//
//                        break;
//                    case "Gross":
//                        if(TYPE.equals("Sales")){
//                            Tamt += Double.parseDouble(model.getGrossAmtINV_CRN());
//                        }
//                        else if(TYPE.equals("Customer Outstanding")){
//                            Tamt += Double.parseDouble(model.getGrossAmtINV_ARCRN());
//                        }
//                        else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
//                            Tamt += Double.parseDouble(model.getGrossAmtApCrn());
//                        }
//                        break;
//                }
//
//                DecimalFormat df = new DecimalFormat("0.00");
//                String formattedTamt = df.format(Tamt);
//
//                totalAmt += Tamt;
//               // canvas.drawText(itemName, margin, currentY, paint);
////                canvas.drawText("$" + amount, pageWidth - margin - 100, currentY, paint);
////                currentY += itemHeight;
//
//
//                                // Split the item name if it's too long
//                List<String> itemNameLines = splitItemName(i+" "+itemName, pageWidth - margin - 100);
//
//                // Draw item name and amount
//                int currentYBefore = currentY;
//                currentY = drawItemNameLines(canvas, itemNameLines, margin, currentY, paint, pageWidth, formattedTamt);
//
//            }
//
//            // Check if this is the last page
//            if (pageIndex == itemCount / itemsPerPage) {
//                paint.setFakeBoldText(true);
//                canvas.drawText("Total Amount", margin, currentY, paint);
//                canvas.drawText("₹ " + totalAmt, pageWidth - margin - 100, currentY, paint);
//            }
//
//            document.finishPage(page);
//        }
//
//        // Write the document content to a temporary file
//        File file = new File(getCacheDir(), "Invoice.pdf");
//
//        try {
//            document.writeTo(new FileOutputStream(file));
//            Log.d("MainActivity", "PDF created at: " + file.getAbsolutePath());
//            viewPDF(file);
//        } catch (IOException e) {
//            Log.e("MainActivity", "Error writing PDF: " + e.toString());
//        } finally {
//            // Close the document
//            document.close();
//        }
//    }
//
//
//
//    private List<String> splitItemName(String itemName, int maxLength) {
//        List<String> lines = new ArrayList<>();
//        while (itemName.length() > maxLength) {
//            int spaceIndex = itemName.lastIndexOf(' ', maxLength);
//            if (spaceIndex == -1) {
//                spaceIndex = maxLength;
//            }
//            lines.add(itemName.substring(0, spaceIndex));
//            itemName = itemName.substring(spaceIndex + 1);
//        }
//        lines.add(itemName);
//        return lines;
//    }
//
//    private int drawItemNameLines(Canvas canvas, List<String> lines, int x, int y, Paint paint, int pageWidth, String formattedTamt) {
//        int itemHeight = 30; // Assuming item height is fixed at 30 pixels
//        int itemWidth = pageWidth - x - 200; // Width available for item name
//        boolean amountDrawn = false;
//
//        for (String line : lines) {
//
//            if (paint.measureText(line) <= itemWidth) {
//                // If the entire line can be drawn within the available width
//                canvas.drawText(line, x, y, paint);
//                y += itemHeight; // Move to the next line
//            } else {
//                // If the line needs to be split
//                String remainingText = line;
//                while (paint.measureText(remainingText) > itemWidth) {
//                    int splitIndex = paint.breakText(remainingText, true, itemWidth, null);
//                    String splitText = remainingText.substring(0, splitIndex);
//                    canvas.drawText(splitText, x, y, paint);
//                    y += itemHeight; // Move to the next line
//                    remainingText = remainingText.substring(splitIndex);
//                }
//                canvas.drawText(remainingText, x, y, paint);
//                y += itemHeight; // Move to the next line
//            }
//        }
//
//        // Draw the amount on the right if it hasn't been drawn yet
//        if (!amountDrawn) {
//            canvas.drawText("$" + formattedTamt, pageWidth - x - 100, y - itemHeight * lines.size(), paint);
//            amountDrawn = true;
//        }
//
//        return y;
//    }


    private void viewPDF(File file) {
        new Thread(() -> {
            try {
                downloadAndDisplayPdf(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

}
