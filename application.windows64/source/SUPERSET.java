import processing.core.*; 
import processing.data.*; 
import processing.opengl.*; 

import java.lang.reflect.*; 
import java.awt.Frame; 
import java.awt.BorderLayout; 
import java.awt.event.*; 
import controlP5.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class SUPERSET extends PApplet {

boolean DEBUG = false;









float rotX;
float rotY;
public int DeplacerX = 0;
public int DeplacerY = 0;
public int DeplacerZ = 0;
public int RotationX = 0;
public int RotationY = 0;
public int RotationZ = 0;
public int Importer = 0;
public int Contraste;
int CIBLE;
public int Presentation_Mode = 0 ;
public int Mouse = 1;
public float Echelle = 1.00f;
boolean isControlInit = false;


PVector A, B, C, D, CENTRE;
PShape s;
private ControlP5 cp5;
ControlFrame cf;


Renderer renderers[];


public void setup() {

  size(displayHeight, displayHeight, P3D);
  //size(800,800,P3D);
  background(0);

  // Description des points de l'\u00e9cran
  A = new PVector(0, 0, 0);
  B = new PVector(width, 0, 0);
  C = new PVector(width, height, 0);
  D = new PVector(0, height, 0);
  CENTRE = new PVector(width/2, height/2, 0);

  ///DECLARATION MODEL
  s = loadShape("MTECH.obj");

  // INTERFACE
cp5 = new ControlP5(this);
  cf = addControlFrame("AGRANDIR= plus d'options", 280, 220);
  
  // Renderers
  renderers = new Renderer[4];
  renderers[0] = new Renderer(this, width, height/2, D, C, CENTRE);
  renderers[1] = new Renderer(this, width, height/2, A, B, CENTRE);
  renderers[2] = new Renderer(this, width, height/2, A, D, CENTRE);
  renderers[3] = new Renderer(this, width, height/2, C, B, CENTRE);
}

public void mouseWheel(int delta) {
  Mouse = delta;
  println("scroll "+ delta);
}

public void draw()
{

  float time = (millis()*0.01f)* Presentation_Mode;

  renderers[0].setRotation( 0, -180, 0);
  renderers[1].setRotation( 0, 0, 0); 
  renderers[2].setRotation( 0, -90, 0);
  renderers[3].setRotation( 0, -270, 0 );

  for (int i=0;i<renderers.length;i++) {
    renderers[i].draw();
  }


  drawRenderer(0);
  drawRenderer(1);
  drawRenderer(2);
  drawRenderer(3);


  /*
tint(255,255,255,60);
   image(renderers[0].pg, 0, 0);
   */
}




public void drawRenderer(int which)
{
  Renderer r = renderers[which];

  noStroke();
  fill(255, 255);
  beginShape(TRIANGLES);
  textureMode(NORMAL);
  texture(r.pg);
  if (which == 0) {
    vertex(r.A.x, r.A.y, 0, 1);
    vertex(r.B.x, r.B.y, 1, 1);
    vertex(r.C.x, r.C.y, 0.5f, 0);
  }
  else
    if (which == 1)
    {
      vertex(r.A.x, r.A.y, 1, 1);
      vertex(r.B.x, r.B.y, 0, 1);
      vertex(r.C.x, r.C.y, 0.5f, 0);
    }
    else
      if (which == 2)
      {
        vertex(r.A.x, r.A.y, 0, 1);
        vertex(r.B.x, r.B.y, 1, 1);
        vertex(r.C.x, r.C.y, 0.5f, 0.0f);
      }
      else
        if (which == 3)
        {
          vertex(r.A.x, r.A.y, 0, 1);
          vertex(r.B.x, r.B.y, 1, 1);
          vertex(r.C.x, r.C.y, 0.5f, 0);
        }
  endShape();

  if (DEBUG)
  {
    noFill();
    stroke(255,0,0);
    strokeWeight(2);
    triangle(r.A.x, r.A.y, r.B.x, r.B.y, r.C.x, r.C.y);
    fill(255);
    text("vue "+which,(r.A.x+r.B.x+r.C.x)/3,(r.A.y+r.B.y+r.C.y)/3);
  }
}



///// IMPORTATEUR

public void Importer(int theValue) {
  if (!isControlInit)
    return;
  selectInput("Select a file to process:", "fileselected");
}

public void fileselected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  }
  else {
    println("User selected " + selection.getAbsolutePath());
    PShape temp = loadShape(selection.getAbsolutePath());
    if (temp != null)
      s = temp;
  }
}
//////////////////


