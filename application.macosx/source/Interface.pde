
ControlFrame addControlFrame(String theName, int theWidth, int theHeight) {
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
  public float Echelle = 10.00;

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
        .setColor(#FF0F0F).setColorBackground(color(255, 80)).setColorForeground(color(255, 100));
    ;
    
    myTextarea.setText(
    "INSTRUCTIONS DE FONCTIONNEMENT :"
      +"   "
      +"  _____________________________________  ROTATIONS X,Y,Z, tournez selon l'axe que vous souhaitez"
      +"   "
      +"  _____________________________________  DEPLACEMENTS X,Y pour déplacer l'hologramme dans l'espace"
      +" "
      +"  _____________________________________  SHOWROOM = faire tourner l'objet (pour une présentation par exemple), choisir la vitesse"
      +" "
      +"  _____________________________________  ECHELLE : Ajustez la taille de l'objet"
      +" "
      +"  _____________________________________  IMPORTER OBJ: importer votre 3D en hologramme sour format WAVEFRONT (.obj)"
      +" "
      +"  _____________________________________  CIBLE: pour positionner son écran dans l'axe de la pyramide"
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

