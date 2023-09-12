package com.example.minigames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class Game1 extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_scissors, btn_rock, btn_paper;
    ImageView img_user_select, img_robot_select;
    TextView score_user, score_robot;
    TextView remainCnt;

    // 가위 : 0, 바위 : 1, 보 : 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        btn_scissors = findViewById(R.id.btn_scissors);
        btn_rock = findViewById(R.id.btn_rock);
        btn_paper = findViewById(R.id.btn_paper);
        img_user_select = findViewById(R.id.img_user_select);
        img_robot_select = findViewById(R.id.img_robot_select);
        score_user = findViewById(R.id.score_user);
        score_robot = findViewById(R.id.score_robot);
        remainCnt = findViewById(R.id.remainCnt);

        btn_scissors.setOnClickListener(this);
        btn_rock.setOnClickListener(this);
        btn_paper.setOnClickListener(this);
    }

    int win[][] = {{0,2}, {1,0}, {2,1}};

    int hands[] = {R.drawable.gif_scissors, R.drawable.gif_rock, R.drawable.gif_paper};

    @Override
    public void onClick(View view) {
        int veluser = 0;
        switch (view.getId()) {
            case R.id.btn_scissors:
                veluser = 0;
                break;
            case R.id.btn_rock:
                veluser = 1;
                break;
            case R.id.btn_paper:
                veluser = 2;
                break;
        }



        Random r = new Random();
        int velrobot = r.nextInt(3);

        img_user_select.setImageResource(hands[veluser]);
        img_robot_select.setImageResource(hands[velrobot]);

        if (veluser == velrobot) {
            try {
                moveGifImage(img_robot_select, hands[velrobot]);
                moveGifImage(img_user_select, hands[veluser]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if ((veluser == win[0][0] && velrobot == win[0][1]) ||
                (veluser == win[1][0] && velrobot == win[1][1]) ||
                (veluser == win[2][0] && velrobot == win[2][1])) {
            try {
                moveGifImage(img_user_select, hands[veluser]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Integer user = Integer.parseInt(score_user.getText().toString());
            user++;
            score_user.setText(user.toString());
        } else {
            try {
                moveGifImage(img_robot_select, hands[velrobot]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Integer robot = Integer.parseInt(score_robot.getText().toString());
            robot++;
            score_robot.setText(robot.toString());
        }

        // 횟수 차감

        isGameOver();

    }

    private void isGameOver() {
        // 횟수를 읽고, -1 적용 후 화면에 표시
        Integer count = Integer.parseInt(remainCnt.getText().toString()) - 1;
        remainCnt.setText(count.toString());

        String msg = "";

        if(count == 0) {
            int userScore = Integer.parseInt(score_user.getText().toString());
            int robotScore = Integer.parseInt(score_robot.getText().toString());


            if(userScore > robotScore) {
                msg = "축하합니다~";
            }
            else if(userScore < robotScore) {
                msg = "다음 기회에~";
            }
            else {
                msg = "비겼습니다~";
            }

            // 다이얼로그 상자 띄우기
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("게임오버");
            builder.setMessage(msg);
            builder.setPositiveButton("새게임", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    score_user.setText("0");
                    score_robot.setText("0");
                    remainCnt.setText("10");
                    img_user_select.setImageResource(R.drawable.img_empty);
                    img_robot_select.setImageResource(R.drawable.img_empty);
                }
            });

            builder.setNegativeButton("게임종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });


            builder.setCancelable(false);
            builder.create();
            builder.show();


        }
    }

    private void moveGifImage(ImageView imgView, int hand) throws IOException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            Drawable decodeImg = ImageDecoder.decodeDrawable(
                ImageDecoder.createSource(getResources(), hand));

            imgView.setImageDrawable(decodeImg);

            AnimatedImageDrawable ani = (AnimatedImageDrawable) decodeImg;
            ani.setRepeatCount(1);
            ani.start();
        }
        else {

        }
    }
}