public void drawRendererObject(Renderer r) //METTRE 3D A LA PLACE?
{
  r.graphics().translate(DeplacerX, DeplacerY+50, DeplacerZ);
  if (DEBUG)
    drawAxis(r.graphics(),20); // dessine les axes
  float time = (millis()*0.01f)* Presentation_Mode;

  r.graphics().scale(Echelle/200);
  r.graphics().rotateX(radians(RotationX+rotX)+600);
  r.graphics().rotateY(radians(RotationY+rotY+time));
  r.graphics().ambientLight(Contraste/2+110, Contraste/2+110, Contraste/2+110);
  r.graphics().directionalLight(255, 255, 255, -1, -1, -1);
  r.graphics().directionalLight(255, 255, 255, -1, -1, -1);
  r.graphics().shape(s, 0, 0);
}
public void mouseDragged()
{
  rotY += (mouseX - pmouseX)  ;
  rotX -= (mouseY - pmouseY)  ;
}


public ControlFrame addControlFrame(String theName, int theWidth, int theHeight) {
  Frame f = new Frame(theName);
  ControlFrame p = new ControlFrame(this, theWidth, theHeight);
  f.add(p);
  p.init();
  f.setTitle(theName);
  f.setSize(p.w, p.h);
  f.setLocation(100, 100);
  f.setResizable(true);
  f.setVisible(true);
  return p;
}


public class ControlFrame extends PApplet {

  int w, h;

  int abc = 100;

  public int Contraste;
  public float Echelle = 10.00f;

  //PImage bg;
  Textarea myTextarea;


  public void setup() {
    size(w, h);
    frameRate(25);
    cp5 = new ControlP5(this);

     cp5.addSlider("Rotation X").plugTo(parent, "RotationX").setRange(0, 360)
      .setPosition(20, 30).setSize(200, 15).setColorBackground(color(255, 100));

    cp5.addSlider("Rotation Y").plugTo(parent, "RotationY").setRange(0, 360)
      .setPosition(20, 60).setSize(200, 15).setColorBackground(color(255, 100));

    


    //////DEPLACEMENTS

    cp5.addSlider("Deplacer X").plugTo(parent, "DeplacerX").setRange(-100, 100)
      .setPosition(280, 30).setSize(200, 15).setColorBackground(color(255, 50));

    cp5.addSlider("Deplacer Y").plugTo(parent, "DeplacerY").setRange(-200, 200)
      .setPosition(280, 60).setSize(200, 15).setColorBackground(color(255, 50));

    cp5.addSlider("Deplacer Z").plugTo(parent, "DeplacerZ").setRange(-100, 100)
      .setPosition(280, 90).setSize(200, 15).setColorBackground(color(255, 50));


    /////MODE PRESENTATION

    cp5.addSlider("Contraste / Ombres").plugTo(parent, "Contraste").setRange(0, 255)
      .setPosition(280, 130).setSize(100, 10).setColorBackground(color(255));

    cp5.addSlider("Showroom / Vitesse de Rotation").plugTo(parent, "Presentation_Mode").setRange(-50, 50)
      .setPosition(280, 155).setSize(100, 10).setColorBackground(color(255));

    /////OPTIONS

    cp5.addSlider("Echelle").plugTo(parent, "Echelle").setRange(1, 100)
      .setPosition(20, 90).setSize(200, 15).setColorBackground(color(255, 80));

    cp5.addButton("Importer OBJ").plugTo(parent, "Importer").setValue(0)
      .setPosition(20, 155).setSize(65, 20).setColorBackground(color(255, 80));

    
    /////TEXT INSTRUCTIONS
    myTextarea = cp5.addTextarea("INSTRUCTIONS").setPosition(20, 200).setSize(200, 200)
      .setFont(createFont("Avenir-Light", 10)).setLineHeight(20)
        .setColor(0xffFF0F0F).setColorBackground(color(255, 80)).setColorForeground(color(255, 100));
    ;
    
    myTextarea.setText(
    "INSTRUCTIONS DE FONCTIONNEMENT :"
      +"   "
      +"  _____________________________________  ROTATIONS X,Y,Z, tournez selon l'axe que vous souhaitez"
      +"   "
      +"  _____________________________________  DEPLACEMENTS X,Y pour d\u00e9placer l'hologramme dans l'espace"
      +" "
      +"  _____________________________________  SHOWROOM = faire tourner l'objet (pour une pr\u00e9sentation par exemple), choisir la vitesse"
      +" "
      +"  _____________________________________  ECHELLE : Ajustez la taille de l'objet"
      +" "
      +"  _____________________________________  IMPORTER OBJ: importer votre 3D en hologramme sour format WAVEFRONT (.obj)"
      +" "
      +"  _____________________________________  CIBLE: pour positionner son \u00e9cran dans l'axe de la pyramide"
      );

    isControlInit = true;
    smooth();
  }

