// modified from https://stackoverflow.com/questions/8632104/sine-wave-sound-generator-in-java

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Thread;

public class SinSynth {

   protected static final int SAMPLE_RATE = 16 * 1024;

   public static byte[] createSinWaveBuffer(double freq, int ms) {
       int samples = (int)((ms * SAMPLE_RATE) / 1000);
       byte[] output = new byte[samples];
       //
       double period = (double)SAMPLE_RATE / freq;
       for (int i = 0; i < output.length; i++) {
           double angle = 2.0 * Math.PI * i / period;
           output[i] = (byte)(Math.sin(angle) * 127f);  }

       return output;
   }


   public static void main(String[] args) throws LineUnavailableException {

       boolean forwardNotBack = true;

       final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
       SourceDataLine line = AudioSystem.getSourceDataLine(af);
       line.open(af, SAMPLE_RATE);
       line.start();

       System.out.println("\n[+] playing...\n");
       System.out.println("Sample rate: " + SAMPLE_RATE);

       PointerInfo pi = MouseInfo.getPointerInfo();
       Point p = pi.getLocation();
       double freq;
       freq = p.getX();
       byte [] toneBuffer = createSinWaveBuffer(freq, 1000);
       int count = line.write(toneBuffer, 0, toneBuffer.length);

       line.drain();
       line.close();
    }
}
