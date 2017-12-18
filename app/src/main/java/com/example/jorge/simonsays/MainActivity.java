package com.example.jorge.simonsays;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    int MAX_VOLUME = 100; //volumen máximo de referencia
    int soundVolume = 90; //volumen que queremos poner
    float volume = (float) (1 - (Math.log(MAX_VOLUME - soundVolume) / Math.log(MAX_VOLUME)));
    Button botonescolor[];
    Button jugar;
    Button restart;
    TextView labelPuntuacion;
    Button rojo, azul, verde, amarillo;


    public static final int INTERVALO = 2000; //2 segundos para salir
    public long tiempoPrimerClick;

    ArrayList<Integer> game = new ArrayList();
    ArrayList<Integer> answers = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.);
       // setSupportActionBar(toolbar);

        //Desactivamos la opcion de rotacion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        //boton start y reset funcional desde xml
        jugar = (Button)findViewById(R.id.jugar);
        restart = (Button)findViewById(R.id.restart);
        restart.setEnabled(false);

        //puntuacion funcional desde xml
        labelPuntuacion = (TextView)findViewById(R.id.labelPuntuacion);

        //botones funcionales desde xml
        botonescolor = new Button[]{
                (Button)findViewById(R.id.rojo),
                (Button)findViewById(R.id.verde),
                (Button)findViewById(R.id.azul),
                (Button)findViewById(R.id.amarillo)
        };



        //pulsar boton start y inicar el juego
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                generategame();
                disabledButtons();
            }
        });

        //boton reset funcional
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                generategame();
                disabledButtons();
            }
        });



    }

    //generar partida con activacion de botones y desactivacion (cuando inicie la secuencia de colores estarán
    //desactivados hasta que finalize
    public void generategame(){
        restart.setText("Next Lvl");
        restart.setEnabled(true);
        jugar.setVisibility(View.INVISIBLE);
        restart.setVisibility(View.INVISIBLE);
        int time=1000;

        final int color = (int)(Math.random()*4);
        game.add(color);

        for(int g=0; g<game.size(); g++){
            Handler hand1 = new Handler();
            final Handler hand2 = new Handler();

            final int end = g;
            hand1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    botonescolor[game.get(end)].setPressed(true);
                    disabledButtons();
                    hand2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            botonescolor[game.get(end)].setPressed(false);
                            enabledButtons();
                        }
                    },500);
                }
            },time*g+1000);
        }
        disabledButtons();
        labelPuntuacion.setText(String.valueOf(game.size()));
    }

    //comprobaciones colores, sonido al perder y mensaje
    public void check(int colorCheck){

        final Toast txtlooser = Toast.makeText(getApplicationContext(),"YOU ARE LOOSER", Toast.LENGTH_SHORT);

        if(colorCheck==0){
            answers.add(0);
        }
        else if(colorCheck==1){
            answers.add(1);
        }
        else if(colorCheck==2){
            answers.add(2);
        }
        else{
            answers.add(3);
        }

       // if(game.size()==answers.size()){
          //  for(int a=0; a<game.size(); a++){
          //      if(game.get(a).equals(answers.get(a))){
            //    }
          //      else{
               //     looser.start();
               //     txtlooser.show();
            ///        winner.stop();
                //    answers.clear();
                //    game.clear();
                //    lvl.setText("0");
                //    play.setVisibility(View.VISIBLE);
               //     reset.setEnabled(false);
              //  }
          //  }
         //   answers.clear();
         //   winner.start();
         //   reset.setVisibility(View.VISIBLE);
     //   }

    }



    //descactivar botones al iniciar el juego
    public void disabledButtons(){

        rojo = (Button)findViewById(R.id.rojo);
        rojo.setEnabled(false);
        azul = (Button)findViewById(R.id.azul);
        azul.setEnabled(false);
        verde = (Button)findViewById(R.id.verde);
        verde.setEnabled(false);
        amarillo = (Button)findViewById(R.id.amarillo);
        amarillo.setEnabled(false);

    }

    //activar botones una vez inicie el juego
    public void enabledButtons(){

        rojo = (Button)findViewById(R.id.rojo);
        rojo.setEnabled(true);
        azul = (Button)findViewById(R.id.azul);
        azul.setEnabled(true);
        verde = (Button)findViewById(R.id.verde);
        verde.setEnabled(true);
        amarillo = (Button)findViewById(R.id.amarillo);
        amarillo.setEnabled(true);

    }



}