  public void draw() {
    background(30);

    //image(bg,0,0);
  }

  private ControlFrame() {
  }

  public ControlFrame(Object theParent, int theWidth, int theHeight) {
    parent = theParent;
    w = theWidth;
    h = theHeight;
  }


  public ControlP5 control() {
    return cp5;
  }


  ControlP5 cp5;

  Object parent;
}

class Renderer
{
  PApplet applet;
  PGraphics pg;
  PVector transfoRotation;
  PVector position;
  PVector dimension;
  Method methodDrawObject;
  PVector A, B, C;

  Renderer(PApplet applet, float w, float h, PVector A_, PVector B_, PVector C_)
  {
    this.applet = applet;
    this.pg = createGraphics(PApplet.parseInt(w), PApplet.parseInt(h), P3D);
    this.pg.sphereDetail(7);

    //position = new PVector(x, y, 0);
    A = A_.get();
    B = B_.get();
    C = C_.get();
    dimension = new PVector(w, h, 0);
    transfoRotation = new PVector(0, 0, 0);

    try 
    {
      this.methodDrawObject = applet.getClass().getMethod("drawRendererObject", new Class[] {
        Renderer.class
      }
      );
      System.out.println("- \"findMethodFrame\" found.");
    } 
    catch (Exception e) 
    {
      System.out.println("- no \"findMethodFrame\" found.");
    }
  }

  public PGraphics graphics() {
    return pg;
  }

  public void setRotation(float x, float y, float z)
  {
    transfoRotation.set(x, y, z);
  }

  public void draw()
  {

    pg.beginDraw();    
    pg.background(0);//
   pg.beginCamera();
    pg.camera();
    //float camZ = (pg.height/2.0) / tan(PI*30.0 / 180.0);
//    pg.camera(pg.width/2.0, pg.height/2.0, camZ, pg.width/2.0, pg.height/2.0, 0, 0, 1, 0);
//    pg.camera(pg.width/2.0, pg.height/2.0, camZ, pg.width/2.0, pg.height/2.0, 0, 0, 1, 0);
//    pg.rotateX(radians(transfoRotation.x));
    float camX = 200*cos(radians(transfoRotation.y+90));
    float camY = 0;
    float camZ = 200*sin(radians(transfoRotation.y+90));
    pg.camera(camX, camY, camZ, 0, 0, 0, 0, 1, 0);
//    pg.translate(0,0,200);
//    pg.rotateY(radians(transfoRotation.z));
//    pg.rotateY(radians(transfoRotation.y));
    pg.endCamera();

//    pg.translate(pg.width/2, pg.height/2);
    //pg.rotateX(radians(transfoRotation.x));
//    pg.rotateY(radians(transfoRotation.y));
    //pg.rotateY(radians(transfoRotation.z));
    pg.pushMatrix();

    try {
      if (methodDrawObject!=null)
      methodDrawObject.invoke( applet, new Object[] { 
        this
      } 
      );
    }
    catch(Exception e) {
    }

    pg.popMatrix();
    pg.endDraw();

    //image(pg, position.x, position.y, dimension.x, dimension.y);
  }

  /*
 void drawAxis(float l)
   {
   
   //Axes d'ajustement :
   
   pg.pushStyle();
   pg.stroke(255, 0, 0); 
   pg.line(0, 0, 0, l, 0, 0);
   pg.stroke(0, 255, 0); 
   pg.line(0, 0, 0, 0, l, 0);
   pg.stroke(0, 0, 255); 
   pg.line(0, 0, 0, 0, 0, l);
   pg.popStyle();
   }//*/
}

public void drawAxis(PGraphics pg, float l)
{
  pg.pushStyle();
  pg.strokeWeight(2);
  pg.stroke(255,0,0);pg.line(0,0,0, l,0,0);
  pg.stroke(0,255,0);pg.line(0,0,0, 0,l,0);
  pg.stroke(0,0,255);pg.line(0,0,0, 0,0,l);
  pg.popStyle();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SUPERSET" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
