package com.example.minigames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minigames.databinding.ActivityLottoBinding;

import java.util.ArrayList;
import java.util.Random;

public class LottoActivity extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnScrollListener {

    ActivityLottoBinding binding;

    // 선택한 숫자 저장이 될 예정
    ArrayList<Integer> myNumberList = new ArrayList<>();
    ArrayList<Integer> robotNumberList = new ArrayList<>();


    // 보여질 공에 대한
    ArrayList<TextView> myBall = new ArrayList<>();
    ArrayList<TextView> robotBall = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotto);


        binding = ActivityLottoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addButton.setOnClickListener(this); // 추가 버튼
        binding.clearButton.setOnClickListener(this); // 클리어 버튼
        binding.runButton.setOnClickListener(this); // 숫자 추첨 버튼
        binding.numberPicker.setOnScrollListener(this);

        // numberPicker 스크롤 범위 지정
        binding.numberPicker.setMinValue(1);
        binding.numberPicker.setMaxValue(45);
        Random r = new Random();
        binding.numberPicker.setValue(r.nextInt(45) + 1);

        // TextView로 이루어진 공 객체를 미리 리스트에 넣어두기
        myBall.add(binding.myNum1);
        myBall.add(binding.myNum2);
        myBall.add(binding.myNum3);
        myBall.add(binding.myNum4);
        myBall.add(binding.myNum5);
        myBall.add(binding.myNum6);

        robotBall.add(binding.lottoNum1);
        robotBall.add(binding.lottoNum2);
        robotBall.add(binding.lottoNum3);
        robotBall.add(binding.lottoNum4);
        robotBall.add(binding.lottoNum5);
        robotBall.add(binding.lottoNum6);

        clearMyBalls();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.addButton) {
            pickOneBall();
        }
        else if(view.getId() == R.id.clearButton) {
            clearMyBalls();
        }
        else if(view.getId() == R.id.runButton) {
            // 6개의 공을 다 선택하지 않았으면?
            if(myNumberList.size() != 6) {
                Toast.makeText(this, "먼저 6개의 공을 선택하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 로봇이 6개의 공을 골라서 화면에 표시
            pickRobotBall();
            for( int i = 0; i < robotNumberList.size(); i++ ) {
                robotBall.get(i).setText(Integer.toString(robotNumberList.get(i)));
                robotBall.get(i).setVisibility(View.VISIBLE);
            }

            // 몇개가 일치하는지 체크에서 화면에 보이기
            int win = 0; // 맞은 개수
            int count = 0; // 시도 횟수

            while( win < 6 ) {
                win = 0;
                for (Integer my : myNumberList) {
                    if (robotNumberList.contains(my)) {
                        win++;
                    }
                }
                pickRobotBall();
                count++;


            }

            binding.txtResult.setText("당첨개수: " + win + " 시도횟수: " + count);
            for( int i = 0; i < robotNumberList.size(); i++ ) {
                robotBall.get(i).setText(Integer.toString(robotNumberList.get(i)));
                robotBall.get(i).setVisibility(View.VISIBLE);
            }
        }
    }

    private void pickRobotBall() {
        // 초기화
        robotNumberList.clear();

        int robotNum = 0;
        Random r = new Random();

        while(robotNumberList.size() < 6) {
            robotNum = r.nextInt(45) + 1;
            if(robotNumberList.contains(robotNum)) {
                continue;
            }
            robotNumberList.add(robotNum);
        }
    }

    private void clearMyBalls() { // 공 지우기
        
        for( int i = 0; i < myBall.size(); i++ ) { // 내 공 그림 안보이게 설정
            myBall.get(i).setVisibility(View.INVISIBLE);
        }
        for( int i = 0; i < robotBall.size(); i++ ) { // 로봇 공 그림 안보이게 설정
            robotBall.get(i).setVisibility(View.INVISIBLE);
        }

        myNumberList.clear(); // 내 숫자 클리어
        robotNumberList.clear(); // 로봇 숫자 클리어
        binding.txtResult.setText("6개의 공을 선택하세요.");

    }

    // 공 하나 추첨
    private void pickOneBall() {

        // 6개의 공을 이미 다 뽑은 경우
        if(myNumberList.size() >= 6) {
            Toast.makeText(this, "6개 모두 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 내가 고른 숫자 읽기
        int num = binding.numberPicker.getValue();
        Log.d("숫자", "TEST" + num);

        // 중복된 값인지 체크
        if(myNumberList.contains(num)) {
            Toast.makeText(this, "이미 선택한 숫자입니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            // 리스트에 공 번호 추가
            myNumberList.add(num);

            // 화면에 공의 번호 보이기
            for( int i = 0; i < myNumberList.size(); i++ ) {
                myBall.get(i).setText(Integer.toString(myNumberList.get(i)));
                myBall.get(i).setVisibility(View.VISIBLE);
            }

        }




    }

    @Override
    public void onScrollStateChange(NumberPicker numberPicker, int i) {
        if(binding.switch1.isChecked() && i == SCROLL_STATE_IDLE) {
            binding.addButton.performClick();
        }
    }
}