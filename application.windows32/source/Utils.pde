void drawAxis(PGraphics pg, float l)
{
  pg.pushStyle();
  pg.strokeWeight(2);
  pg.stroke(255,0,0);pg.line(0,0,0, l,0,0);
  pg.stroke(0,255,0);pg.line(0,0,0, 0,l,0);
  pg.stroke(0,0,255);pg.line(0,0,0, 0,0,l);
  pg.popStyle();
}
