package com.softcore.vtpsales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.softcore.vtpsales.databinding.ActivityInOutPaymentList2Binding;

public class InOutInvoiceView extends AppCompatActivity {

    ActivityInOutPaymentList2Binding binding;

    private String DocNo;
    private String VTPDocNo;
    private String VTP_Doc_No;
    private String DocEntry;
    private String CusName;
    private String PostingDate;
    private String Reference;
    private String Payment_On_Account;
    private String Inv_Doc_No;
    private String Amount;
    private String Total;
    private String Cheque_No;
    private String Cheque_Date;
    private String Mode_Of_Payment;
    private String Remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_ar_invoice_view);
        binding = ActivityInOutPaymentList2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
        DocNo = intent.getStringExtra("DocNo");
        VTPDocNo = intent.getStringExtra("VTPDocNo");
        VTP_Doc_No = intent.getStringExtra("VTP_Doc_No");
        DocEntry = intent.getStringExtra("DocEntry");
        CusName = intent.getStringExtra("CusName");
        PostingDate = intent.getStringExtra("PostingDate");
        Reference = intent.getStringExtra("Reference");
        Payment_On_Account = intent.getStringExtra("Payment_On_Account");
        Inv_Doc_No = intent.getStringExtra("Inv_Doc_No");
        Amount = intent.getStringExtra("Amount");
        Total = intent.getStringExtra("Total");
        Cheque_No = intent.getStringExtra("Cheque_No");
        Cheque_Date = intent.getStringExtra("Cheque_Date");
        Mode_Of_Payment = intent.getStringExtra("Mode_Of_Payment");
        Remarks = intent.getStringExtra("Remarks");




//        binding.





//        GetInvoice();
    }

//    private void GetInvoice() {
//
//        String EmpNo = AppUtil.getStringData(getApplicationContext(),"EmpCode","");
//        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");
//
//
//        AppUtil.showProgressDialog(binding.getRoot(),"Loading");
//
//        ARInvoiceViewModel viewModel= new ViewModelProvider(this).get(ARInvoiceViewModel.class);
//        viewModel.getArInvoiceData(DbName).observe(this, new Observer<CommanResorce<List<ARInvoiceModel>>>() {
//            @Override
//            public void onChanged(CommanResorce<List<ARInvoiceModel>> listCommanResorce) {
//
//                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {
//
//                    List<ARInvoiceModel> list;
//                    list = new ArrayList<>();
//
//                    for(int i = 0;i<listCommanResorce.data.size();i++){
//                        if(DocEntry.equals(String.valueOf(listCommanResorce.data.get(i).getDocEntry()))
//                         && DocNo.equals(String.valueOf(listCommanResorce.data.get(i).getDocNum()))){
//                            list.add(listCommanResorce.data.get(i));
//                        }
//                    }
//
//
//                    if(list.size() != 0){
//                       binding.textCardName.setText(list.get(0).getCardName());
//                        binding.textGrossAmount.setText("₹ "+String.format("%.2f",list.get(0).getGrossAmount()));
//                        binding.textGrossAmount2.setText("₹ "+String.format("%.2f",list.get(0).getGrossAmount()));
//                        binding.textNetAmount.setText("₹ "+String.format("%.2f",list.get(0).getNetAmount()));
//                        binding.textNetAmount2.setText("₹ "+String.format("%.2f",list.get(0).getNetAmount()));
//                        binding.textPostingDate.setText(AppUtil.convertDateFormat(String.valueOf(list.get(0).getPostingDate())));
//                        binding.textDueDate.setText(AppUtil.convertDateFormat(String.valueOf(list.get(0).getDueDate())));
//                     //   binding.textDocNum.setText("Document No.: "+String.valueOf(list.get(0).getDocNum()));
//                        if(list.get(0).getCustomerRefNo() != null){
//                            binding.textCustomerRefNo.setText("Customer Ref No.: "+list.get(0).getCustomerRefNo());
//                        }
//                       else{
//                            binding.textCustomerRefNo.setText("Customer Ref No.: ");
//                        }
//                        binding.textTaxDate.setText("Document Date: "+AppUtil.convertDateFormat(String.valueOf(list.get(0).getTaxDate())));
//                        binding.textHSN.setText("HSN/SAC: "+String.valueOf(list.get(0).getHsnSac()));
//                        binding.textCGSTAmount.setText("₹ "+String.format("%.2f",list.get(0).getCgstAmount()));
//                        binding.textSGSTAmount.setText("₹ "+String.format("%.2f",list.get(0).getSgstAmount()));
//                        binding.textStatus.setText(String.valueOf(list.get(0).getStatus()));
//                        binding.textQuantity.setText("Quantity: "+String.valueOf(list.get(0).getQuantity()));
//                        binding.textRate.setText("Rate: "+String.valueOf(list.get(0).getRate()));
//                        binding.textLrno.setText("Lr No.: "+list.get(0).getLrNo());
//                        binding.textTransportVechicle.setText("Transport Vehicle No.: "+list.get(0).getVehicleNo());
//                        binding.textDueDate2.setText("Due Date: "+AppUtil.convertDateFormat(String.valueOf(list.get(0).getDueDate())));
//                        binding.textDocNum.setText("VTP Doc No.: "+VTPDocNo);
//                        binding.laybar.appbarTextView.setText("Invoice Doc No.: "+VTPDocNo.toString());
//                        binding.textItemDescription.setText(String.valueOf(list.get(0).getItemDescription()));
//
//                        if(list.get(0).getStatus().equals("unpaid")){
//                            binding.cardStatusCard.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
//                        }
//                        else if(list.get(0).getStatus().equals("paid")){
//                            binding.cardStatusCard.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_green));
//                        }else{
//                            binding.cardStatusCard.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
//                        }
//
//                        binding.layShareInvoice.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(getApplicationContext(), PdfViewerActivity.class);
//                                intent.putExtra("DocEntry", String.valueOf(list.get(0).getDocEntry()));
//                                intent.putExtra("CusName", String.valueOf(list.get(0).getCardName()));
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }
//                        });
//
//                    }else {
//
//                    }
//
//                }
//
//                AppUtil.hideProgressDialog();
//            }
//        });
//
//    }
}