package com.senzec.alfa.utils;

import android.content.Context;
import android.os.CountDownTimer;
import com.senzec.alfa.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProgressClass {

    private static ProgressClass progressClass = null;
    private SweetAlertDialog pDialog, pDialogText;
    private int i = -1;
    private final int SPLASH_TIME = 20000;

    private ProgressClass()
    {

    }
    public static ProgressClass getProgressInstance()
    {
        if(progressClass == null)
        {
            progressClass = new ProgressClass();
        }
        return progressClass;
    }

     public void startProgress(final Context mContext)
    {
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Loading");
        pDialog.show();
        pDialog.setCancelable(true);
        new CountDownTimer(1500 * 7, 1500) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i){
                    case 0:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.error_stroke_color));
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.red_btn_bg_pressed_color));
                        break;
                    case 4:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 6:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.warning_stroke_color));
                        break;
                }
            }

            public void onFinish() {
                i = -1;
            //    pDialog.dismiss();
               /* pDialog.setTitleText("Success!")
                        .setConfirmText("OK")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);*/
            }
        }.start();

    }
    public void startProgressTextChanged(final Context mContext, String customText)
    {
        pDialogText = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(customText);
        pDialogText.show();
        pDialogText.setCancelable(true);
        new CountDownTimer(1500 * 7, 1500) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i){
                    case 0:
                        pDialogText.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        pDialogText.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.error_stroke_color));
                        break;
                    case 2:
                        pDialogText.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        pDialogText.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.red_btn_bg_pressed_color));
                        break;
                    case 4:
                        pDialogText.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        pDialogText.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 6:
                        pDialogText.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.warning_stroke_color));
                        break;
                }
            }



            public void onFinish() {
                i = -1;
                //    pDialog.dismiss();
               /* pDialog.setTitleText("Success!")
                        .setConfirmText("OK")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);*/
            }
        }.start();

    }

    public void startTimeProgressbar(final Context mContext)
    {
        startProgressTextChanged(mContext, "Loading");
        CountDownTimer counter = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilDone){
//                startProgressTextChanged(mContext, "Loading");
            }

            public void onFinish() {
                stopProgressText();
                startProgressTextChanged(mContext, "Custom Text");

            }
        }.start();

    }



    public void alertProgress()
    {
        pDialog.setTitleText("Success!")
                .setConfirmText("OK")
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
    }
    public void stopProgress()
    {
        for(int i = 0; i<10; i++) {
            if (pDialog != null){
                pDialog.dismiss();
        }}
    }
    public void stopProgressText()
    {
     //   progressDialog.cancel();
        for(int i = 0; i<10; i++) {
         if(pDialogText != null)
         { pDialogText.dismiss(); }
        }
    }



}
