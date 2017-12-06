package com.theribssh.www;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by echobiel on 13/11/2017.
 */

@SuppressLint("ValidFragment")
public class DialogFragmentReserva extends DialogFragment {

    private int numStyle;
    private int numTheme;
    private int id_restaurante;
    private int id_cliente;

    WebView web_view_reserva;

    @SuppressLint("ValidFragment")
    public DialogFragmentReserva(int numStyle, int numTheme, int id_restaurante){
        this.numStyle = numStyle;
        this.numTheme = numTheme;

        this.id_restaurante = id_restaurante;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Script", "onCreate()");

        int style;
        int theme;

        switch(numStyle){
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_INPUT; break;
            case 3: style = DialogFragment.STYLE_NO_FRAME; break;
            default: style = DialogFragment.STYLE_NORMAL; break;
        }

        switch(numTheme){
            case 1: theme = android.R.style.Theme_Holo_Light; break;
            case 2: theme = android.R.style.Theme_Holo_Dialog; break;
            default: theme = android.R.style.Theme_Holo_Light_DarkActionBar; break;
        }

        setStyle(style, theme);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.i("Script", "onCreateView()");

        View view = inflater.inflate(R.layout.dialog_reserva, container);

        Intent intent = getActivity().getIntent();

        id_cliente = intent.getIntExtra("id_usuario", 0);

        web_view_reserva = (WebView) view.findViewById(R.id.web_view_reserva);

        web_view_reserva.getSettings().setJavaScriptEnabled(true);
        web_view_reserva.setWebChromeClient(new WebChromeClient());
        web_view_reserva.setWebViewClient(new WViewClient());

        web_view_reserva.loadUrl(String.format("http://%s?id=%d&id_cliente=%d&funcao=reservar", getResources().getString(R.string.ip_reserva), id_restaurante, id_cliente));

        web_view_reserva.addJavascriptInterface(new JsInterface(), "android");

        web_view_reserva.setInitialScale(100);

        WebSettings mWebSettings = web_view_reserva.getSettings();

        mWebSettings.setJavaScriptEnabled(true);

        return view;
    }

    private class  WViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

    private class JsInterface{

        @JavascriptInterface
        public void toast(String msg){
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            dismiss();
        }

        @JavascriptInterface
        public void fechar(){
            dismiss();
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Script", "onActivityCreated()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Script", "onAttach()");
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.i("Script", "onCancel()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Script", "onDestroy()");
    }
}
