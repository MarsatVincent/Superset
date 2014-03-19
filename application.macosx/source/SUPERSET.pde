boolean DEBUG = false;


import java.lang.reflect.*;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.event.*;

import controlP5.*;

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
public float Echelle = 1.00;
boolean isControlInit = false;


PVector A, B, C, D, CENTRE;
PShape s;
private ControlP5 cp5;
ControlFrame cf;


Renderer renderers[];


void setup() {

  size(displayHeight, displayHeight, P3D);
  //size(800,800,P3D);
  background(0);

  // Description des points de l'écran
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

void mouseWheel(int delta) {
  Mouse = delta;
  println("scroll "+ delta);
}

void draw()
{

  float time = (millis()*0.01)* Presentation_Mode;

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




void drawRenderer(int which)
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
    vertex(r.C.x, r.C.y, 0.5, 0);
  }
  else
    if (which == 1)
    {
      vertex(r.A.x, r.A.y, 1, 1);
      vertex(r.B.x, r.B.y, 0, 1);
      vertex(r.C.x, r.C.y, 0.5, 0);
    }
    else
      if (which == 2)
      {
        vertex(r.A.x, r.A.y, 0, 1);
        vertex(r.B.x, r.B.y, 1, 1);
        vertex(r.C.x, r.C.y, 0.5, 0.0);
      }
      else
        if (which == 3)
        {
          vertex(r.A.x, r.A.y, 0, 1);
          vertex(r.B.x, r.B.y, 1, 1);
          vertex(r.C.x, r.C.y, 0.5, 0);
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

void fileselected(File selection) {
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


void drawRendererObject(Renderer r) //METTRE 3D A LA PLACE?
{
  r.graphics().translate(DeplacerX, DeplacerY+50, DeplacerZ);
  if (DEBUG)
    drawAxis(r.graphics(),20); // dessine les axes
  float time = (millis()*0.01)* Presentation_Mode;

  r.graphics().scale(Echelle/200);
  r.graphics().rotateX(radians(RotationX+rotX)+600);
  r.graphics().rotateY(radians(RotationY+rotY+time));
  r.graphics().ambientLight(Contraste/2+110, Contraste/2+110, Contraste/2+110);
  r.graphics().directionalLight(255, 255, 255, -1, -1, -1);
  r.graphics().directionalLight(255, 255, 255, -1, -1, -1);
  r.graphics().shape(s, 0, 0);
}
void mouseDragged()
{
  rotY += (mouseX - pmouseX)  ;
  rotX -= (mouseY - pmouseY)  ;
}

