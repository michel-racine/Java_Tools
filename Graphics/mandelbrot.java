import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.lang.Math.*;
import java.io.File;
import javax.imageio.ImageIO;

public class MemoryImageGenerator extends Frame {
  Image img;

  Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
  int w = 800; // (int)size.getWidth();
  int h = 800; // (int)size.getHeight();

  public MemoryImageGenerator() {
    int pixels[] =  new int[w * h];
    int i=0;
    double xn1 = 0.0;
    double yn1 = 0.0;
    double xn = 0.0;
    double yn = 0.0;
    double xc = 0.0;
    double yc = 0.0;
    double radius = 0.0;
    int iti = 0;
    int max_iter = 255;

    for(double y=0; y<800; y++) {
      for(double x=0; x<800; x++) {
        xc = (x/200)-2.0-0.01;
        yc = (y/200)-2.0-0.01;
        xn = xc; xn1 = xc;
        yn = yc; yn1 = yc;
        iti = 0;
        radius = Math.sqrt(Math.pow(xn1,2) + Math.pow(yn1,2));

        while((radius < 2.0) & iti < max_iter){
          xn1 = Math.pow(xn, 2) - Math.pow(yn, 2) + xc;
          yn1 = 2 * xn * yn + yc;
          radius = Math.sqrt(Math.pow(xn1, 2) + Math.pow(yn1, 2));
          xn = xn1;
          yn = yn1;
          iti += 1;
        }
          pixels[i++] = (255 << 24) | (iti << 16) | (iti << 8) | iti;
   //       pixels[i++] = (iti << 24) | (255 << 16) | (255 << 8) | 255;
      }
    }
    img = createImage(new MemoryImageSource(w, h, pixels, 0, w));

//File outputfile = new File("mandelbrot.png");
//ImageIO.write(img, "png", outputfile);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        System.exit(0);
      }
    });
  }

  public void paint(Graphics g) {
    g.drawImage(img, getInsets().left, getInsets().top, null);
  }

  public static void main(String[] args) {
    MemoryImageGenerator appwin = new MemoryImageGenerator();
    appwin.setSize(new Dimension(800, 800));
    appwin.setTitle("Mandelbrot Set");
    Image icon = Toolkit.getDefaultToolkit().getImage("Data/death.png");
    appwin.setIconImage(icon);
    appwin.setVisible(true);
  }
}
