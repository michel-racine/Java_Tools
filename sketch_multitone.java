import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Thread;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class SinSynth {

   protected static final int SAMPLE_RATE = 16 * 1024;

   public static byte[] createSinWaveBuffer(int ms) {

       double period, angle;
       double freq = 1000;

       int samples = (int)((ms * SAMPLE_RATE) / 1000);
       byte[] output = new byte[samples];

       for (int i = 0; i < output.length; i++) {
           // make divebomb effect
           freq *= 0.999999;
           period = (double)SAMPLE_RATE / freq;
           angle = 2.0 * Math.PI * i / period;
           output[i] = (byte)(Math.sin(angle) * 127f);
       }

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

       // create buffer 3000 milliseconds long
       byte [] toneBuffer = createSinWaveBuffer(3000);

       // write out or "play" buffer
       int count = line.write(toneBuffer, 0, toneBuffer.length);

       line.drain();
       line.close();
    }
}
