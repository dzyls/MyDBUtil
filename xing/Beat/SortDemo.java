package com.xing.Beat;

/*
 *  @author dzyls
 *  @create 2017-08-05 19:24
 */

import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortDemo {


    public static void sort(int []a){

        for (int i = 1; i < a.length; i++) {

            int j = i;
            int temp = a[j];
            while (j > 0 && temp < a[j - 1]) {
                a[j] = a[j-1];
                j--;
            }
            a[j] = temp;
        }
    }

    public static void MpSort(int []a){

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - 1; j++) {

                if (a[j]>a[j+1]){

                    a[j] +=a[j+1];
                    a[j+1] = a[j] -a[j+1];
                    a[j] -=a[j+1];

                }


            }
        }
        
    }

    public  static byte[] getRandom(){
        byte []bytes = new byte[1024];
        for (int i = 0; i < 1024; i++) {
            bytes[i] = (byte) new Random().nextInt(128);
        }

        return bytes;
    }

    public  static char[] getRandomChar(){
        char []bytes = new char[1024];
        for (int i = 0; i < 1024; i++) {
            bytes[i] = (char) ( new Random().nextInt(50000)+9000);
        }

        return bytes;
    }

    public static void main(String[] args) {


        try {
            FileOutputStream fout = new FileOutputStream("金瓶梅3.txt");
            FileChannel channel = fout.getChannel();

            FileWriter writer = new FileWriter("a.txt",true);

            Charset charset = Charset.forName("utf-8");
            System.out.println(Arrays.toString(getRandomChar()));
            channel.force(true);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            CharBuffer charBuffer = CharBuffer.allocate(1024);
            for (int i = 0; i < 1024; i++) {

                writer.write(getRandomChar());


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }






}
