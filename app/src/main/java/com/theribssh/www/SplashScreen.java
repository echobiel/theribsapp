package com.theribssh.www;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by ecobiel on 04/10/2017.
 */

public class SplashScreen extends Activity{

    ImageView imagem_introducao;
    TextView text_bem_vindo;
    ProgressBar progress_bar_introducao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        text_bem_vindo = (TextView) findViewById(R.id.text_bem_vindo);
        imagem_introducao = (ImageView) findViewById(R.id.imagem_introducao);
        progress_bar_introducao = (ProgressBar) findViewById(R.id.progress_bar_introducao);

        Thread timerThread = createThread();
        timerThread.start();
    }

    @NonNull
    private Thread createThread() {
        return new Thread(){
                @Override
                public void run() {
                    try {
                        synchronized (this){
                            final int[] progresso={0};
                            wait(1000);
                            while (progresso[0] < 34){
                                wait(20);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progresso[0] = progresso[0] + 1;
                                        progress_bar_introducao.setProgress(progresso[0]);
                                    }
                                });
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    text_bem_vindo.setText("Faça reservas !");
                                }
                            });

                            while (progresso[0] < 67){
                                wait(20);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progresso[0] = progresso[0] + 1;
                                        progress_bar_introducao.setProgress(progresso[0]);
                                    }
                                });
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    text_bem_vindo.setText("Dê-nos um feedback !");
                                }
                            });

                            while (progresso[0] <= 100){
                                wait(20);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progresso[0] = progresso[0] + 1;
                                        progress_bar_introducao.setProgress(progresso[0]);
                                    }
                                });
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    text_bem_vindo.setText("E tenha um bom dia !");
                                }
                            });
                        }
                        sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            };
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
