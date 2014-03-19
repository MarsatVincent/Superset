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
    this.pg = createGraphics(int(w), int(h), P3D);
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

  PGraphics graphics() {
    return pg;
  }

  void setRotation(float x, float y, float z)
  {
    transfoRotation.set(x, y, z);
  }

  void draw()
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

